package com.example.zapatillas.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.zapatillas.R
import com.example.zapatillas.models.Zapatilla

class AdapterZapatilla(
    var listaZapatillas: MutableList<Zapatilla>,
    val onDeleteClick: (Int) -> Unit,
    val onEditClick: (Int) -> Unit
) : RecyclerView.Adapter<ViewHolderZapatilla>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderZapatilla {
        val layoutInflater = LayoutInflater.from(parent.context)
        val layoutItem = R.layout.item_zapatilla
        return ViewHolderZapatilla(layoutInflater.inflate(layoutItem, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolderZapatilla, position: Int) {
        val item = listaZapatillas[position]
        holder.renderize(item, position, onDeleteClick, onEditClick)
    }

    override fun getItemCount(): Int = listaZapatillas.size
}

