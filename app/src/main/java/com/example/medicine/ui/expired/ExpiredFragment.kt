package com.example.medicine.ui.expired

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SnapHelper
import com.example.medicine.databinding.FragmentExpiredBinding
import com.example.medicine.model.Medicine
import com.example.medicine.ui.my_medicine.MedicineListAdapter
import com.example.medicine.ui.my_medicine.OnDeleteButtonClick
import com.google.android.material.snackbar.Snackbar

class ExpiredFragment : Fragment(), OnDeleteButtonClick {

    private var _binding: FragmentExpiredBinding? = null
    private lateinit var expiredViewModel: ExpiredViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        expiredViewModel =
            ViewModelProvider(this)[ExpiredViewModel::class.java]


        _binding = FragmentExpiredBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val adapter = MedicineListAdapter(this)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        expiredViewModel.expiredMedicineList.observe(requireActivity(), Observer{
            adapter.updateDate(it)
        })
        expiredViewModel.toast.observe(requireActivity(), Observer{
            handleToast(it)
        })
        expiredViewModel.getExpiredMedicine()

        return root
    }

    private fun handleToast(value: Int) {
        if(value == 1){
            Snackbar.make(binding.root, "Error while retrieving medicine list", Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(medicine: Medicine) {
        expiredViewModel.deleteMedicine(medicine.getMedicineId())
    }
}