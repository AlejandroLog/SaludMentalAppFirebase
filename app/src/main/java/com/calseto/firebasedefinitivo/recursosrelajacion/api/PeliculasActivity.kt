package com.calseto.firebasedefinitivo.recursosrelajacion.api

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.calseto.firebasedefinitivo.R
import com.calseto.firebasedefinitivo.databinding.ActivityMainBinding

class PeliculasActivity : AppCompatActivity() {
    private lateinit var viewModel: MoviesViewModel
    private lateinit var adapterMovies: AdapterMovies
    private lateinit var btnGetAll: Button
    private lateinit var btnGetPopular: Button
    private lateinit var btnGetTopRated: Button
    private lateinit var btnGetUpComing: Button
    private lateinit var tvCategory: TextView
    private lateinit var rvMovies: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_peliculas)

        btnGetAll = findViewById(R.id.btnGetAll)
        btnGetPopular = findViewById(R.id.btnGetPopular)
        btnGetTopRated = findViewById(R.id.btnGetTopRated)
        btnGetUpComing = findViewById(R.id.btnGetUpcoming)
        tvCategory = findViewById(R.id.tvCategory)
        rvMovies = findViewById(R.id.rvMovies)


        // Inicialización del ViewModel

        viewModel = ViewModelProvider(this)[MoviesViewModel::class.java]

        // Configuración del RecyclerView

        setupRecyclerView()

        // Configuración inicial

        tvCategory.text = "PREMIER"
        viewModel.getAllMovies()

        // Observadores

        viewModel.moviesList.observe(this) {
            adapterMovies.moviesList = it
            adapterMovies.notifyDataSetChanged()
        }

        // Configuración de botones

        btnGetAll.setOnClickListener {
            tvCategory.text = "TODO"
            viewModel.getAllMovies()
        }


        btnGetPopular.setOnClickListener {
            tvCategory.text = "POPULAR"
            viewModel.getPopular()
        }


        btnGetUpComing.setOnClickListener {
            tvCategory.text = "UPCOMING"
            viewModel.getComing()
        }



        btnGetTopRated.setOnClickListener {
            tvCategory.text = "TOPRATED"
            viewModel.getTopRated()
        }


    }

    private fun setupRecyclerView() {
        val layoutManager = GridLayoutManager(this, 3)
        rvMovies.layoutManager = layoutManager
        adapterMovies = AdapterMovies(this, arrayListOf())
        rvMovies.adapter = adapterMovies
    }


}
