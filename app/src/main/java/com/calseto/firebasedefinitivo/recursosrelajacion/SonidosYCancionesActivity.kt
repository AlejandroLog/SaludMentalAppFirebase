package com.calseto.firebasedefinitivo.recursosrelajacion

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.calseto.firebasedefinitivo.R
import com.calseto.firebasedefinitivo.recursosrelajacion.canciones.Song1Activity
import com.calseto.firebasedefinitivo.recursosrelajacion.canciones.Song2Activity
import com.calseto.firebasedefinitivo.recursosrelajacion.canciones.Song3Activity
import com.calseto.firebasedefinitivo.recursosrelajacion.canciones.Song4Activity

class SonidosYCancionesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sonidos_ycanciones)


        val btnSong1: Button = findViewById(R.id.btnSong1)
        val btnSong2: Button = findViewById(R.id.btnSong2)
        val btnSong3: Button = findViewById(R.id.btnSong3)
        val btnSong4: Button = findViewById(R.id.btnSong4)


        btnSong1.setOnClickListener {
            val intent = Intent(this, Song1Activity::class.java)
            startActivity(intent)
        }
        btnSong2.setOnClickListener {
            val intent = Intent(this, Song2Activity::class.java)
            startActivity(intent)
        }
        btnSong3.setOnClickListener {
            val intent = Intent(this, Song3Activity::class.java)
            startActivity(intent)
        }
        btnSong4.setOnClickListener {
            val intent = Intent(this, Song4Activity::class.java)
            startActivity(intent)
        }
    }
}

