package com.example.termprojectadmin.FirebaseHelper

import com.example.termprojectadmin.Entity.Queue
import com.example.termprojectadmin.Entity.Sale
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase

object FirebaseQueueHelper {
    private val firebaseInstance = FirebaseDatabase.getInstance()
    private var queuery = firebaseInstance.reference.child("queue")

//    get snapshot of queue list data
    fun getOption(): FirebaseRecyclerOptions<Queue> {
        val options = FirebaseRecyclerOptions.Builder<Queue>()
                .setQuery(queuery, Queue::class.java)
                .build()
        return options
    }

//    remove queue item in firebase
    fun removeValue(queueId:String){
        queuery.child(queueId).setValue(null)
    }

//    remove all queue
    fun resetValue(){
        queuery.ref.setValue(null)
    }
}