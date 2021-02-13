package com.example.termprojectadmin.Entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.TypeParceler

@Parcelize
data class Order(
        @TypeParceler<MenuItem, MenuClassParceler>() val item: MenuItem,
        var quantity: Int,
        var reward: Boolean
) : Parcelable {
    fun addQuantity() = quantity++
    fun subtractQuantity() = quantity--
    override fun equals(other: Any?): Boolean {
        return (other is Order) && (item.name == other.item.name) && (reward == other.reward)
    }
}
