package com.example.termprojectadmin.Entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class MenuItem(
    val imageUrl: String = "",
    val name: String = "Unknown",
    val point: Int =-1,
    val price: Int = -1
){

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
    fun isDefaultValue():Boolean{
        return name != "Unknown" && point > 0 && price > 0
    }
    companion object{
        val DEFAULT_MENU = MenuItem(
                "https://firebasestorage.googleapis.com/v0/b/bubbletea-94b66.appspot.com/o/icons8-image-100.png?alt=media&token=9673273f-fd15-4503-9325-7a4772717866",
                "Unknown",
                -1,
                -1
        )
    }
}

