package com.example.termprojectadmin.FirebaseHelper

import android.app.ProgressDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.termprojectadmin.Menu.MenuActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream

class FirebaseStorageHelper(val context: Context) {
    val storage = Firebase.storage
    val storageRef = storage.reference
    var progressDialog = ProgressDialog(context).apply {
        setTitle("Upload Photos")
        setCanceledOnTouchOutside(false)
        setMessage("Loading...")
    }

    fun upload(imageView: ImageView, callback: (String) -> Unit) {
        val imageRef = storageRef.child("${System.currentTimeMillis()}.PNG")
        imageView.isDrawingCacheEnabled = true
        imageView.buildDrawingCache()
        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val data = baos.toByteArray()

        var uploadTask = imageRef.putBytes(data)
        uploadTask.addOnProgressListener {
            progressDialog.show()
        }.addOnSuccessListener { taskSnapshot ->
            progressDialog.dismiss()
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

    fun remove(url: String){
        storageRef.storage.getReferenceFromUrl(url).delete()
    }
}