package com.carlostorres.valmexdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var analytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        analytics = Firebase.analytics
        analytics.setUserProperty("Genero", "Hombre")

        btnRegistrar.setOnClickListener {
            if (etCorreo.text.isNotEmpty() && etContrasena.text.isNotEmpty()) {
            }
        }

        btnIniciar.setOnClickListener {
            if (etCorreo.text.isNotEmpty() && etContrasena.text.isNotEmpty()) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                    etCorreo.text.toString(),
                    etContrasena.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        //Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show()

                        val homeIntent = Intent(this, HomeActivity::class.java).apply {
                            putExtra("correo", etCorreo.text.toString())
                        }
                        startActivity(homeIntent)

                    } else {
                        Toast.makeText(this, "Usuario no registrado", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }


}