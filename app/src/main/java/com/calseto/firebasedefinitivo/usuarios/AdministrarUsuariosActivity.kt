package com.calseto.firebasedefinitivo.usuarios

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.calseto.firebasedefinitivo.R
import com.calseto.firebasedefinitivo.recyclerview.UserAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
class AdministrarUsuariosActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var userAdapter: UserAdapter
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_administrar_usuarios)

        val btnRegistrarNew: Button = findViewById(R.id.btnRegistrarNew)
        val btnExit: Button = findViewById(R.id.btnExit)
        btnExit.setOnClickListener {
            finish()
        }

        btnRegistrarNew.setOnClickListener {
            val intent = Intent(this, NewUsuarioActivity::class.java)
            startActivity(intent)
        }

        // Referencia a RecyclerView
        recyclerView = findViewById(R.id.recyclerViewUsuarios)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // UID del usuario autenticado
        val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid ?: ""

        // Configurar el adaptador
        userAdapter = UserAdapter(
            currentUserUid,
            onClick = { user ->
                // Navegar a DetallesUsuarioActivity
                val intent = Intent(this, DetallesUsuarioActivity::class.java)
                intent.putExtra("USER_ID", user.userId)
                startActivity(intent)
            },
            onDelete = { user ->
                // Eliminar el usuario seleccionado
                eliminarUsuario(user)
            }
        )

        recyclerView.adapter = userAdapter

        // Configurar Firebase Database
        database = FirebaseDatabase.getInstance().reference.child("usuarios")

        // Cargar usuarios desde Firebase
        cargarUsuarios()
    }

    private fun cargarUsuarios() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val usuarios = mutableListOf<User>()
                for (data in snapshot.children) {
                    val user = data.getValue(User::class.java)
                    if (user != null) {
                        usuarios.add(user)
                    }
                }
                userAdapter.submitList(usuarios)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@AdministrarUsuariosActivity, "Error al cargar usuarios", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun eliminarUsuario(user: User) {
        database.child(user.userId).removeValue().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Usuario eliminado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Error al eliminar usuario", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
