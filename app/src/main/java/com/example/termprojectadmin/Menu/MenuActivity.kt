package com.example.termprojectadmin.Menu

import android.app.ProgressDialog
import android.app.ProgressDialog.show
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.termprojectadmin.BaseActivity
import com.example.termprojectadmin.Entity.MenuItem
import com.example.termprojectadmin.Entity.Sale
import com.example.termprojectadmin.FirebaseHelper.FIrebaseMenuHelper
import com.example.termprojectadmin.FirebaseHelper.FirebaseSaleHelper
import com.example.termprojectadmin.FirebaseHelper.FirebaseStorageHelper
import com.example.termprojectadmin.R
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream

const val PHOTO_PICK = 1
const val TAKE_PHOTO = 0
class MenuActivity : BaseActivity() {
    lateinit var menuList: FirebaseRecyclerOptions<MenuItem>
    lateinit var edit_menu_recycler: RecyclerView
    lateinit var selectedMenu: MenuItem
    lateinit var dialog: AlertDialog
    var uri:Uri? = null
    var thunbnail: Bitmap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        menuList = FIrebaseMenuHelper.getOption()
        init()
        edit_menu_recycler.apply {
            layoutManager = GridLayoutManager(this@MenuActivity, 2)
            adapter = MenuAdapter(menuList, { item ->
                /* delete menu*/
                FirebaseStorageHelper(this@MenuActivity).remove(item.imageUrl)
                FIrebaseMenuHelper.removeValue(item)
                FirebaseSaleHelper.removeValue(item.name)

            }, { item ->
                /* Add menu*/
                showDialog(createDialog(), item)
            })
        }

    }

    private fun init() {
        edit_menu_recycler = findViewById(R.id.edit_menu_recycler)
    }

    override fun onStart() {
        super.onStart()
        (edit_menu_recycler.adapter as FirebaseRecyclerAdapter<*,*>).startListening()
    }

    override fun onStop() {
        super.onStop()
        (edit_menu_recycler.adapter as FirebaseRecyclerAdapter<*,*>).stopListening()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        setUpLayout()
    }

    override fun setLayoutResource(): Int {
        return R.layout.activity_menu
    }

    fun onClickBack(view: View) {
        finish()
    }
    fun createDialog(): AlertDialog{
        val view = layoutInflater.inflate(R.layout.dialog_custom_layout, null)
        dialog = AlertDialog.Builder(this).apply {
            setView(view)
            setCancelable(false)
            setPositiveButton("Confirm") { _, _ ->
            }
            setNegativeButton("Cancel") { _, _ ->
            }
        }.create()
        return  dialog
    }
    fun showDialog(dialog: AlertDialog, menu: MenuItem){
        selectedMenu = menu

        dialog.setOnShowListener {

            val nameEdit = dialog.findViewById<EditText>(R.id.name_edt)
            val priceEdit = dialog.findViewById<EditText>(R.id.price_edt)
            val pointEdit = dialog.findViewById<EditText>(R.id.point_edt)
            val image = dialog.findViewById<ImageView>(R.id.dialog_imageView)

            nameEdit!!.setText(menu.name)
            priceEdit!!.setText(menu.price.toString())
            pointEdit!!.setText(menu.point.toString())

            Glide.with(this).load(menu.imageUrl).into(image!!)

            setImageOnclick(image)

            dialog.getButton(AlertDialog.BUTTON_POSITIVE).apply {
                setTextColor(Color.parseColor("#81B29A"))
                setOnClickListener {
                    // get image here before push into menu
                    val newItem =  MenuItem(
                            menu.imageUrl,
                            nameEdit.text.toString(),
                            pointEdit.text.toString().toInt(),
                            priceEdit.text.toString().toInt()
                    )
                    if (newItem.isDefaultValue()){
                        // updateimage here
                        FIrebaseMenuHelper.removeValue(menu)
                        FirebaseStorageHelper(this@MenuActivity).apply {
                            remove(newItem.imageUrl)
                            upload(image) {url ->
                                newItem.imageUrl = url
                                FIrebaseMenuHelper.writeValue(newItem)
                                FirebaseSaleHelper.writeValue(
                                        Sale(newItem.imageUrl, newItem.name, newItem.price, 0)
                                )
                            }
                        }
                        dialog.dismiss()
                    }else{
                        nameEdit.error = "Please Change"
                        priceEdit.error = "Please Change"
                        pointEdit.error = "Please Change"
                    }
                }
            }

            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).apply {
                setTextColor(resources.getColor(R.color.button, null))
                setOnClickListener {
                    dialog.dismiss()
                }
            }
        }

        dialog.show()
    }

    fun selectImage(context: Context){
        val options = arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
        AlertDialog.Builder(context).apply {
            setTitle("Choose item picture")
            setItems(options) { dialog, which ->
                when(options[which]){
                    "Take Photo" -> {
                        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        startActivityForResult(cameraIntent, TAKE_PHOTO)
                    }
                    "Choose from Gallery" -> {
                        val pickPhoto = Intent()
                        pickPhoto.type = "image/*"
                        pickPhoto.action = Intent.ACTION_GET_CONTENT
                        startActivityForResult(pickPhoto, PHOTO_PICK)
                    }
                    "Cancel" -> {
                        dialog.dismiss()
                    }
                }
            }
            show()
        }
    }
    fun setImageOnclick(imageViwe: ImageView){
        imageViwe.setOnClickListener {
            selectImage(this)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                PHOTO_PICK -> {
                    uri = data?.data
                    thunbnail = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri)

                    dialog.findViewById<ImageView>(R.id.dialog_imageView).apply {
                        this?.setImageBitmap(thunbnail)

                    }



                }
                TAKE_PHOTO -> {
                    thunbnail = data?.extras?.get("data") as Bitmap
                    dialog.findViewById<ImageView>(R.id.dialog_imageView).apply {
                        this!!.setImageBitmap(thunbnail)
                    }
                }
            }
        }
    }

    fun onClickAction(view: View) {
        showDialog(createDialog(), MenuItem.DEFAULT_MENU)
    }



}