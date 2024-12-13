package com.calseto.firebasedefinitivo.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.calseto.firebasedefinitivo.R
import com.calseto.firebasedefinitivo.recursosrelajacion.Recurso

class RecursoAdapter(private var listaRecursos: MutableList<Recurso>) : RecyclerView.Adapter<RecursoAdapter.RecursoViewHolder>() {

    class RecursoViewHolder(itemView: android.view.View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageViewRecurso)
        val txtDescripcion: TextView = itemView.findViewById(R.id.txtDescripcion)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecursoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recurso, parent, false)
        return RecursoViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecursoViewHolder, position: Int) {
        val recurso = listaRecursos[position]
        holder.txtDescripcion.text = recurso.descripcion

        Glide.with(holder.itemView.context)
            .load(recurso.imageUrl)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int = listaRecursos.size

    fun updateData(nuevosRecursos: List<Recurso>) {
        listaRecursos.clear()
        listaRecursos.addAll(nuevosRecursos)
        notifyDataSetChanged()
    }
}

