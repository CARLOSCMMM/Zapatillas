package com.example.zapatillas.presentacion.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.zapatillas.databinding.FragmentZapatillaDetailBinding
import com.example.zapatillas.presentacion.viewmodel.ZapatillaViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ZapatillaDetailFragment : Fragment() {

    private var _binding: FragmentZapatillaDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ZapatillaViewModel by viewModels()

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
        val zapatilla = viewModel.getZapatillaByIndex(zapatillaId)

        if (zapatilla != null) {
            binding.tvNombreDetail.text = zapatilla.nombre
            binding.tvMarcaDetail.text = zapatilla.marca
            binding.tvPrecioDetail.text = "${zapatilla.precio} €"
            Glide.with(this)
                .load(zapatilla.imagenUrl)
                .into(binding.ivZapatillaDetail)
        }

        binding.buttonVolver.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}