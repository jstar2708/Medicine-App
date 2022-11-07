package com.example.medicine.ui.my_medicine

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.WorkManager
import com.example.medicine.DailyWorker
import com.example.medicine.R
import com.example.medicine.databinding.FragmentMyMedicineBinding
import com.example.medicine.model.Medicine
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import java.util.*
import java.util.concurrent.TimeUnit

class MyMedicineFragment : Fragment(), OnDeleteButtonClick {

    private var _binding: FragmentMyMedicineBinding? = null
    private lateinit var myMedicineViewModel: MyMedicineViewModel

//     This property is only valid between onCreateView and
//     onDestroyView.

    private val binding get() = _binding!!

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        myMedicineViewModel =
            ViewModelProvider(this)[MyMedicineViewModel::class.java]

        myMedicineViewModel.initializeExpiryWork(requireContext())

        _binding = FragmentMyMedicineBinding.inflate(inflater, container, false)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = MedicineListAdapter(this)
        binding.recyclerView.adapter = adapter

        myMedicineViewModel.list.observe(requireActivity(), Observer{
            adapter.updateDate(it)
        })

        myMedicineViewModel.toast.observe(requireActivity(), Observer{
            handleToast(it)
        })

        myMedicineViewModel.getMedicineList()

        adapter.updateDate(myMedicineViewModel.list.value!!)

        binding.addButton.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext(), R.style.CustomAlertDialog).create()
            val view = layoutInflater.inflate(R.layout.activity_add_medicine, null)
            val button = view.findViewById<Button>(R.id.addToList)
            builder.setView(view)
            builder.setCanceledOnTouchOutside(true)
            builder.show()

            var day = ""
            var month = ""
            var year = ""
            var manufactureDate = ""
            var expiryDate = ""

            val calendar = Calendar.getInstance()
            calendar.time = Date()

            view.findViewById<ImageButton>(R.id.manufactureDateCalendar).setOnClickListener {

                val datePickerDialog = DatePickerDialog(requireContext(), { view1, year, monthOfYear, dayOfMonth ->

                    view.findViewById<TextInputEditText>(R.id.manufactureDate).setText("$dayOfMonth/${monthOfYear+1}/$year")
                    manufactureDate = "$dayOfMonth/${monthOfYear+1}/$year"
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH))

                datePickerDialog.show()

            }

            view.findViewById<ImageButton>(R.id.expiryDateCalendar).setOnClickListener {
                val datePickerDialog = DatePickerDialog(requireContext(), { view1, year, monthOfYear, dayOfMonth ->

                    view.findViewById<TextInputEditText>(R.id.expiryDate).setText("$dayOfMonth/${monthOfYear+1}/$year")
                    expiryDate = "$dayOfMonth/${monthOfYear+1}/$year"
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH))

                datePickerDialog.show()
            }

            view.findViewById<Button>(R.id.addToList).setOnClickListener {
                if(view.findViewById<TextInputEditText>(R.id.medicineName).text.isNullOrEmpty()
                    || view.findViewById<TextInputEditText>(R.id.manufactureDate).text.isNullOrEmpty()
                    || view.findViewById<TextInputEditText>(R.id.expiryDate).text.isNullOrEmpty()){

                    Snackbar.make(binding.root, "Please fill the details", Snackbar.LENGTH_SHORT).show()
                }
                else{
                    val medicine = Medicine(view.findViewById<TextInputEditText>(R.id.medicineName).text.toString(), manufactureDate, expiryDate, "")
                    myMedicineViewModel.addMedicineToList(medicine)
                    builder.dismiss()
                }
            }

            myMedicineViewModel.getMedicineList()

        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(medicine: Medicine) {
        myMedicineViewModel.deleteMedicine(medicine.getMedicineId())
    }

    private fun handleToast(value: Int){
        when(value){
            1-> Snackbar.make(binding.root, "Medicine added", Snackbar.LENGTH_SHORT).show()
            2-> Snackbar.make(binding.root, "Error while adding", Snackbar.LENGTH_SHORT).show()
            3-> Snackbar.make(binding.root, "Error while retrieving data", Snackbar.LENGTH_SHORT).show()
            4-> Snackbar.make(binding.root, "Medicine deleted", Snackbar.LENGTH_SHORT).show()
            5-> Snackbar.make(binding.root, "Error while deletion", Snackbar.LENGTH_SHORT).show()
            else-> value
        }
    }
}