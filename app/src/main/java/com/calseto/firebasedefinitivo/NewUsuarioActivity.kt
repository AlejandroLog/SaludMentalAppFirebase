package com.calseto.firebasedefinitivo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database


class NewUsuarioActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_usuario)

        auth = Firebase.auth
        database = Firebase.database.reference

        val etEmailNew: EditText = findViewById(R.id.etEmailNew)
        val etContraseñaNew: EditText = findViewById(R.id.etContraseñaNew)
        val btnRegistrar: Button = findViewById(R.id.btnRegistrar)
        val btnExitNew: Button = findViewById(R.id.btnExitNew)

        btnExitNew.setOnClickListener {
            finish()
        }

        btnRegistrar.setOnClickListener {
            val email = etEmailNew.text.toString()
            val password = etContraseñaNew.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val userId = auth.currentUser?.uid
                            val user = User(userId, email)
                            userId?.let {
                                database.child("usuarios").child(it).setValue(user)
                            }
                            Toast.makeText(this, "Registro Guardado", Toast.LENGTH_SHORT).show()
                            finish()
                        } else {
                            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Ingresa lo que se te pide", Toast.LENGTH_SHORT).show()
            }
        }


    }
}

data class User(
    val userId: String? = "",
    val email: String = ""
)

