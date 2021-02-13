package com.example.termprojectadmin.Menu

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.termprojectadmin.MenuItem
import com.example.termprojectadmin.R

const val PHOTO_PICK = 1
const val TAKE_PHOTO = 0
class MenuActivity : AppCompatActivity() {
    lateinit var menuList: ArrayList<MenuItem>
    lateinit var edit_menu_recycler: RecyclerView
    lateinit var selectedMenu: MenuItem
    lateinit var dialog: AlertDialog
    var uri:Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
//        menuList = MenuItem.createMenu()
        Log.d("Array", menuList.toString())
        Log.d("item1", menuList[0].price.toString())
        init()
        edit_menu_recycler.apply {
            layoutManager = GridLayoutManager(this@MenuActivity, 2)
            adapter = MenuAdapter(menuList, { position ->
                menuList.removeAt(position)
                fetchData(edit_menu_recycler)
            }, { position ->
                showDialog(createDialog(), menuList[position])
            })
        }

    }

    private fun init() {
        edit_menu_recycler = findViewById(R.id.edit_menu_recycler)
    }

    override fun onStart() {
        super.onStart()
        window.decorView.apply {
            systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
    }
     fun setUpLayout(){
        window.decorView.apply {
            systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        setUpLayout()
    }

    fun onClickBack(view: View) {
        finish()
    }
    fun fetchData(recycler: RecyclerView){
        recycler.adapter?.notifyDataSetChanged()
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
            val image = dialog.findViewById<ImageView>(R.id.dialog_imageView)
            nameEdit!!.setText(menu.name)
            priceEdit!!.setText(menu.price.toString())
//            image!!.setImageResource(menu.imageId)
//            setImageOnclick(image)
//            dialog.getButton(AlertDialog.BUTTON_POSITIVE).apply {
//                setTextColor(Color.parseColor("#81B29A"))
//                setOnClickListener {
//                    val position = menuList.indexOf(menu)
//                    menuList[position].name = nameEdit.text.toString()
//                    menuList[position].price = priceEdit.text.toString().toInt()
//                    dialog.dismiss()
//                    fetchData(edit_menu_recycler)
//                }
//            }
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).apply {
                setTextColor(resources.getColor(R.color.button))
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
                    dialog.findViewById<ImageView>(R.id.dialog_imageView).apply {
                        this?.setImageURI(uri)
                    }


                }
                TAKE_PHOTO -> {
                    val thunbnail = data?.extras?.get("data") as Bitmap
                    dialog.findViewById<ImageView>(R.id.dialog_imageView).apply {
                        this!!.setImageBitmap(thunbnail)
                    }
                }
            }
        }
    }
}