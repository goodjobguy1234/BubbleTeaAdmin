package com.example.termprojectadmin.Entity

import com.example.termprojectadmin.Entity.OrderList

data class Queue(
        val orderList: Map<String, OrderList> = mapOf(),
        val queueId: String = "Unknown"
)
