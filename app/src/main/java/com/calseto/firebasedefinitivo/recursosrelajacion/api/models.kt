package com.calseto.firebasedefinitivo.recursosrelajacion.api

import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object Constants {
    const val API_KEY= "1d3d9a40d262c7e72f286d168886de03"
    const val API_URL = "https://api.themoviedb.org/3/movie/"
    const val API_URL_IMAGE = "https://image.tmdb.org/t/p/w500"
    const val IMAGE_WIDTH  =  350
    const val IMAGE_HEIGHT = 350
}

data class MovieModel(
    @SerializedName("id") val id: String,
    @SerializedName("original_title") val title: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val image: String,
    @SerializedName("popularity") val popularity: String,
    @SerializedName("vote_average") val rating: String
)

data class MoviesResponse(
    @SerializedName("results") val results: List<MovieModel>
)


object RetrofitClient {

    val webService: WebService by lazy {
        Retrofit.Builder().baseUrl(Constants.API_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create())).build()
            .create(WebService::class.java)
    }

}


interface WebService {
    @GET("now_playing")
    suspend fun getAllMovies(
        @Query("api_key")  apiKey: String
    ): Response<MoviesResponse>

    @GET("popular")
    suspend fun getPopular(
        @Query("api_key")  apiKey: String
    ): Response<MoviesResponse>

    @GET("top_rated")
    suspend fun getTopRated(
        @Query("api_key")  apiKey: String
    ): Response<MoviesResponse>

    @GET("upcoming")
    suspend fun getUpcoming(
        @Query("api_key")  apiKey: String
    ): Response<MoviesResponse>

}

