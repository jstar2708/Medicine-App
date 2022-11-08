package com.example.medicine.ui.expired

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.medicine.model.Medicine
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ExpiredViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance("https://medicine-ffa6b-default-rtdb.firebaseio.com/")
    var toast: MutableLiveData<Int> = MutableLiveData(0)
    var expiredMedicineList: MutableLiveData<ArrayList<Medicine>> = MutableLiveData(ArrayList())

    fun getExpiredMedicine(){
        database.reference.child("Customer").child(auth.currentUser?.uid.toString()).child("Expired").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = ArrayList<Medicine>()
                snapshot.children.forEach {
                    it.getValue(Medicine::class.java)?.let { it1 -> list.add(it1) }
                }
                expiredMedicineList.value = list
            }

            override fun onCancelled(error: DatabaseError) {
                toast.value = 1
            }
        })
    }

    fun deleteMedicine(medicineId: String) {
        database.reference.child("Customer").child(auth.currentUser?.uid.toString()).child("Expired").child(medicineId).setValue(null)
    }
}