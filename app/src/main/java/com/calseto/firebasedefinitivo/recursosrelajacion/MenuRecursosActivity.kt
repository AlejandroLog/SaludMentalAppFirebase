package com.calseto.firebasedefinitivo.recursosrelajacion

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.calseto.firebasedefinitivo.R
import com.calseto.firebasedefinitivo.recursosrelajacion.api.PeliculasActivity

class MenuRecursosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu_recursos)

        val btnConsejosRelajacion: Button = findViewById(R.id.btnConsejosRelajacion)
        val btnPeliculasParaver: Button = findViewById(R.id.btnPeliculasParaver)
        val btnMusicaDeRelajacion: Button = findViewById(R.id.btnMusicaDeRelajacion)
        val btnExit: Button = findViewById(R.id.btnExit)

        btnConsejosRelajacion.setOnClickListener {
            val intent = Intent(this, RecursosActivity::class.java)
            startActivity(intent)
        }

        btnPeliculasParaver.setOnClickListener {
            val intent = Intent(this, PeliculasActivity::class.java)
            startActivity(intent)
        }

        btnMusicaDeRelajacion.setOnClickListener {
            val intent = Intent(this, SonidosYCancionesActivity::class.java)
            startActivity(intent)
        }

        btnExit.setOnClickListener {
            finish()
        }

    }
}