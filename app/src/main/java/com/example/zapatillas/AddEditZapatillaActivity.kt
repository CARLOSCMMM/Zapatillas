package com.example.zapatillas

import android.app.Activity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.zapatillas.models.Zapatilla
import com.example.zapatillas.objects_models.Repositorio
import android.widget.Button


class AddEditZapatillaActivity : AppCompatActivity() {

    private lateinit var etNombre: EditText
    private lateinit var etMarca: EditText
    private lateinit var etPrecio: EditText
    private lateinit var etUrlImagen: EditText
    private var isEditMode = false
    private var zapatillaPosicion: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_zapatilla)

        etNombre = findViewById(R.id.etNombre)
        etMarca = findViewById(R.id.etMarca)
        etPrecio = findViewById(R.id.etPrecio)
        etUrlImagen = findViewById(R.id.etUrlImagen)

        zapatillaPosicion = intent.getIntExtra("ZAPATILLA_POSICION", -1)
        if (zapatillaPosicion != -1) {
            isEditMode = true
            val zapatilla = Repositorio.listaZapatillas[zapatillaPosicion!!]
            etNombre.setText(zapatilla.nombre)
            etMarca.setText(zapatilla.marca)
            etPrecio.setText(zapatilla.precio.toString())
            etUrlImagen.setText(zapatilla.imagenUrl)
        }

        findViewById<Button>(R.id.btnGuardar).setOnClickListener {
            val nombre = etNombre.text.toString()
            val marca = etMarca.text.toString()
            val precio = etPrecio.text.toString().toDouble()
            val urlImagen = etUrlImagen.text.toString()

            val nuevaZapatilla = Zapatilla(nombre, marca, precio, urlImagen)

            if (isEditMode) {
                Repositorio.editarZapatilla(zapatillaPosicion!!, nuevaZapatilla)
            } else {
                Repositorio.añadirZapatilla(nuevaZapatilla)
            }
            Toast.makeText(this, "Zapatilla guardada", Toast.LENGTH_SHORT).show()
            setResult(Activity.RESULT_OK)
            finish()
        }
    }
}
