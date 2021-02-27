package com.example.termprojectadmin.Entity

sealed class RecyclerItem{
    data class Header(val typeName: String): RecyclerItem()
    data class Product(val order: OrderList): RecyclerItem()
}
