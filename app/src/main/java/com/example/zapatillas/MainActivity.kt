package com.example.zapatillas

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.zapatillas.LoginActivity
import com.example.zapatillas.R
import com.google.android.material.navigation.NavigationView
import com.example.zapatillas.databinding.ActivityMainBinding
import com.example.zapatillas.controller.Controller

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
    }

    private fun iniciarApp() {
        controller = Controller(this)
        controller.configurarRecyclerView(binding)
    }

    fun actualizarRecyclerView() {
        controller.actualizarRecyclerView()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (item.itemId == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
