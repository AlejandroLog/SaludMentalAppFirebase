package com.calseto.firebasedefinitivo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.calseto.firebasedefinitivo.recyclerview.RecursoAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore

class RecursosActivity : AppCompatActivity() {
    private lateinit var recursoAdapter: RecursoAdapter
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recursos)

        val btnSalirRecursos: Button = findViewById(R.id.btnSalirRecursos)
        val btnNuevoRecurso: Button = findViewById(R.id.btnNuevoRecurso)

        btnNuevoRecurso.setOnClickListener {
            startActivity(Intent(this, AgregarRecursoActivity::class.java))
        }
        btnSalirRecursos.setOnClickListener {
            finish()
        }

        database = FirebaseDatabase.getInstance().getReference("recursos")
        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewRecursos)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recursoAdapter = RecursoAdapter(mutableListOf())
        recyclerView.adapter = recursoAdapter

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val listaRecursos = mutableListOf<Recurso>()
                for (data in snapshot.children) {
                    data.getValue(Recurso::class.java)?.let { listaRecursos.add(it) }
                }
                recursoAdapter.updateData(listaRecursos)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@RecursosActivity, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
data class Recurso(
    val id: String = "",
    val imageUrl: String = "",
    val descripcion: String = ""
)


