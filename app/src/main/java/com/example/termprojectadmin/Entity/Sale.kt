package com.example.termprojectadmin.Entity

data class Sale(
    val imageUrl: String = "",
    val name: String = "Unknow",
    var price: Int = -1,
    val quantity: Int = -1
)
