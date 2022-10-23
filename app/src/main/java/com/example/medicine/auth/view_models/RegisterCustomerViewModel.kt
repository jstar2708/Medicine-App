package com.example.medicine.auth.view_models

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.medicine.model.Customer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegisterCustomerViewModel: ViewModel() {
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance("https://medicine-ffa6b-default-rtdb.firebaseio.com/")
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val toast: MutableLiveData<Int> = MutableLiveData(0)
    val action: MutableLiveData<Int> = MutableLiveData(0)

    fun registerCustomer(customer: Customer, isPhoneNumberVerified: Boolean){
        if(!isPhoneNumberVerified){
            toast.value = 1
            return
        }
        auth.createUserWithEmailAndPassword(customer.getEmail(), customer.getPassword()).addOnCompleteListener {
            if(it.isSuccessful){
                toast.value = 2
                customer.setUserId(auth.currentUser?.uid.toString())
                database.reference.child("Customer").child(auth.uid.toString()).setValue(customer)
                action.value = 1
            }
            else{
                toast.value = 3
                Log.d("Error", it.exception?.message.toString())
            }
        }
    }
}