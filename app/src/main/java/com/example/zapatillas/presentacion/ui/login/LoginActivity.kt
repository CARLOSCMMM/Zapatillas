package com.example.zapatillas.presentacion.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.zapatillas.databinding.ActivityLoginBinding
import com.example.zapatillas.presentacion.ui.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        start()
    }

    private fun init() {
        auth = Firebase.auth
    }

    private fun start() {
        binding.buttonLogin.setOnClickListener {
            val email = binding.editTextUser.text.toString().trim()
            val password = binding.editTextPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                startLogin(email, password) { result, msg ->
                    Toast.makeText(this@LoginActivity, msg, Toast.LENGTH_LONG).show()
                    if (result) {
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            } else {
                Toast.makeText(this, "Por favor, ingrese email y contraseña", Toast.LENGTH_SHORT).show()
            }
        }

        binding.buttonRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.buttonForgotPassword.setOnClickListener {
            val email = binding.editTextUser.text.toString().trim()
            if (email.isNotEmpty()) {
                recoverPassword(email) { result, msg ->
                    Toast.makeText(this@LoginActivity, msg, Toast.LENGTH_LONG).show()
                    if (!result) {
                        binding.editTextUser.setText("")
                    }
                }
            } else {
                Toast.makeText(this, "Debes rellenar el campo email", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun recoverPassword(email: String, onResult: (Boolean, String) -> Unit) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { taskResetEmail ->
                if (taskResetEmail.isSuccessful) {
                    onResult(true, "Acabamos de enviarte un email para recuperar la contraseña")
                } else {
                    val msg = try {
                        throw taskResetEmail.exception ?: Exception("Error de reseteo inesperado")
                    } catch (e: FirebaseAuthInvalidCredentialsException) {
                        "El formato del email es incorrecto"
                    } catch (e: Exception) {
                        e.message.toString()
                    }
                    onResult(false, msg)
                }
            }
    }

    private fun startLogin(user: String, pass: String, onResult: (Boolean, String) -> Unit) {
        auth.signInWithEmailAndPassword(user, pass)
            .addOnCompleteListener { taskSignIn ->
                if (taskSignIn.isSuccessful) {
                    val possibleUser = auth.currentUser
                    if (possibleUser?.isEmailVerified == true) {
                        onResult(true, "Usuario logueado satisfactoriamente")
                    } else {
                        auth.signOut()
                        onResult(false, "Debes verificar tu correo antes de loguearte")
                    }
                } else {
                    val msg = try {
                        throw taskSignIn.exception ?: Exception("Error desconocido")
                    } catch (e: FirebaseAuthInvalidUserException) {
                        "El usuario tiene problemas por haberse borrado o deshabilitado"
                    } catch (e: FirebaseAuthInvalidCredentialsException) {
                        if (e.message?.contains("There is no user record corresponding to this identifier") == true) {
                            "El usuario no existe"
                        } else {
                            "Contraseña incorrecta"
                        }
                    } catch (e: Exception) {
                        e.message.toString()
                    }

                    onResult(false, msg)
                }
            }
    }
}
