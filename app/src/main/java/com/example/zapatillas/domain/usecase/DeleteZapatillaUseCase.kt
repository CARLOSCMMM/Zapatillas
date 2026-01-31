package com.example.zapatillas.domain.usecase

import com.example.zapatillas.domain.model.Zapatilla
import com.example.zapatillas.domain.repositorio.Repositorio
import javax.inject.Inject

class DeleteZapatillaUseCase @Inject constructor(private val repositorio: Repositorio) {
    operator fun invoke(zapatilla: Zapatilla) {
        repositorio.deleteZapatilla(zapatilla)
    }
}