package com.example.zapatillas.presentacion.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zapatillas.R
import com.example.zapatillas.databinding.FragmentZapatillaListBinding
import com.example.zapatillas.presentacion.adapter.AdapterZapatilla
import com.example.zapatillas.presentacion.ui.addEdit.AddEditZapatillaActivity
import com.example.zapatillas.presentacion.viewmodel.ZapatillaViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ZapatillaListFragment : Fragment() {

    private var _binding: FragmentZapatillaListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: AdapterZapatilla
    private val viewModel: ZapatillaViewModel by viewModels()

    private val addEditLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == android.app.Activity.RESULT_OK) {
            viewModel.refresh()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentZapatillaListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        viewModel.zapatillas.observe(viewLifecycleOwner) { list ->
            adapter.updateList(list)
        }

        binding.btnAgregar.setOnClickListener {
            agregarZapatilla()
        }
    }

    private fun setupRecyclerView() {
        adapter = AdapterZapatilla(
            emptyList(),
            { position -> deleteZapatilla(position) },
            { position -> editZapatilla(position) },
            { position -> showDetails(position) }
        )

        binding.recyclerViewZapatillas.adapter = adapter
        binding.recyclerViewZapatillas.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun deleteZapatilla(position: Int) {
        val zapatilla = adapter.listaZapatillas[position]
        viewModel.deleteZapatilla(zapatilla)
        Toast.makeText(requireContext(), "Eliminada: ${zapatilla.nombre}", Toast.LENGTH_SHORT).show()
    }

    private fun editZapatilla(position: Int) {
        val zapatilla = adapter.listaZapatillas[position]
        val intent = Intent(requireContext(), AddEditZapatillaActivity::class.java).apply {
            putExtra("ZAPATILLA", zapatilla)
        }
        addEditLauncher.launch(intent)
    }

    private fun showDetails(position: Int) {
        val bundle = Bundle().apply {
            putInt("zapatillaId", position)
        }
        findNavController().navigate(R.id.action_zapatillaListFragment_to_zapatillaDetailFragment, bundle)
    }

    private fun agregarZapatilla() {
        val intent = Intent(requireContext(), AddEditZapatillaActivity::class.java)
        addEditLauncher.launch(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}