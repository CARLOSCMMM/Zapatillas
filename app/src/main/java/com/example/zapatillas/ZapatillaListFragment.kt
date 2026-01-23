package com.example.zapatillas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zapatillas.adapter.AdapterZapatilla
import com.example.zapatillas.databinding.FragmentZapatillaListBinding
import com.example.zapatillas.models.Zapatilla
import com.example.zapatillas.objects_models.Repositorio

class ZapatillaListFragment : Fragment() {

    private var _binding: FragmentZapatillaListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: AdapterZapatilla

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

    }

    private fun setupRecyclerView() {
        adapter = AdapterZapatilla(
            Repositorio.listaZapatillas,
            { position -> deleteZapatilla(position) },
            { position -> editZapatilla(position) },
            { position -> showDetails(position) }
        )

        binding.recyclerViewZapatillas.adapter = adapter
        binding.recyclerViewZapatillas.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun deleteZapatilla(position: Int) {
        val zapatilla = Repositorio.listaZapatillas[position]
        Repositorio.listaZapatillas.removeAt(position)
        adapter.notifyItemRemoved(position)
        adapter.notifyItemRangeChanged(position, Repositorio.listaZapatillas.size)
        Toast.makeText(requireContext(), "Eliminada: ${zapatilla.nombre}", Toast.LENGTH_SHORT).show()
    }

    private fun editZapatilla(position: Int) {
        // Aquí puedes implementar la edición, por ahora solo toast
        Toast.makeText(requireContext(), "Editar ${position}", Toast.LENGTH_SHORT).show()
    }

    private fun showDetails(position: Int) {
        val bundle = Bundle().apply {
            putInt("zapatillaId", position)
        }
        findNavController().navigate(R.id.action_zapatillaListFragment_to_zapatillaDetailFragment, bundle)
    }

    private fun agregarZapatilla() {
        // Functionality to add a sneaker will be implemented later
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}