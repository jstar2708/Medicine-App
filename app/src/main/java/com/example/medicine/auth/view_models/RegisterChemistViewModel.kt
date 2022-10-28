package com.example.medicine.auth.view_models

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.medicine.model.Chemist
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegisterChemistViewModel: ViewModel() {
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance("https://medicine-ffa6b-default-rtdb.firebaseio.com/")
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val action: MutableLiveData<Int> = MutableLiveData()
    val toast: MutableLiveData<Int> = MutableLiveData()

    fun registerChemist(chemist: Chemist, isPhoneNumberVerified: Boolean){
        if(!isPhoneNumberVerified){
            toast.value = 1
            return
        }
        auth.createUserWithEmailAndPassword(chemist.getEmail(), chemist.getPassword()).addOnCompleteListener {
            if(it.isSuccessful){
                chemist.setUserId(auth.currentUser?.uid.toString())
                toast.value = 2
                database.reference.child("Chemist").child(auth.uid.toString()).setValue(chemist)
                action.value = 1
            }
            else{
                toast.value = 3
                Log.d("Error", it.exception?.message.toString())
            }
        }
    }

}