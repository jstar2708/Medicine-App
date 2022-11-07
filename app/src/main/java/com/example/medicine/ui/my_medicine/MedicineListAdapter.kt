package com.example.medicine.ui.my_medicine

import android.view.*
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.medicine.R
import com.example.medicine.model.Medicine

class MedicineListAdapter(private val listener: OnDeleteButtonClick): RecyclerView.Adapter<MedicineViewHolder>() {
    private var arrayList = ArrayList<Medicine>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.medicine_view, parent, false)
        val holder = MedicineViewHolder(view)
        val popupMenu = PopupMenu(parent.context,view)
        val inflater = popupMenu.menuInflater
        popupMenu.setOnMenuItemClickListener {
            if(it.itemId == R.id.delete){
                listener.onClick(arrayList[holder.adapterPosition])
                arrayList.removeAt(holder.adapterPosition)
            }
            true
        }
        inflater.inflate(R.menu.medicine_menu, popupMenu.menu)
        holder.medicineMenu.setOnClickListener {
            popupMenu.show()
        }
        return holder
    }

    override fun onBindViewHolder(holder: MedicineViewHolder, position: Int) {
        holder.medicineName.text = arrayList[position].getName()
        holder.manufactureDate.text = arrayList[position].getManufactureDate()
        holder.expiryDate.text = arrayList[position].getExpiryDate()
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    fun updateDate(list: ArrayList<Medicine>){
        arrayList.clear()
        arrayList.addAll(list)

        this.notifyDataSetChanged()
    }

}

class MedicineViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    val medicineName: TextView = itemView.findViewById(R.id.medicineName)
    val manufactureDate: TextView = itemView.findViewById(R.id.manufactureDate)
    val expiryDate: TextView = itemView.findViewById(R.id.expiryDate)
    val medicineMenu: ImageButton = itemView.findViewById(R.id.medicineMenu)
}

interface OnDeleteButtonClick{
    fun onClick(medicine: Medicine)
}