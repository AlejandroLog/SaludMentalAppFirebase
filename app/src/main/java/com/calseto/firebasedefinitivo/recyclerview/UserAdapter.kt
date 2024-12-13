package com.calseto.firebasedefinitivo.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.calseto.firebasedefinitivo.R
import com.calseto.firebasedefinitivo.usuarios.User

class UserAdapter(
    private val currentUserUid: String,
    private val onClick: (User) -> Unit,
    private val onDelete: (User) -> Unit
) : ListAdapter<User, UserAdapter.UserViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_usuario, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user, currentUserUid, onClick, onDelete)
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvNombre: TextView = itemView.findViewById(R.id.tvNombre)
        private val ivFoto: ImageView = itemView.findViewById(R.id.ivFoto)
        private val btnEliminar: Button = itemView.findViewById(R.id.btnEliminar)

        fun bind(
            user: User,
            currentUserUid: String,
            onClick: (User) -> Unit,
            onDelete: (User) -> Unit
        ) {
            tvNombre.text = user.nombre
            Glide.with(itemView.context).load(user.imageUrl).into(ivFoto)

            // Deshabilitar el botón si es el usuario autenticado
            if (user.userId == currentUserUid) {
                btnEliminar.isEnabled = false
                btnEliminar.text = "No disponible" // Mensaje opcional
            } else {
                btnEliminar.isEnabled = true
                btnEliminar.text = "Eliminar"
            }

            // Configurar clic en eliminar
            btnEliminar.setOnClickListener { onDelete(user) }

            // Configurar clic en el resto del item
            itemView.setOnClickListener { onClick(user) }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean = oldItem.userId == newItem.userId
        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean = oldItem == newItem
    }
}



