package com.calseto.firebasedefinitivo.recursosrelajacion.canciones

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.calseto.firebasedefinitivo.R

class Song2Activity : AppCompatActivity() {
    private lateinit var mediaPlayer: MediaPlayer
    private var isPlaying = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_song2)
        // Inicializa el MediaPlayer con tu archivo MP3
        mediaPlayer = MediaPlayer.create(this, R.raw.so2) // Asegúrate de tener el archivo en res/raw

        // Configura el botón
        val btnPlayPause: Button = findViewById(R.id.btnPlayPause)
        btnPlayPause.setOnClickListener {
            if (isPlaying) {
                pauseMusic(btnPlayPause)
            } else {
                playMusic(btnPlayPause)
            }
        }
    }
    private fun playMusic(button: Button) {
        mediaPlayer.start()
        button.text = "Pause"
        isPlaying = true
    }

    private fun pauseMusic(button: Button) {
        mediaPlayer.pause()
        button.text = "Play"
        isPlaying = false
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
        }
        mediaPlayer.release()
    }
}