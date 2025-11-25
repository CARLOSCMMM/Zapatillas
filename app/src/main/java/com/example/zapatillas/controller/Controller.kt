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

    /**
     * Inicializa la lista de zapatillas obteniéndolas desde el DAO.
     */
    private fun inicializarDatos() {
        listaZapatillas = DaoZapatilla.miDao.obtenerZapatillas().toMutableList()
    }

    /**
     * Configura el RecyclerView con el adaptador y el layout manager.
     * También configura el listener del botón para agregar nuevas zapatillas.
     */
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

    /**
     * Elimina una zapatilla de la lista en la posición especificada y notifica al adaptador.
     */
    private fun borrarZapatilla(posicion: Int) {
        val zapatilla = listaZapatillas[posicion]

        listaZapatillas.removeAt(posicion)

        adaptador.notifyItemRemoved(posicion)
        adaptador.notifyItemRangeChanged(posicion, listaZapatillas.size)

        Toast.makeText(context, "Eliminada: ${zapatilla.nombre}", Toast.LENGTH_SHORT).show()
    }

    /**
     * Muestra un mensaje Toast indicando que la función de editar zapatilla se implementará próximamente.
     */
    private fun editarZapatilla(posicion: Int) {
        val nombre = listaZapatillas[posicion].nombre
        Toast.makeText(context, "Próximamente: Editar $nombre", Toast.LENGTH_SHORT).show()
    }

    /**
     * Muestra un mensaje Toast indicando que la función de añadir zapatilla se implementará próximamente.
     */
    private fun agregarZapatilla() {
        Toast.makeText(context, "Próximamente: Añadir Zapatilla", Toast.LENGTH_SHORT).show()
    }
}