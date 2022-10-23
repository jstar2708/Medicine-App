package com.example.medicine.auth.view_models

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel: ViewModel(){
    private lateinit var auth: FirebaseAuth
    var move: MutableLiveData<Int> = MutableLiveData(0)

    fun signIn(email: String, password: String, who: Int){
        auth = FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if(it.isSuccessful){
                move.value = if(who == 1){ 1 } else if(who == 2){ 2 } else{ 0 }
            }
            else{
                Log.d("LOGIN_ERROR", it.exception?.message.toString())
            }
        }


    }
}