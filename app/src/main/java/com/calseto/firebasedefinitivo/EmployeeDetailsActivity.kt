package com.calseto.firebasedefinitivo

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class EmployeeDetailsActivity : AppCompatActivity() {
    private lateinit var textViewIdEmpleado: TextView
    private lateinit var textViewNombreEmpleado: TextView
    private lateinit var textViewEdadEmpleado: TextView
    private lateinit var textViewSalarioEmpleado: TextView


    private fun initView(){
        textViewIdEmpleado = findViewById(R.id.txtId)
        textViewNombreEmpleado = findViewById(R.id.txtNombre)
        textViewEdadEmpleado = findViewById(R.id.txtEdad)
        textViewSalarioEmpleado = findViewById(R.id.txtSalario)

    }

    private fun setValuesToViews(){
        textViewIdEmpleado.text = intent.getStringExtra("idEmpleado")
        textViewNombreEmpleado.text = intent.getStringExtra("nombreEmpleado")
        textViewEdadEmpleado.text = intent.getStringExtra("edadEmpleado")
        textViewSalarioEmpleado.text = intent.getStringExtra("salarioEmpleado")

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_employee_details)

        val btnExitDetalles: Button = findViewById(R.id.btnExitDetalles)

        initView()
        setValuesToViews()

        btnExitDetalles.setOnClickListener {
            finish()
        }

    }
}