package com.example.termprojectadmin.Entity

data class OrderList(
        val imageUrl: String = "",
        val name: String = "Unknown",
        val price: Int = -1,
        val quantity: Int = -1,
        val reward: Boolean = false
)

