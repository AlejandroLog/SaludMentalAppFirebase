package com.calseto.firebasedefinitivo.datosemociones

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.calseto.firebasedefinitivo.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class IngresarDatosActivity : AppCompatActivity() {

    private lateinit var dbRef: DatabaseReference
    private lateinit var etNombre: Spinner
    private lateinit var etEdad: Spinner
    private lateinit var etSalario: EditText
    private lateinit var btnGuardar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ingresar_datos)

        etNombre = findViewById(R.id.etNombre)
        etEdad = findViewById(R.id.etEdad)
        etSalario = findViewById(R.id.etSalario)
        btnGuardar = findViewById(R.id.btnGuardar)

        // Obtener UID del usuario autenticado y actualizar la referencia de la base de datos
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            dbRef = FirebaseDatabase.getInstance().getReference("Empleados/$userId")
        } else {
            Toast.makeText(this, "Error: Usuario no autenticado", Toast.LENGTH_SHORT).show()
            finish() // Cierra la actividad si no hay usuario autenticado
            return
        }

        val btnSalirIngresar: Button = findViewById(R.id.btnSalirIgresar)

        btnGuardar.setOnClickListener {
            guardarEmpleado()
        }

        btnSalirIngresar.setOnClickListener {
            finish()
        }
    }

    private fun guardarEmpleado() {
        val nombreEmpleado = etNombre.selectedItem.toString()
        val edadEmpleado = etEdad.selectedItem.toString() // Esto representa las emociones
        val salarioEmpleado = etSalario.text.toString() // Esto puede representar un dato extra
        val idEmpleado = dbRef.push().key!! // Genera una clave única
        // Validar emociones
        when (edadEmpleado) {
            "Feliz" -> mostrarDialogoHappy()
            "Triste" -> mostrarDialogoSad()
            "Ansiedad" -> mostrarDialogoAnsiedad()
        }
        // Crear el modelo con los datos
        val emocion = EmployeModel(idEmpleado, nombreEmpleado, edadEmpleado, salarioEmpleado)
        // Guardar la emoción en el nodo del usuario
        dbRef.child(idEmpleado).setValue(emocion).addOnCompleteListener {
            Toast.makeText(this, "Emoción registrada exitosamente", Toast.LENGTH_LONG).show()
            etSalario.text.clear()
        }.addOnFailureListener { err ->
            Toast.makeText(this, "Error: ${err.message}", Toast.LENGTH_LONG).show()
        }
    }


    private fun mostrarDialogoSad() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_sad)
        dialog.findViewById<Button>(R.id.btnCerrarSad).setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }


    private fun mostrarDialogoHappy() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_happy)
        dialog.findViewById<Button>(R.id.btnCerrarSad2).setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }


    private fun mostrarDialogoAnsiedad() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_ansiedad)
        dialog.findViewById<Button>(R.id.btnCerrarSad3).setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }


}