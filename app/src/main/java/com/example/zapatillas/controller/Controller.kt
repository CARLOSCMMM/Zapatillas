package com.example.zapatillas.controller

import android.content.Context
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zapatillas.adapter.AdapterZapatilla
import com.example.zapatillas.dao.DaoZapatilla
import com.example.zapatillas.databinding.ActivityMainBinding
import com.example.zapatillas.models.Zapatilla


class Controller(val context: Context) {

    lateinit var listaZapatillas: MutableList<Zapatilla>
    lateinit var adaptador: AdapterZapatilla

    init {
        inicializarDatos()
    }

    private fun inicializarDatos() {
        listaZapatillas = DaoZapatilla.miDao.obtenerZapatillas().toMutableList()
    }

    fun configurarRecyclerView(binding: ActivityMainBinding) {
        adaptador = AdapterZapatilla(
            listaZapatillas,
            { posicion -> borrarZapatilla(posicion) },
            { posicion -> editarZapatilla(posicion) }
        )

        binding.miRecyclerView.adapter = adaptador
        binding.miRecyclerView.layoutManager = LinearLayoutManager(context)

        binding.btnAgregar.setOnClickListener {
            agregarZapatilla()
        }
    }

    private fun borrarZapatilla(posicion: Int) {
        val zapatilla = listaZapatillas[posicion]

        listaZapatillas.removeAt(posicion)

        adaptador.notifyItemRemoved(posicion)
        adaptador.notifyItemRangeChanged(posicion, listaZapatillas.size)

        Toast.makeText(context, "Eliminada: ${zapatilla.nombre}", Toast.LENGTH_SHORT).show()
    }

    private fun editarZapatilla(posicion: Int) {
        val nombre = listaZapatillas[posicion].nombre
        Toast.makeText(context, "Próximamente: Editar $nombre", Toast.LENGTH_SHORT).show()
    }
//no hay boton para esta accion pero mas adelante se pondra uno para agregar zapatillas
    private fun agregarZapatilla() {
        Toast.makeText(context, "Próximamente: Añadir Zapatilla", Toast.LENGTH_SHORT).show()
    }
}