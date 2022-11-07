package com.example.medicine.model

public class Medicine{
    private lateinit var name: String
    private lateinit var manufactureDate: String
    private lateinit var expiryDate: String
    private lateinit var medicineId: String

    constructor()

    constructor(name: String, manufactureDate: String, expiryDate: String, medicineId: String) {
        this.name = name
        this.manufactureDate = manufactureDate
        this.expiryDate = expiryDate
        this.medicineId = medicineId
    }

    constructor(name: String, manufactureDate: String, expiryDate: String) {
        this.name = name
        this.manufactureDate = manufactureDate
        this.expiryDate = expiryDate
    }

    fun getMedicineId(): String{
        return this.medicineId
    }

    fun setMedicineId(medicineId: String){
        this.medicineId = medicineId
    }

    fun getName(): String{
        return this.name
    }

    fun setName(name: String){
        this.name = name
    }

    fun getManufactureDate(): String{
        return this.manufactureDate
    }

    fun setManufactureDate(manufactureDate: String){
        this.manufactureDate = manufactureDate
    }

    fun getExpiryDate(): String{
        return this.expiryDate
    }

    fun setExpiryDate(expiryDate: String){
        this.expiryDate = expiryDate
    }
}
