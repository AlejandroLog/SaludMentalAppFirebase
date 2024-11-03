package com.calseto.firebasedefinitivo

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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
        dbRef = FirebaseDatabase.getInstance().getReference("Empleados")
        val btnSalirIngresar: Button = findViewById(R.id.btnSalirIgresar)


        btnGuardar.setOnClickListener {
            guardarEmpleado()
        }



        btnSalirIngresar.setOnClickListener {
            finish()
        }



    }
    private fun guardarEmpleado() {

        val nombreEmpleado =  etNombre.selectedItem.toString()
        val edadEmpleado = etEdad.selectedItem.toString()
        val salarioEmpleado = etSalario.text.toString()
        val idEmpleado = dbRef.push().key!!



        if (edadEmpleado == "Feliz"){
            mostrarDialogoHappy()
        }else if (edadEmpleado == "Triste"){
            mostrarDialogoSad()
        }else{
            mostrarDialogoAnsiedad()
        }



        val empleadoo = EmployeModel(idEmpleado, nombreEmpleado, edadEmpleado, salarioEmpleado)



        dbRef.child(idEmpleado).setValue(empleadoo).addOnCompleteListener {
            Toast.makeText(this, "Guardado", Toast.LENGTH_LONG).show()
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