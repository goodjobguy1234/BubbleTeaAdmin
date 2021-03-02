package com.example.termprojectadmin.Menu

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.termprojectadmin.BaseActivity
import com.example.termprojectadmin.Entity.MenuItem
import com.example.termprojectadmin.Entity.RewardSale
import com.example.termprojectadmin.Entity.Sale
import com.example.termprojectadmin.FirebaseHelper.FIrebaseMenuHelper
import com.example.termprojectadmin.FirebaseHelper.FirebaseRewardHelper
import com.example.termprojectadmin.FirebaseHelper.FirebaseSaleHelper
import com.example.termprojectadmin.FirebaseHelper.FirebaseStorageHelper
import com.example.termprojectadmin.R
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

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

//        display edit menu list
        edit_menu_recycler.apply {
            layoutManager = GridLayoutManager(this@MenuActivity, 2)

            adapter = MenuAdapter(menuList, { item ->
                /*when user click delete button then remove menu, sales and reward sales*/
                FirebaseStorageHelper(this@MenuActivity).remove(item.imageUrl)
                FIrebaseMenuHelper.removeValue(item)
                FirebaseSaleHelper.removeValue(item.name)
                FirebaseRewardHelper.removeValue(item.name)

            }, { item ->
                /*when user click add button then add new menu, sales and reward sales*/
                showDialog(createDialog(), item)
            })
        }

    }

    //    map variable with ui
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

//    set up ui
    override fun setLayoutResource() = R.layout.activity_menu

//    when click at top left it go back to main page
    fun onClickBack(view: View) = finish()

//    create dialog
    private fun createDialog(): AlertDialog{
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

//    show dialog as well as set button behavior
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

    //load image to show in edit dialog
            Glide.with(this).load(menu.imageUrl).into(image!!)

    //set when user click image behavior
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
                        // update image here
                        FIrebaseMenuHelper.removeValue(menu)
                        /*
                        * firebase storage helper use to keep image of item
                        * remove mean remove old image of that item then upload
                        * new one
                        * */
                        FirebaseStorageHelper(this@MenuActivity).apply {
                            remove(newItem.imageUrl)
                            upload(image) {url ->
                                newItem.imageUrl = url
                                /*write new item in menu, sales and reward sales data in firebase*/
                                FIrebaseMenuHelper.writeValue(newItem)

                                FirebaseSaleHelper.writeValue(
                                        Sale(newItem.imageUrl, newItem.name, newItem.price, 0)
                                )

                                FirebaseRewardHelper.writeValue(
                                        RewardSale(newItem.imageUrl, newItem.name, newItem.price, 0)
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

        /*create dialog and show to 3 section menu */
        AlertDialog.Builder(context).apply {
            setTitle("Choose item picture")
            setItems(options) { dialog, which ->
                when(options[which]){

                /*when choose the this option it will ask system to open the camera */
                    "Take Photo" -> {
                        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        startActivityForResult(cameraIntent, TAKE_PHOTO)
                    }

                /*when choose the this option it will ask system to open the gallery */
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

    /*set click behavior when user click image in edit mode
    * called selectImage method
    * */
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

    /*when user click add action button at the button right*/
    fun onClickAction(view: View) = showDialog(createDialog(), MenuItem.DEFAULT_MENU)
}