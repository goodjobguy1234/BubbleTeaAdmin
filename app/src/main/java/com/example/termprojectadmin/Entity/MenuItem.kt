package com.example.termprojectadmin.Entity

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize

@Parcelize
data class MenuItem(
    val imageUrl: String = "",
    val name: String = "Unknow",
    val point: Int =-1,
    val price: Int = -1,
    var remain: Int = -1
): Parcelable{

    fun subtractRemain(){
        remain--
    }
    fun addRemain(){
        remain++
    }
    fun addRemainAmount(amount: Int){
        remain += amount
    }
    fun checkRemain():Boolean{
        if (remain >= 1){
            return true
        }
        return false
    }

    override fun equals(other: Any?): Boolean {
        return (other is MenuItem) && (name == other.name)
    }

    fun toMap(): Map<String, Any?>{
        return mapOf(
            "imageUrl" to imageUrl,
            "name" to name,
            "point" to point,
            "price" to price,
            "remain" to remain
        )
    }
}

object MenuClassParceler: Parceler<MenuItem> {
    override fun create(parcel: Parcel): MenuItem {
        return MenuItem(parcel.readString()!!, parcel.readString()!!, parcel.readInt(), parcel.readInt(), parcel.readInt())
    }

    override fun MenuItem.write(parcel: Parcel, flags: Int) {
        parcel.writeString(imageUrl)
        parcel.writeString(name)
        parcel.writeInt(point)
        parcel.writeInt(price)
        parcel.writeInt(remain)
    }
}
