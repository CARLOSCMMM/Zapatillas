package com.example.zapatillas.presentacion.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.zapatillas.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        start()
    }

    private fun init() {
        auth = Firebase.auth
    }

    private fun start() {
        binding.buttonRegister.setOnClickListener {
            val email = binding.editTextEmail.text.toString().trim()
            val pass = binding.editTextPassword.text.toString()
            val repeatPass = binding.editTextRepeatPassword.text.toString()

            if (email.isEmpty() || pass.isEmpty() || repeatPass.isEmpty() || pass != repeatPass) {
                Toast.makeText(this, "Campos vacíos y/o contraseñas diferentes", Toast.LENGTH_LONG).show()
            } else {
                registerUser(email, pass) { result, msg ->
                    Toast.makeText(this@RegisterActivity, msg, Toast.LENGTH_LONG).show()
                    if (result) {
                        startActivityLogin()
                    }
                }
            }
        }

        binding.buttonBackToLogin.setOnClickListener {
            startActivityLogin()
        }
    }

    private fun startActivityLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun registerUser(email: String, pass: String, onResult: (Boolean, String) -> Unit) {
        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { taskAssign ->
                if (taskAssign.isSuccessful) {
                    val user = auth.currentUser
                    user?.sendEmailVerification()
                        ?.addOnCompleteListener { taskVerification ->
                            val msg = if (taskVerification.isSuccessful) {
                                "Usuario registrado. Verifique su correo"
                            } else {
                                "Usuario creado, pero no se pudo enviar la verificación: ${taskVerification.exception?.message}"
                            }
                            auth.signOut()
                            onResult(taskVerification.isSuccessful, msg)
                        }
                        ?.addOnFailureListener { exception ->
                            Log.e("RegisterActivity", "Fallo al enviar correo: ${exception.message}")
                            auth.signOut()
                            onResult(false, "No se pudo enviar el correo de verificación: ${exception.message}")
                        }
                } else {
                    val msg = try {
                        throw taskAssign.exception ?: Exception("Error desconocido")
                    } catch (e: FirebaseAuthUserCollisionException) {
                        "Ese usuario ya existe"
                    } catch (e: FirebaseAuthWeakPasswordException) {
                        "La contraseña es débil: ${e.reason}"
                    } catch (e: FirebaseAuthInvalidCredentialsException) {
                        "El email proporcionado no es válido"
                    } catch (e: Exception) {
                        e.message.toString()
                    }
                    onResult(false, msg)
                }
            }
    }
}
