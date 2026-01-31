package com.example.zapatillas.presentacion.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zapatillas.domain.model.Zapatilla
import com.example.zapatillas.domain.usecase.AddZapatillaUseCase
import com.example.zapatillas.domain.usecase.DeleteZapatillaUseCase
import com.example.zapatillas.domain.usecase.GetZapatillasUseCase
import com.example.zapatillas.domain.usecase.UpdateZapatillaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ZapatillaViewModel @Inject constructor(
    private val getZapatillasUseCase: GetZapatillasUseCase,
    private val addZapatillaUseCase: AddZapatillaUseCase,
    private val updateZapatillaUseCase: UpdateZapatillaUseCase,
    private val deleteZapatillaUseCase: DeleteZapatillaUseCase
) : ViewModel() {

    private val _zapatillas = MutableLiveData<List<Zapatilla>>()
    val zapatillas: LiveData<List<Zapatilla>> = _zapatillas

    init {
        loadZapatillas()
    }

    private fun loadZapatillas() {
        _zapatillas.value = getZapatillasUseCase()
    }

    fun addZapatilla(zapatilla: Zapatilla) {
        addZapatillaUseCase(zapatilla)
        loadZapatillas()
    }

    fun updateZapatilla(old: Zapatilla, new: Zapatilla) {
        updateZapatillaUseCase(old, new)
        loadZapatillas()
    }

    fun deleteZapatilla(zapatilla: Zapatilla) {
        deleteZapatillaUseCase(zapatilla)
        loadZapatillas()
    }

    fun getZapatillaByIndex(index: Int): Zapatilla? = _zapatillas.value?.getOrNull(index)

    fun refresh() {
        loadZapatillas()
    }
}