package com.example.zapatillas.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Zapatilla(
    val id: Int,
    var nombre: String,
    var marca: String,
    var precio: Double,
    var imagenUrl: String
) : Parcelable