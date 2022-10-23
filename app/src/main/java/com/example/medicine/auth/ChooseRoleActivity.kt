package com.example.medicine.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.medicine.databinding.ActivityChooseRoleBinding

class ChooseRoleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChooseRoleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseRoleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var intent: Intent
        binding.chemist.setOnClickListener {
            intent = Intent(this, RegisterChemistActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.customer.setOnClickListener {
            intent = Intent(this, RegisterCustomerActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}