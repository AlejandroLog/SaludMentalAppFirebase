package com.calseto.firebasedefinitivo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PresentacionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_presentacion)

        val btnInicioSesion: Button = findViewById(R.id.btnInicioSesion)

        btnInicioSesion.setOnClickListener {
            val miIntent = Intent(this, LoginActivity::class.java)
            startActivity(miIntent)
            finish()
        }

    }
}