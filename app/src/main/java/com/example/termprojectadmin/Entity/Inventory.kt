package com.example.termprojectadmin.Entity


data class Inventory(
        val name: String = "",
        var remain: Int = -1
) {
    fun toMap(): Map<String, Any?> {
        return mapOf(
                "name" to name,
                "remain" to remain

                )
    }
    fun addRemainAmount(quantity:Int){
        remain += quantity
    }
    fun checkRemain(): Boolean{
        return remain > 0
    }

    fun subtractRemain(){
        remain--
    }

    fun addRemain(){
        remain++
    }
}
