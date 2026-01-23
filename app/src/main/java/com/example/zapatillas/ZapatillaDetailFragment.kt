package com.example.zapatillas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.zapatillas.databinding.FragmentZapatillaDetailBinding
import com.example.zapatillas.objects_models.Repositorio
import com.bumptech.glide.Glide

class ZapatillaDetailFragment : Fragment() {

    private var _binding: FragmentZapatillaDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentZapatillaDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val zapatillaId = arguments?.getInt("zapatillaId") ?: -1

        val zapatilla = Repositorio.listaZapatillas[zapatillaId]

        binding.textViewNombre.text = zapatilla.nombre
        binding.textViewMarca.text = zapatilla.marca
        binding.textViewPrecio.text = "€${zapatilla.precio}"
        Glide.with(requireContext())
            .load(zapatilla.imagenUrl)
            .centerCrop()
            .into(binding.imageViewZapatilla)

        binding.buttonBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
