package com.example.zapatillas.dao

import com.example.zapatillas.models.Zapatilla
import com.example.zapatillas.objects_models.Repositorio

class DaoZapatilla private constructor() {
    companion object {
        val miDao: DaoZapatilla by lazy {
            DaoZapatilla()
        }
    }

    fun obtenerZapatillas(): List<Zapatilla> = Repositorio.listaZapatillas
}