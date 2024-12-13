package com.calseto.firebasedefinitivo.datosemociones

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.calseto.firebasedefinitivo.R
import com.calseto.firebasedefinitivo.recyclerview.EmployeeAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ObtenerDatosActivity : AppCompatActivity() {

    private lateinit var empleadosRecicleView: RecyclerView
    private lateinit var txtDatosLoad: TextView
    private lateinit var empList: ArrayList<EmployeModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_obtener_datos)

        empleadosRecicleView = findViewById(R.id.rvEmpleado)
        empleadosRecicleView.layoutManager = LinearLayoutManager(this)
        empleadosRecicleView.setHasFixedSize(true)
        txtDatosLoad = findViewById(R.id.txtDatos)

        empList = arrayListOf()

        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            dbRef = FirebaseDatabase.getInstance().getReference("Empleados/$userId")
            ObtenerDatosEmpleado()
        } else {
            Toast.makeText(this, "Error: Usuario no autenticado", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun ObtenerDatosEmpleado() {
        empleadosRecicleView.visibility = View.GONE
        txtDatosLoad.visibility = View.VISIBLE

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                empList.clear()
                if (snapshot.exists()) {
                    for (empSnap in snapshot.children) {
                        val empData = empSnap.getValue(EmployeModel::class.java)
                        if (empData != null) {
                            empList.add(empData)
                        }
                    }
                    val mAdapter = EmployeeAdapter(empList)
                    empleadosRecicleView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : EmployeeAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@ObtenerDatosActivity, EmployeeDetailsActivity::class.java)
                            intent.putExtra("idEmpleado", empList[position].idEmpleado)
                            intent.putExtra("nombreEmpleado", empList[position].nombreEmpleado)
                            intent.putExtra("edadEmpleado", empList[position].edadEmpleado)
                            intent.putExtra("salarioEmpleado", empList[position].salarioEmpleado)
                            startActivity(intent)
                        }
                    })

                    empleadosRecicleView.visibility = View.VISIBLE
                    txtDatosLoad.visibility = View.GONE
                } else {
                    Toast.makeText(this@ObtenerDatosActivity, "No hay datos disponibles", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ObtenerDatosActivity, "Error al obtener datos: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}




