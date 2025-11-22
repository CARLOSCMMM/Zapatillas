package com.example.zapatillas.models

class Zapatilla(
    var nombre: String,
    var marca: String,
    var precio: Double,
    var imagenUrl: String
) {
    override fun toString(): String {
        return "Zapatilla(nombre='$nombre', marca='$marca')"
    }
}