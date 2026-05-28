package com.pacheco.gestionfutbol.domain

import com.google.gson.annotations.SerializedName

data class Equipo(
    @SerializedName("id") val id: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("nombre_equipo") val nombreEquipo: String,
    @SerializedName("eslogan") val eslogan: String,
    @SerializedName("tecnico") val tecnico: String,
    @SerializedName("pais") val pais: String,
    @SerializedName("ciudad") val ciudad: String,
    @SerializedName("categoria") val categoria: String,

    // Usamos Int para los que son Number en MockAPI
    @SerializedName("goles") val goles: Int,
    @SerializedName("partidos_jugados") val partidosJugados: Int,
    @SerializedName("ganados") val ganados: Int,
    @SerializedName("campeonatos") val campeonatos: Int,
    @SerializedName("expulsiones") val expulsiones: Int,

    // Este quedó como String en la configuración de MockAPI
    @SerializedName("empates") val empates: String
)