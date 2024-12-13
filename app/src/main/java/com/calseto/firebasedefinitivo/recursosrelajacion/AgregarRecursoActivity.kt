package com.calseto.firebasedefinitivo.recursosrelajacion

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.calseto.firebasedefinitivo.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AgregarRecursoActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_recurso)

        database = FirebaseDatabase.getInstance().reference.child("recursos")

        val btnGuardar: Button = findViewById(R.id.btnAgregarTodoElRecurso)
        val txtImageUrl: EditText = findViewById(R.id.etUrl)
        val txtDescripcion: EditText = findViewById(R.id.etDescripcion)
        val btnExitNewRecurso: Button = findViewById(R.id.btnExitNewRecurso)

        btnExitNewRecurso.setOnClickListener {
            finish()
        }

        btnGuardar.setOnClickListener {
            val imageUrl = txtImageUrl.text.toString()
            val descripcion = txtDescripcion.text.toString()
            val id = database.push().key ?: ""

            if (imageUrl.isNotEmpty() && descripcion.isNotEmpty()) {
                val nuevoRecurso = Recurso(id, imageUrl, descripcion)
                database.child(id).setValue(nuevoRecurso).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "Recurso guardado", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}


