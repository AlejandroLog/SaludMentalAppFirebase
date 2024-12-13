package com.calseto.firebasedefinitivo.usuarios

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.calseto.firebasedefinitivo.R
import com.google.firebase.auth.FirebaseAuth

class RestableserPassActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_restableser_pass)


        auth = FirebaseAuth.getInstance()

        val etCorreo: EditText = findViewById(R.id.etCorreoRecuperar)
        val btnEnviar: Button = findViewById(R.id.btnEnviarRecuperacion)
        val btnRegresar: Button = findViewById(R.id.btnRegresarLogin)

        btnEnviar.setOnClickListener {
            val email = etCorreo.text.toString()
            if (email.isNotEmpty()) {
                auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Correo enviado, revisa tu bandeja de entrada", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
                        }
                    }
            }


            else {
                Toast.makeText(this, "Por favor, ingresa un correo válido.", Toast.LENGTH_SHORT).show()
            }
        }

        btnRegresar.setOnClickListener {
            finish()
        }
    }
}





