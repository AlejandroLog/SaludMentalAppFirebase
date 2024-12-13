package com.calseto.firebasedefinitivo.presentacion

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.calseto.firebasedefinitivo.R
import com.calseto.firebasedefinitivo.login.LoginActivity

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