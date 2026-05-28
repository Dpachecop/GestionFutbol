package com.pacheco.gestionfutbol.domain

import com.google.gson.annotations.SerializedName

data class Usuario(
    @SerializedName("id")
    val id: String,

    @SerializedName("createdAt")
    val createdAt: String,

    @SerializedName("usuario")
    val usuario: String,

    @SerializedName("correo")
    val correo: String,

    @SerializedName("contrasenia")
    val contrasenia: String
)