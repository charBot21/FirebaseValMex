package com.carlostorres.valmexdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.ads.MobileAds
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        MobileAds.initialize(this, "ca-app-pub-9760815815309758~1754574761")

        val firebaseConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 60
        }
        firebaseConfig.setConfigSettingsAsync(configSettings)
        firebaseConfig.setDefaultsAsync(mapOf("show_button" to false, "error_text" to "Error"))

        Firebase.remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
            if ( task.isSuccessful ) {
                val errorText = Firebase.remoteConfig.getString("error_text")
                val show = Firebase.remoteConfig.getBoolean("show_button")

                if ( show ) {
                    Toast.makeText(this, "${errorText}", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Error al conectar", Toast.LENGTH_SHORT).show()
                }

            }
        }

        val getPutExtra = intent.extras
        val email = getPutExtra?.getString("correo")

        btnGuardar.setOnClickListener {
            if ( etMensaje.text.isNotEmpty() ) {
                db.collection("usuarios").document(email!!).set(
                    hashMapOf("provider" to "ValMex", "mensaje" to etMensaje.text.toString() )
                )
            }
        }

        btnObtener.setOnClickListener {
            db.collection("usuarios").document(email!!).get().addOnSuccessListener {
                Toast.makeText(this, "${it.get("mensaje")}", Toast.LENGTH_SHORT).show()
            }
        }

    }
}