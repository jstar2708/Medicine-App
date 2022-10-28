package com.example.medicine.model

class Chemist {
    private lateinit var password: String
    private lateinit var email: String
    private lateinit var userId: String
    private lateinit var address: String
    private lateinit var ownerName: String
    private var phoneNumber: Long = 0
    private var pinCode: Int = 0
    private lateinit var state: String
    private lateinit var city: String
    private lateinit var uniqueId: String
    private lateinit var shopName: String

    constructor(){}

    constructor(
        password: String,
        email: String,
        userId: String,
        address: String,
        name: String,
        phoneNumber: Long,
        pinCode: Int,
        state: String,
        city: String,
        uniqueId: String,
        shopName: String
    ) {
        this.password = password
        this.email = email
        this.userId = userId
        this.address = address
        this.ownerName = name
        this.phoneNumber = phoneNumber
        this.pinCode = pinCode
        this.state = state
        this.city = city
        this.uniqueId = uniqueId
        this.shopName = shopName
    }

    fun getUniqueId(): String {
        return this.uniqueId
    }

    fun setUniqueId(uniqueId: String){
        this.uniqueId = uniqueId
    }

    fun getCity(): String{
        return this.city
    }

    fun setCity(value: String){
        this.city = value
    }

    fun getPinCode(): Int{
        return this.pinCode
    }

    fun setPinCode(pinCode: Int){
        this.pinCode = pinCode
    }

    fun setState(state: String){
        this.state = state
    }

    fun getState(): String{
        return this.state
    }

    fun getEmail(): String{
        return this.email
    }

    fun setEmail(email: String){
        this.email = email
    }

    fun getPassword(): String{
        return this.password
    }

    fun setPassword(password: String){
        this.password = password
    }

    fun getUserId(): String{
        return this.userId
    }

    fun setUserId(userId: String){
        this.userId = userId
    }

    fun setOwnerName(name: String){
        this.ownerName = name
    }

    fun getOwnerName(): String{
        return this.ownerName
    }

    fun setPhoneNumber(number: Long){
        this.phoneNumber = number
    }

    fun getPhoneNumber(): Long{
        return this.phoneNumber
    }


    fun setAddress(address: String){
        this.address = address
    }

    fun getAddress(): String{
        return this.address
    }

    fun getShopName(): String{
        return this.shopName
    }
    fun setShopName(shopName: String){
        this.shopName = shopName
    }
}