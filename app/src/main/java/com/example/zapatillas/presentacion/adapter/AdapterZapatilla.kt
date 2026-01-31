package com.example.zapatillas.presentacion.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.zapatillas.R
import com.example.zapatillas.domain.model.Zapatilla

class AdapterZapatilla(
    var listaZapatillas: List<Zapatilla>,
    val onDeleteClick: (Int) -> Unit,
    val onEditClick: (Int) -> Unit,
    val onItemClick: ((Int) -> Unit)? = null
) : RecyclerView.Adapter<ViewHolderZapatilla>() {

    fun updateList(newList: List<Zapatilla>) {
        listaZapatillas = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderZapatilla {
        val layoutInflater = LayoutInflater.from(parent.context)
        val layoutItem = R.layout.item_zapatilla
        return ViewHolderZapatilla(layoutInflater.inflate(layoutItem, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolderZapatilla, position: Int) {
        val item = listaZapatillas[position]
        holder.renderize(item, onDeleteClick, onEditClick, onItemClick)
    }

    override fun getItemCount(): Int = listaZapatillas.size
}
