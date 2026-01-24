package com.example.zapatillas

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.zapatillas.controller.Controller
import com.example.zapatillas.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var controller: Controller

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


        iniciarApp()

        binding.btnAgregar.setOnClickListener {
            agregarZapatilla()
        }
    }

    private fun agregarZapatilla() {
        val intent = Intent(this, AddEditZapatillaActivity::class.java)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_options, menu)
        return true
    }
    private fun iniciarApp() {
        controller = Controller(this)
        controller.configurarRecyclerView(binding)
    }

    fun actualizarRecyclerView() {
        controller.actualizarRecyclerView()
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
                // Settings functionality will be added later
                return true
            }
            R.id.menu_search -> {
                // Search functionality will be added later
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