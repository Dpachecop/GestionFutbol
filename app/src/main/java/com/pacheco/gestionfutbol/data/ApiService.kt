package com.pacheco.gestionfutbol.data

import com.pacheco.gestionfutbol.domain.Equipo
import com.pacheco.gestionfutbol.domain.Usuario
import retrofit2.http.*

interface ApiService {
    @GET("equipos")
    suspend fun getEquipos(): List<Equipo>

    @POST("equipos")
    suspend fun createEquipo(@Body equipo: Equipo): Equipo


    @PUT("equipos/{id}")
    suspend fun updateEquipo(@Path("id") id: String, @Body equipo: Equipo): Equipo

    @DELETE("equipos/{id}")
    suspend fun deleteEquipo(@Path("id") id: String): Equipo

    @GET("usuarios")
    suspend fun loginUsuario(@Query("correo") correo: String, @Query("contrasenia") contrasenia: String): List<Usuario>

    @POST("usuarios")
    suspend fun createUsuario(@Body usuario: Usuario): Usuario
}