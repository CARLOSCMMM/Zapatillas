package com.example.zapatillas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.zapatillas.databinding.FragmentZapatillaDetailBinding
import com.example.zapatillas.objects_models.Repositorio
class ZapatillaDetailFragment : Fragment() {

    private var zapatillaId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentZapatillaDetailBinding.inflate(inflater, container, false)

        zapatillaId = arguments?.getInt("ZAPATILLA_ID")

        val zapatilla = Repositorio.listaZapatillas[zapatillaId ?: -1]

        binding.textViewNombre.text = zapatilla.nombre
        binding.textViewMarca.text = zapatilla.marca
        binding.textViewPrecio.text = "€${zapatilla.precio}"

        return binding.root
    }

    companion object {
        fun newInstance(zapatillaId: Int): ZapatillaDetailFragment {
            val fragment = ZapatillaDetailFragment()
            val args = Bundle()
            args.putInt("ZAPATILLA_ID", zapatillaId)
            fragment.arguments = args
            return fragment
        }
    }
}
