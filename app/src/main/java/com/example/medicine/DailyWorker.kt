package com.example.medicine

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.medicine.model.Medicine
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*

class DailyWorker(private val context: Context, workerParameters: WorkerParameters): Worker(context, workerParameters) {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance("https://medicine-ffa6b-default-rtdb.firebaseio.com/")
    private val notificationManager: NotificationManager = applicationContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    private val CHANNEL_ID = "My_channel"
    override fun doWork(): Result {
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        database.reference.child("Customer").child(auth.currentUser?.uid.toString())
            .child("My_Medicine").addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val currentDate = "${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(Calendar.MONTH)+1}/${calendar.get(Calendar.YEAR)}"
                    snapshot.children.forEach { med ->
                        med.getValue(Medicine::class.java).let {
                            if(it?.getExpiryDate() == currentDate){

                                makeNotification(it.getName(), it.getManufactureDate(), it.getMedicineId())
                             //   moveToExpired(it.getMedicineId(), it)

                            }
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
        return Result.success()
    }

    fun makeNotification(medicineName: String, manufactureDate: String, medicineId: String){
        val notification: Notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notification = NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.expiry)
                .setContentText("Hey your medicine $medicineName manufactured on $manufactureDate is expired!")
                .setContentTitle("Medicine Expired")
                .setChannelId(CHANNEL_ID)
                .setStyle(NotificationCompat.BigTextStyle().bigText("Hey your medicine $medicineName manufactured on $manufactureDate is expired!"))
                .build()

            notificationManager.createNotificationChannel(NotificationChannel(CHANNEL_ID, "New Channel", NotificationManager.IMPORTANCE_HIGH))
        } else {
            notification = NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.expiry)
                .setContentText("Hey your medicine $medicineName manufactured on $manufactureDate is expired!")
                .setContentTitle("Medicine Expired")
                .setStyle(NotificationCompat.BigTextStyle().bigText("Hey your medicine $medicineName manufactured on $manufactureDate is expired!"))
                .build()
        }

        notificationManager.notify(100, notification)
    }

    private fun moveToExpired(id: String, medicine: Medicine){
        database.reference.child("Customer").child(auth.currentUser?.uid.toString())
            .child("My_Medicine").child(id).setValue(null)

        val newId: String = database.reference.child("Customer").child(auth.currentUser?.uid.toString())
            .child("Expired").push().key.toString()

        medicine.setMedicineId(newId)

        database.reference.child("Customer").child(auth.currentUser?.uid.toString())
            .child("Expired").child(newId).setValue(medicine)

    }
}