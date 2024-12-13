package com.calseto.firebasedefinitivo.recursosrelajacion.api

import android.content.Context
import android.icu.text.CaseMap.Title
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.cardview.widget.CardView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.calseto.firebasedefinitivo.R
import okhttp3.Request

class AdapterMovies(val context: Context, var moviesList: List<MovieModel>) :
    RecyclerView.Adapter<AdapterMovies.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cvMovie = itemView.findViewById(R.id.cvMovie) as CardView
        val ivImage = itemView.findViewById(R.id.ivImage) as ImageView
        val tvInfo = itemView.findViewById(R.id.tvInfo) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rvmovie, parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount() = moviesList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = moviesList[position]


        Glide.with(context).load("${Constants.API_URL_IMAGE}${movie.image}")
            .apply(RequestOptions().override(Constants.IMAGE_WIDTH, Constants.IMAGE_HEIGHT))
            .into(holder.ivImage)

        holder.tvInfo.text = HtmlCompat.fromHtml(
            "<b>Title:</b> " +movie.title+
                    "<br><b>Popularity:</b>"+movie.popularity+
                    "<br><b>Rating:</b>"+movie.rating,
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )

        holder.cvMovie.setOnClickListener {
            showOverviw(movie.title, movie.overview)
        }
    }

    fun showOverviw(title: String, overview: String){
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(overview)
        builder.show()
    }

}