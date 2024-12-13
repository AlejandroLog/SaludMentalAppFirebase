package com.calseto.firebasedefinitivo.usuarios

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.calseto.firebasedefinitivo.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DetallesUsuarioActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var userId: String
    private lateinit var tvNombre: TextView
    private lateinit var tvCorreo: TextView
    private lateinit var ivFoto: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_usuario)

        userId = intent.getStringExtra("USER_ID") ?: return
        tvNombre = findViewById(R.id.tvNombreDetalle)
        tvCorreo = findViewById(R.id.tvCorreoDetalle)
        ivFoto = findViewById(R.id.ivFotoDetalle)

        val btnActualizar = findViewById<Button>(R.id.btnActualizar)
        val btnEliminar = findViewById<Button>(R.id.btnEliminar)

        database = FirebaseDatabase.getInstance().getReference("usuarios").child(userId)

        cargarDetalles()

        btnActualizar.setOnClickListener { mostrarDialogActualizar() }
        btnEliminar.setOnClickListener { eliminarUsuario() }
    }

    private fun cargarDetalles() {
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                if (user != null) {
                    tvNombre.text = user.nombre
                    tvCorreo.text = user.email
                    Glide.with(this@DetallesUsuarioActivity).load(user.imageUrl).into(ivFoto)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@DetallesUsuarioActivity, "Error al cargar detalles", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun mostrarDialogActualizar() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_actualizar_usuario, null)
        val etNombre = dialogView.findViewById<EditText>(R.id.etNombreActualizar)
        val etCorreo = dialogView.findViewById<EditText>(R.id.etCorreoActualizar)
        val etUrl = dialogView.findViewById<EditText>(R.id.etUrlActualizar)

        etNombre.setText(tvNombre.text)
        etCorreo.setText(tvCorreo.text)
        etUrl.setText((ivFoto.drawable as? BitmapDrawable)?.bitmap.toString())

        AlertDialog.Builder(this)
            .setTitle("Actualizar Usuario")
            .setView(dialogView)
            .setPositiveButton("Actualizar") { _, _ ->
                val nombre = etNombre.text.toString()
                val correo = etCorreo.text.toString()
                val url = etUrl.text.toString()

                database.updateChildren(mapOf("nombre" to nombre, "email" to correo, "imageUrl" to url))
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(this, "Usuario actualizado", Toast.LENGTH_SHORT).show()
                            cargarDetalles()
                        } else {
                            Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }


    private fun eliminarUsuario() {
        database.removeValue().addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(this, "Usuario eliminado", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Error al eliminar", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
