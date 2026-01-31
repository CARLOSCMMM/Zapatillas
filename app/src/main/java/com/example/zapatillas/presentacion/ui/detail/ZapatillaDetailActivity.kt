package com.example.zapatillas.presentacion.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.zapatillas.databinding.ActivityZapatillaDetailBinding
import com.example.zapatillas.domain.model.Zapatilla
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ZapatillaDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityZapatillaDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityZapatillaDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val zapatilla = intent.getParcelableExtra<Zapatilla>("ZAPATILLA")
        if (zapatilla != null) {
            binding.tvNombreDetail.text = zapatilla.nombre
            binding.tvMarcaDetail.text = zapatilla.marca
            binding.tvPrecioDetail.text = "${zapatilla.precio} €"
            Glide.with(this)
                .load(zapatilla.imagenUrl)
                .into(binding.ivZapatillaDetail)
        }

        binding.buttonVolver.setOnClickListener {
            finish()
        }
    }
}