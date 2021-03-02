package com.example.termprojectadmin.FirebaseHelper

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

object FirebaseQueueIDHelper {
    private val firebaseInstance = FirebaseDatabase.getInstance()
    private var queuery = firebaseInstance.reference.child("queueID")

// get current queue id and date in real time use to update ui
    fun getRealtimeCurrentQueue(callback: (String, String) -> Unit){
        queuery.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val queue = snapshot.child("currentq").getValue(String::class.java)
                val date = snapshot.child("date").getValue(String::class.java)
                callback(queue!!, date!!)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

//    update queue id and date
    fun setQueue(queueid:String, date:String){
        queuery.updateChildren(mapOf(
                "currentq" to queueid,
                "date" to date
        ))
    }
}