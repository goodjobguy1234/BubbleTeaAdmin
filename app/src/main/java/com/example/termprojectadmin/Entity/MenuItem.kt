package com.example.termprojectadmin.Entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MenuItem(
    val imageUrl: String = "",
    val name: String = "Unknow",
    val point: Int =-1,
    val price: Int = -1
): Parcelable{

    override fun equals(other: Any?): Boolean {
        return (other is MenuItem) && (name == other.name)
    }

    fun toMap(): Map<String, Any?>{
        return mapOf(
            "imageUrl" to imageUrl,
            "name" to name,
            "point" to point,
            "price" to price,

        )
    }

}

