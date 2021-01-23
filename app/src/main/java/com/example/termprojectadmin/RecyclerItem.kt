package com.example.termprojectadmin

sealed class RecyclerItem{
    data class Header(val typeName: String): RecyclerItem()
    data class Product(val order:Order): RecyclerItem()
}
