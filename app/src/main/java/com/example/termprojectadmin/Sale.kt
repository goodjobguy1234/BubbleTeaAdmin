package com.example.termprojectadmin

data class Sale(
    val name: String,
    var quantity: Int,
    val price: Int
){
    companion object{
        fun createSales(): ArrayList<Sale>{
            return arrayListOf(
                    Sale("Matcha Brown Sugar Latte", 10, 35),
                    Sale("Classic Brown Sugar Milk Tea", 5, 40),
                    Sale("Yuzu Refresher", 6, 50)
            )
        }
    }
}
