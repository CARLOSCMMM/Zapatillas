package com.example.zapatillas

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.zapatillas.controller.Controller
import com.example.zapatillas.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var controller: Controller

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        iniciarApp()
    }

    private fun iniciarApp() {
        controller = Controller(this)
        controller.configurarRecyclerView(binding)
    }

    fun actualizarRecyclerView() {
        controller.actualizarRecyclerView()
    }
}
