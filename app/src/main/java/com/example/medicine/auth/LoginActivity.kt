package com.example.medicine.auth

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.medicine.MainActivity
import com.example.medicine.auth.view_models.LoginViewModel
import com.example.medicine.chemist.HomeActivity
import com.example.medicine.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var progressBar: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        progressBar = ProgressDialog(this)
        progressBar.setTitle("Logging you in")
        progressBar.setMessage("Wait while we log you in")

        loginViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))[LoginViewModel::class.java]


        loginViewModel.move.observe(this) {
            var intent: Intent
            if(it == 1){
                intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
            if(it == 2){
                intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            if(it == 0){
                Snackbar.make(binding.root, "Error", Snackbar.LENGTH_SHORT).show()
            }
        }

        binding.loginButton.setOnClickListener {
            progressBar.show()
            if(binding.emailEditText.editText?.text?.isEmpty() == true){
                Snackbar.make(binding.root, "Email should not be empty", Snackbar.LENGTH_SHORT).show()
                progressBar.dismiss()
            }
            if(binding.passwordEditText.editText?.text?.length!! < 6){
                Snackbar.make(binding.root, "Password length must be greater than 6 characters", Snackbar.LENGTH_SHORT).show()
                progressBar.dismiss()
            }
            else{
                loginViewModel.signIn(binding.emailEditText.editText!!.text.toString(), binding.passwordEditText.editText!!.text.toString())
                progressBar.dismiss()
            }
        }

        binding.newToApp.setOnClickListener {
            val intent = Intent(this, ChooseRoleActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}