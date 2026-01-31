package com.example.zapatillas.domain.repositorio

import com.example.zapatillas.domain.model.Zapatilla

interface Repositorio {
    fun getZapatillas(): List<Zapatilla>
    fun addZapatilla(zapatilla: Zapatilla)
    fun updateZapatilla(old: Zapatilla, new: Zapatilla)
    fun deleteZapatilla(zapatilla: Zapatilla)
}