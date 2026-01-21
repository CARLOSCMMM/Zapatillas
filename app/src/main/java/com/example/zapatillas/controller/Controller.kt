package com.example.zapatillas.controller

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zapatillas.AddEditZapatillaActivity
import com.example.zapatillas.MainActivity
import com.example.zapatillas.adapter.AdapterZapatilla
import com.example.zapatillas.databinding.ActivityMainBinding
import com.example.zapatillas.models.Zapatilla
import com.example.zapatillas.objects_models.Repositorio


class Controller(private val mainActivity: MainActivity) {

    lateinit var adaptador: AdapterZapatilla

    private val addEditLauncher = mainActivity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            actualizarRecyclerView()
        }
    }

    fun configurarRecyclerView(binding: ActivityMainBinding) {
        adaptador = AdapterZapatilla(
            Repositorio.listaZapatillas,
            { posicion -> borrarZapatilla(posicion) },
            { posicion -> editarZapatilla(posicion) }
        )

        binding.miRecyclerView.adapter = adaptador
        binding.miRecyclerView.layoutManager = LinearLayoutManager(mainActivity)
    }

    private fun borrarZapatilla(posicion: Int) {
        val zapatilla = Repositorio.listaZapatillas[posicion]
        Repositorio.listaZapatillas.removeAt(posicion)
        adaptador.notifyItemRemoved(posicion)
        adaptador.notifyItemRangeChanged(posicion, Repositorio.listaZapatillas.size)

        Toast.makeText(mainActivity, "Eliminada: ${zapatilla.nombre}", Toast.LENGTH_SHORT).show()
    }

    private fun editarZapatilla(posicion: Int) {
        val zapatilla = Repositorio.listaZapatillas[posicion]


        val intent = Intent(mainActivity, AddEditZapatillaActivity::class.java)
        intent.putExtra("ZAPATILLA_POSICION", posicion)
        intent.putExtra("ZAPATILLA_NOMBRE", zapatilla.nombre)
        intent.putExtra("ZAPATILLA_MARCA", zapatilla.marca)
        intent.putExtra("ZAPATILLA_PRECIO", zapatilla.precio)
        intent.putExtra("ZAPATILLA_IMAGEN_URL", zapatilla.imagenUrl)

        addEditLauncher.launch(intent)
    }

    private fun agregarZapatilla() {
        val intent = Intent(mainActivity, AddEditZapatillaActivity::class.java)
        addEditLauncher.launch(intent)
    }

    fun actualizarRecyclerView() {
        adaptador.notifyDataSetChanged()
    }
}
