package com.example.zapatillas.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.zapatillas.databinding.ItemZapatillaBinding
import com.example.zapatillas.models.Zapatilla

class ViewHolderZapatilla(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemZapatillaBinding.bind(view)

    fun renderize(
        zapatilla: Zapatilla,
        position: Int,
        onDeleteClick: (Int) -> Unit,
        onEditClick: (Int) -> Unit
    ) {
        binding.txtviewNombre.text = zapatilla.nombre
        binding.txtviewMarca.text = zapatilla.marca
        binding.txtviewPrecio.text = "${zapatilla.precio} €"

        binding.txtviewModelo.text = "Modelo Exclusivo"

        Glide.with(itemView.context)
            .load(zapatilla.imagenUrl)
            .centerCrop()
            .into(binding.ivZapatilla)

        binding.btnDelete.setOnClickListener {
            onDeleteClick(position)
        }

        // Al pulsar editar
        binding.btnEdit.setOnClickListener {
            onEditClick(position)
        }
    }
}