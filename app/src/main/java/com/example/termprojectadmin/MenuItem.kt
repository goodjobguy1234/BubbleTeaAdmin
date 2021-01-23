package com.example.termprojectadmin

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize

@Parcelize
data class MenuItem(
        val imageId: Int,
        val name: String,
        val price: Int = 0,
        var remainder: Int
): Parcelable{
    companion object{
        fun createMenu():ArrayList<MenuItem>{
            var menu = ArrayList<MenuItem>()
            menu.add(MenuItem(1234, "item1", 35,10))
            menu.add(MenuItem(1234, "item2", 75,10))
            menu.add(MenuItem(1234, "item3", 105,10))
            menu.add(MenuItem(1234, "item4", 15,10))
            menu.add(MenuItem(1234, "item5", 40,10))
            return menu

        }
    }
    fun subtractRemain(){
        remainder--
    }
    fun addRemain(){
        remainder++
    }
    fun addRemainAmount(amount: Int){
        remainder += amount
    }
    fun checkRemain():Boolean{
        if (remainder > 0){
            return true
        }
        return false
    }

    override fun equals(other: Any?): Boolean {
        return (other is MenuItem) && (name == other.name)
    }
}

object MenuClassParceler: Parceler<MenuItem> {
    override fun create(parcel: Parcel): MenuItem {
        return MenuItem(parcel.readInt(), parcel.readString()!!, parcel.readInt(), parcel.readInt())
    }

    override fun MenuItem.write(parcel: Parcel, flags: Int) {
        parcel.writeInt(imageId)
        parcel.writeString(name)
        parcel.writeInt(price)
        parcel.writeInt(remainder)
    }

}
