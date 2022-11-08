package com.example.medicine

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.medicine.auth.LoginActivity
import com.example.medicine.chemist.HomeActivity
import com.example.medicine.model.Chemist
import com.example.medicine.model.Customer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar?.hide()

        var intent = Intent(this, LoginActivity::class.java)

        Handler().postDelayed({
            startActivity(intent)
            finish()
        }, 3000)
    }
}