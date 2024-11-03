package com.calseto.firebasedefinitivo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.ktx.auth

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth

        val etEmail: EditText = findViewById(R.id.etEmail)
        val etContraseña: EditText = findViewById(R.id.etContraseña)
        val btnEntrar: Button = findViewById(R.id.btnIngresar)
        val btnRegistrar: Button = findViewById(R.id.btnNuevoUsuario)

        btnEntrar.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etContraseña.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Ingrese lo que se le pide", Toast.LENGTH_SHORT).show()
            }
        }

        btnRegistrar.setOnClickListener {
            val intent = Intent(this, NewUsuarioActivity::class.java)
            startActivity(intent)
        }
    }
}
