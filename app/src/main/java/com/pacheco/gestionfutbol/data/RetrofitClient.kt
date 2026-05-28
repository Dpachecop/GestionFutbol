package com.pacheco.gestionfutbol.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    // La URL base que vimos en tu MockAPI
    private const val BASE_URL = "https://6a15eab891ff9a63de08fad9.mockapi.io/"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // Transforma JSON a las Data Classes
            .build()
            .create(ApiService::class.java)
    }
}