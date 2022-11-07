package com.example.medicine.auth.view_models

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.medicine.model.Chemist
import com.example.medicine.model.Customer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LoginViewModel: ViewModel(){
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    var move: MutableLiveData<Int> = MutableLiveData(100)
    private val database = FirebaseDatabase.getInstance("https://medicine-ffa6b-default-rtdb.firebaseio.com/")

    fun signIn(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { it ->
            if(it.isSuccessful){
                database.reference.child("Chemist").addValueEventListener(object : ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.children.forEach {
                            if(auth.currentUser?.uid.toString() ==it.getValue(Chemist::class.java)?.getUserId()){
                                move.value = 1
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                })

                database.reference.child("Customer").addValueEventListener(object : ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.children.forEach {
                            if (auth.currentUser?.uid.toString() == it.getValue(Customer::class.java)?.getUserId()){
                                move.value = 2
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                })
            }
            else{
                Log.d("LOGIN_ERROR", it.exception?.message.toString())
            }
        }
    }

}