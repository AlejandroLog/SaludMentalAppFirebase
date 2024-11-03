package com.calseto.firebasedefinitivo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val btnAgregarEmocion: Button = findViewById(R.id.btnAgregarEmocion)
        val btnVerEmociones: Button = findViewById(R.id.btnVerEmociones)
        val btnRecursosDeRelajacion: Button = findViewById(R.id.btnRecursosDeRelajacion)
        val btnAdministrarUsuarios: Button = findViewById(R.id.btnAdministrarUsuarios)
        val btnExitLogin: Button = findViewById(R.id.btnExitLogin)


        btnAgregarEmocion.setOnClickListener {
            val miInten = Intent(this, IngresarDatosActivity::class.java)
            startActivity(miInten)
        }

        btnVerEmociones.setOnClickListener {
            val miInten = Intent(this, ObtenerDatosActivity::class.java)
            startActivity(miInten)
        }

        btnRecursosDeRelajacion.setOnClickListener {
            val miInten = Intent(this, RecursosActivity::class.java)
            startActivity(miInten)
        }
        btnAdministrarUsuarios.setOnClickListener {
            val miIntent = Intent(this, AdministrarUsuariosActivity::class.java)
            startActivity(miIntent)
        }

        btnExitLogin.setOnClickListener {
            val miIntent = Intent(this, LoginActivity::class.java)
            startActivity(miIntent)
            finish()
        }



    }
}