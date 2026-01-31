package com.example.zapatillas.presentacion.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.zapatillas.databinding.ItemZapatillaBinding
import com.example.zapatillas.domain.model.Zapatilla

class ViewHolderZapatilla(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemZapatillaBinding.bind(view)

    fun renderize(
        zapatilla: Zapatilla,
        onDeleteClick: (Int) -> Unit,
        onEditClick: (Int) -> Unit,
        onItemClick: ((Int) -> Unit)?
    ) {
        binding.txtviewNombre.text = zapatilla.nombre
        binding.txtviewMarca.text = zapatilla.marca
        binding.txtviewPrecio.text = "${zapatilla.precio} €"
        Glide.with(itemView.context)
            .load(zapatilla.imagenUrl)
            .centerCrop()
            .into(binding.ivZapatilla)

        binding.btnDelete.setOnClickListener {
            onDeleteClick(adapterPosition)
        }

        binding.btnEdit.setOnClickListener {
            onEditClick(adapterPosition)
        }
        
        itemView.setOnClickListener {
            onItemClick?.invoke(adapterPosition)
        }
    }
}
