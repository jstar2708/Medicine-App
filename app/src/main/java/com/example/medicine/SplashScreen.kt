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
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance("https://medicine-ffa6b-default-rtdb.firebaseio.com/")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        var intent = Intent(this, LoginActivity::class.java)

        if(firebaseAuth.currentUser != null){
            database.reference.child("Chemist").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach {
                        if(firebaseAuth.currentUser?.uid.toString() ==it.getValue(Chemist::class.java)?.getUserId()){
                            intent = Intent(this@SplashScreen, HomeActivity::class.java)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })

            database.reference.child("Customer").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach {
                        if (firebaseAuth.currentUser?.uid.toString() == it.getValue(Customer::class.java)?.getUserId()){
                            intent = Intent(this@SplashScreen, MainActivity::class.java)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
        }

        Handler().postDelayed({
            startActivity(intent)
        }, 5000)

    }
}