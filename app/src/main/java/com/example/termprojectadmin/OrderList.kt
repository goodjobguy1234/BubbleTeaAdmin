package com.example.termprojectadmin

data class OrderList(
    val listOrder: ArrayList<Order>,
    val id: String

){
//    companion object{
//        fun createQueue(): ArrayList<OrderList>{
//            val list = ArrayList<OrderList>()
//
//            var queueOrder: OrderList
//            val menulist = MenuItem.createMenu()
//            var orderList = ArrayList<Order>()
//            var order = Order(menulist[0],2, false)
//            orderList.add(order)
//            order = Order((menulist[0]), 1, true)
//            orderList.add(order)
//            order = Order((menulist[1]), 5, false)
//            orderList.add(order)
//            order = Order((menulist[2]), 2, false)
//            orderList.add(order)
//            queueOrder = OrderList(orderList, "A101")
//            list.add(queueOrder)
//
//            orderList = ArrayList()
//            order = Order((menulist[4]), 1, true)
//            orderList.add(order)
//            order = Order((menulist[2]), 5, false)
//            orderList.add(order)
//            order = Order((menulist[3]), 2, false)
//            orderList.add(order)
//            queueOrder = OrderList(orderList, "A102")
//            list.add(queueOrder)
//
//            orderList = ArrayList()
//            order = Order((menulist[2]), 1, false)
//            orderList.add(order)
//            order = Order((menulist[1]), 5, false)
//            orderList.add(order)
//            order = Order((menulist[0]), 2, false)
//            orderList.add(order)
//            queueOrder = OrderList(orderList, "A103")
//            list.add(queueOrder)
//
//            return list
        }

