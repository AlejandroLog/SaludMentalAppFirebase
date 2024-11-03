package com.calseto.firebasedefinitivo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.calseto.firebasedefinitivo.recyclerview.UserAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class AdministrarUsuariosActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var userList: ArrayList<User>
    private lateinit var recyclerView: RecyclerView
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_administrar_usuarios)

        database = FirebaseDatabase.getInstance().getReference("usuarios")
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        userList = ArrayList()
        userAdapter = UserAdapter(userList)
        recyclerView.adapter = userAdapter

        val btnAgregarUser: Button = findViewById(R.id.btnAgregarUser)
        val btnExitAdmind: Button = findViewById(R.id.btnExitAdmind)

        btnExitAdmind.setOnClickListener {
            finish()
        }

        btnAgregarUser.setOnClickListener {
            val miIntent = Intent(this, NewUsuarioActivity::class.java)
            startActivity(miIntent)
        }

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                for (dataSnapshot in snapshot.children) {
                    val user = dataSnapshot.getValue(User::class.java)
                    user?.let { userList.add(it) }
                }
                userAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })


    }
}
