package com.calseto.firebasedefinitivo.usuarios

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.calseto.firebasedefinitivo.R
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database


class NewUsuarioActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_usuario)

        auth = Firebase.auth
        database = Firebase.database.reference

        val etEmail: EditText = findViewById(R.id.etEmailNew)
        val etContraseña: EditText = findViewById(R.id.etContraseñaNew)
        val etNombre: EditText = findViewById(R.id.etNombreNew)
        val etUrlFoto: EditText = findViewById(R.id.etUrlFotoNew)
        val btnRegistrar: Button = findViewById(R.id.btnRegistrar)
        val btnExit: Button = findViewById(R.id.btnExitNew)

        btnExit.setOnClickListener {
            finish()
        }

        btnRegistrar.setOnClickListener {
            val nombre = etNombre.text.toString()
            val email = etEmail.text.toString()
            val password = etContraseña.text.toString()
            val imageUrl = etUrlFoto.text.toString()

            if (nombre.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                registrarUsuario(nombre, email, password, imageUrl)
            } else {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun registrarUsuario(nombre: String, email: String, password: String, imageUrl: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->


                if (task.isSuccessful) {

                    val user = auth.currentUser

                    user?.sendEmailVerification()?.addOnCompleteListener { emailTask ->
                        if (emailTask.isSuccessful) {
                            guardarUsuarioEnBaseDeDatos(user.uid, email, nombre, imageUrl)
                            Toast.makeText(this, "Registro exitoso. Verifica tu correo electrónico", Toast.LENGTH_SHORT).show()
                            finish()
                        } else {
                            Toast.makeText(this, "Error al enviar correo de verificación", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun guardarUsuarioEnBaseDeDatos(userId: String, email: String, nombre: String, imageUrl: String) {
        val user = User(userId, email, nombre, imageUrl)
        database.child("usuarios").child(userId).setValue(user)
    }
}

data class User(
    val userId: String = "",
    val email: String = "",
    val nombre: String = "",
    val imageUrl: String = ""
)



