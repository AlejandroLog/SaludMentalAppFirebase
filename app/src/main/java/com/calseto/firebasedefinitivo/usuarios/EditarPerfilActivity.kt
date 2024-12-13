package com.calseto.firebasedefinitivo.usuarios

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.calseto.firebasedefinitivo.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class EditarPerfilActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private lateinit var etNombre: EditText
    private lateinit var etCorreo: EditText
    private lateinit var etUrlImagen: EditText
    private lateinit var btnGuardar: Button
    private lateinit var ivImagenPerfil: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_perfil)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference.child("usuarios").child(auth.currentUser?.uid ?: "")

        etNombre = findViewById(R.id.etNombreEditar)
        etCorreo = findViewById(R.id.etCorreoEditar)
        etUrlImagen = findViewById(R.id.etUrlImagenEditar)
        btnGuardar = findViewById(R.id.btnGuardarCambios)
        ivImagenPerfil = findViewById(R.id.ivImagenPerfil)

        cargarDatosUsuario()

        btnGuardar.setOnClickListener {
            guardarCambios()
        }
    }

    private fun cargarDatosUsuario() {
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                if (user != null) {
                    etNombre.setText(user.nombre)
                    etCorreo.setText(user.email)
                    etUrlImagen.setText(user.imageUrl)
                    Glide.with(this@EditarPerfilActivity).load(user.imageUrl).into(ivImagenPerfil)
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun guardarCambios() {
        val nuevoNombre = etNombre.text.toString()
        val nuevoCorreo = etCorreo.text.toString()
        val nuevaUrlImagen = etUrlImagen.text.toString()

        if (nuevoNombre.isNotEmpty() && nuevoCorreo.isNotEmpty()) {
            val updates = mapOf(
                "nombre" to nuevoNombre,
                "email" to nuevoCorreo,
                "imageUrl" to nuevaUrlImagen
            )

            database.updateChildren(updates).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Perfil actualizado correctamente", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Error al guardar cambios: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "Por favor, llena todos los campos", Toast.LENGTH_SHORT).show()
        }
    }
}
