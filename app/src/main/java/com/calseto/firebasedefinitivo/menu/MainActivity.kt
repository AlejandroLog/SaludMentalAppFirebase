package com.calseto.firebasedefinitivo.menu

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.calseto.firebasedefinitivo.R
import com.calseto.firebasedefinitivo.datosemociones.IngresarDatosActivity
import com.calseto.firebasedefinitivo.datosemociones.ObtenerDatosActivity
import com.calseto.firebasedefinitivo.login.LoginActivity
import com.calseto.firebasedefinitivo.recursosrelajacion.MenuRecursosActivity
import com.calseto.firebasedefinitivo.recursosrelajacion.RecursosActivity
import com.calseto.firebasedefinitivo.usuarios.AdministrarUsuariosActivity
import com.calseto.firebasedefinitivo.usuarios.DetallesUsuarioActivity
import com.calseto.firebasedefinitivo.usuarios.EditarPerfilActivity
import com.google.firebase.auth.FirebaseAuth

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
        val btnEditarPerfil: ImageView = findViewById(R.id.btnEditarPerfil)

        btnEditarPerfil.setOnClickListener {
            val intent = Intent(this, EditarPerfilActivity::class.java)
            startActivity(intent)
        }

        btnAgregarEmocion.setOnClickListener {
            val miInten = Intent(this, IngresarDatosActivity::class.java)
            startActivity(miInten)
        }

        btnVerEmociones.setOnClickListener {
            val miInten = Intent(this, ObtenerDatosActivity::class.java)
            startActivity(miInten)
        }

        btnRecursosDeRelajacion.setOnClickListener {
            val miInten = Intent(this, MenuRecursosActivity::class.java)
            startActivity(miInten)
        }
        btnAdministrarUsuarios.setOnClickListener {
            val miIntent = Intent(this, AdministrarUsuariosActivity::class.java)
            startActivity(miIntent)
        }


        btnExitLogin.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }


    }
}