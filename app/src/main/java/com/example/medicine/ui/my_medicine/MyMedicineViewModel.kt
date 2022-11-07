package com.example.medicine.ui.my_medicine

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.medicine.DailyWorker
import com.example.medicine.model.Medicine
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.concurrent.TimeUnit

class MyMedicineViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance("https://medicine-ffa6b-default-rtdb.firebaseio.com/")
    public var toast: MutableLiveData<Int> = MutableLiveData(0)
    val list: MutableLiveData<ArrayList<Medicine>> = MutableLiveData(ArrayList())

    fun initializeExpiryWork(context: Context){
        if(auth.currentUser != null){
            val periodicWorkRequest = PeriodicWorkRequest.Builder(DailyWorker::class.java,15, TimeUnit.MINUTES).addTag(auth.currentUser?.uid.toString()).build()
            WorkManager.getInstance(context).enqueueUniquePeriodicWork(auth.currentUser!!.uid, ExistingPeriodicWorkPolicy.KEEP, periodicWorkRequest)
        }
    }

    fun addMedicineToList(medicine: Medicine){
        val medicineId: String = database.reference.child("Customer").child(auth.currentUser?.uid.toString()).child("My_Medicine").push().key.toString()
        medicine.setMedicineId(medicineId)
        database.reference.child("Customer").child(auth.currentUser?.uid.toString()).child("My_Medicine").child(medicineId).setValue(medicine).addOnCompleteListener {
            if (it.isSuccessful){
                toast.value = 1
            }
            else{
                toast.value = 2
            }
        }
    }

    fun getMedicineList() {
        database.reference.child("Customer").child(auth.currentUser?.uid.toString()).child("My_Medicine").addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val arrayList = ArrayList<Medicine>()
                snapshot.children.forEach {
                    it.getValue(Medicine::class.java)?.let { it1 -> arrayList.add(it1) }
                }
                list.value = arrayList

            }

            override fun onCancelled(error: DatabaseError) {
                toast.value = 3
            }
        })
    }

    fun deleteMedicine(medicineId: String) {
        database.reference.child("Customer").child(auth.currentUser?.uid.toString()).child("My_Medicine").child(medicineId).setValue(null).addOnCompleteListener {
            if(it.isSuccessful){
                toast.value = 4
            }
            else{
                toast.value = 5
            }
        }
    }
}