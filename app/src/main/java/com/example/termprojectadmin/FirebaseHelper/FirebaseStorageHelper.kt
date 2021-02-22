package com.example.termprojectadmin.FirebaseHelper

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import android.widget.ImageView
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream

object FirebaseStorageHelper {
    val storage = Firebase.storage
    val storageRef = storage.reference

    fun upload(imageView: ImageView, callback: (String) -> Unit){
        val imageRef = storageRef.child("${System.currentTimeMillis()}.jpg")
        imageView.isDrawingCacheEnabled = true
        imageView.buildDrawingCache()
        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        var uploadTask = imageRef.putBytes(data)
        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
        }.addOnSuccessListener { taskSnapshot ->
            val downloadUri = taskSnapshot.metadata!!.reference!!.downloadUrl// get url
            downloadUri.addOnSuccessListener {
                var imageLink = it.toString()
                print(imageLink)
                callback(imageLink)
            }

            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            // ...
        }
    }
}