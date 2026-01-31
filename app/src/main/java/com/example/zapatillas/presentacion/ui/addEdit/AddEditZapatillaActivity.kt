package com.example.zapatillas.presentacion.ui.addEdit

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.zapatillas.R
import com.example.zapatillas.domain.model.Zapatilla
import com.example.zapatillas.domain.usecase.AddZapatillaUseCase
import com.example.zapatillas.domain.usecase.UpdateZapatillaUseCase
import com.example.zapatillas.domain.usecase.DeleteZapatillaUseCase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddEditZapatillaActivity : AppCompatActivity() {

    @Inject
    lateinit var addZapatillaUseCase: AddZapatillaUseCase

    @Inject
    lateinit var updateZapatillaUseCase: UpdateZapatillaUseCase

    @Inject
    lateinit var deleteZapatillaUseCase: DeleteZapatillaUseCase

    private lateinit var etNombre: EditText
    private lateinit var etMarca: EditText
    private lateinit var etPrecio: EditText
    private lateinit var etUrlImagen: EditText
    private var isEditMode = false
    private var oldZapatilla: Zapatilla? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_zapatilla)

        etNombre = findViewById(R.id.etNombre)
        etMarca = findViewById(R.id.etMarca)
        etPrecio = findViewById(R.id.etPrecio)
        etUrlImagen = findViewById(R.id.etUrlImagen)

        oldZapatilla = intent.getParcelableExtra("ZAPATILLA")
        if (oldZapatilla != null) {
            isEditMode = true
            etNombre.setText(oldZapatilla!!.nombre)
            etMarca.setText(oldZapatilla!!.marca)
            etPrecio.setText(oldZapatilla!!.precio.toString())
            etUrlImagen.setText(oldZapatilla!!.imagenUrl)
        }

        findViewById<Button>(R.id.btnGuardar).setOnClickListener {
            val nombre = etNombre.text.toString()
            val marca = etMarca.text.toString()
            val precio = etPrecio.text.toString().toDoubleOrNull() ?: 0.0
            val urlImagen = etUrlImagen.text.toString()

            val nuevaZapatilla = if (isEditMode) {
                Zapatilla(oldZapatilla!!.id, nombre, marca, precio, urlImagen)
            } else {
                Zapatilla(0, nombre, marca, precio, urlImagen)
            }

            if (isEditMode) {
                updateZapatillaUseCase(oldZapatilla!!, nuevaZapatilla)
            } else {
                addZapatillaUseCase(nuevaZapatilla)
            }
            Toast.makeText(this, "Zapatilla guardada", Toast.LENGTH_SHORT).show()
            setResult(RESULT_OK)
            finish()
        }
    }
}