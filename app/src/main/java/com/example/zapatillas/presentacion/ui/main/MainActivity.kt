package com.example.zapatillas.presentacion.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zapatillas.presentacion.adapter.AdapterZapatilla
import com.example.zapatillas.presentacion.ui.addEdit.AddEditZapatillaActivity
import com.example.zapatillas.presentacion.ui.detail.ZapatillaDetailActivity
import com.example.zapatillas.R
import com.example.zapatillas.databinding.ActivityMainBinding
import com.example.zapatillas.presentacion.ui.login.LoginActivity
import com.example.zapatillas.presentacion.viewmodel.ZapatillaViewModel
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: ZapatillaViewModel by viewModels()
    private lateinit var adapter: AdapterZapatilla

    private val addEditLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            viewModel.refresh()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navigationView: NavigationView = findViewById(R.id.navigation_view)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)

        val headerView = navigationView.getHeaderView(0)
        val textViewUserName = headerView.findViewById<TextView>(R.id.textViewUserName)
        textViewUserName.text = "Carlos"

        navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.logout -> {

                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else -> {
                    drawerLayout.closeDrawer(GravityCompat.START)
                }
            }
            true
        }

        setupRecyclerView()

        viewModel.zapatillas.observe(this) { list ->
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

        binding.viewZapatillas.adapter = adapter
        binding.viewZapatillas.layoutManager = LinearLayoutManager(this)
    }

    private fun deleteZapatilla(position: Int) {
        val zapatilla = adapter.listaZapatillas[position]
        viewModel.deleteZapatilla(zapatilla)
    }

    private fun editZapatilla(position: Int) {
        val zapatilla = adapter.listaZapatillas[position]
        val intent = Intent(this, AddEditZapatillaActivity::class.java).apply {
            putExtra("ZAPATILLA", zapatilla)
        }
        addEditLauncher.launch(intent)
    }

    private fun showDetails(position: Int) {
        val zapatilla = adapter.listaZapatillas[position]
        val intent = Intent(this, ZapatillaDetailActivity::class.java).apply {
            putExtra("ZAPATILLA", zapatilla)
        }
        startActivity(intent)
    }

    private fun agregarZapatilla() {
        val intent = Intent(this, AddEditZapatillaActivity::class.java)
        addEditLauncher.launch(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_options, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_logout -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
                return true
            }
            R.id.menu_settings -> {
                // añadimos la funcionalidad mas tarde
                return true
            }
            R.id.menu_search -> {
                // añadimos la funcionalidad mas tarde
                return true
            }
            android.R.id.home -> {
                val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
                drawerLayout.openDrawer(GravityCompat.START)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}