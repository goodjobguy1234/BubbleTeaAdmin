package com.example.termprojectadmin

import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize

@Parcelize
data class MenuItem(
        var imageId: Int,
        var name: String,
        var price: Int = 0,
        var remainder: Int
): Parcelable{
    companion object{
        fun createMenu():ArrayList<MenuItem>{
            var menu = ArrayList<MenuItem>()
            menu.add(MenuItem(R.drawable.yuzu_refresher, "Yuzu refresher", 35,10))
            menu.add(MenuItem(R.drawable.classic_brown_sugar_milk_tea, "Classic brown sugar milk tea", 75,10))
            menu.add(MenuItem(R.drawable.matcha_brown_sugar_latte, "Matcha brown sugar latte", 105,10))
            menu.add(MenuItem(R.drawable.traditional_thai_milk_tea, "Traditional Thai milk tea", 15,10))
            menu.add(MenuItem(R.drawable.hojicha_latte, "Hojicha latte", 40,10))
            menu.add(MenuItem(R.drawable.caramel_macchiato, "Caramel macchiato", 40,10))
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
