package com.example.zapatillas

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.zapatillas.databinding.ActivityZapatillaDetailBinding
import com.example.zapatillas.objects_models.Repositorio

class ZapatillaDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityZapatillaDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityZapatillaDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val position = intent.getIntExtra("ZAPATILLA_POSICION", -1)
        if (position != -1) {
            val zapatilla = Repositorio.listaZapatillas[position]
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
