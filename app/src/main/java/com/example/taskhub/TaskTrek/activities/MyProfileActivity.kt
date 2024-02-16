package com.example.taskhub.TaskTrek.activities

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide

import com.example.taskhub.R
import com.example.taskhub.TaskTrek.Firebase.FirestoreClass
import com.example.taskhub.TaskTrek.models.BaseActivity
import com.example.taskhub.TaskTrek.models.Users
import java.io.IOException

class MyProfileActivity : BaseActivity() {

    lateinit var iv_user_my_profie_image:ImageView
    lateinit var toolbar_my_profile_activity:androidx.appcompat.widget.Toolbar
    lateinit var et_name:EditText
    lateinit var et_mobile:EditText

    companion object{
        private const val READ_STORAGE_PERMISSION_CODE = 1
        private const val PICK_IMAGE_REQUEST_CODE=2
    }

    private var mSelectedImagerUri: Uri?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)
        setActionBar()
        FirestoreClass().loadUserData(this)
        iv_user_my_profie_image=findViewById(R.id.iv_user_my_profie_image)

        iv_user_my_profie_image.setOnClickListener{
            if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    showImageChooser()
            }
            else{
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    READ_STORAGE_PERMISSION_CODE

                )
            }
        }

        fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
        ) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            if(requestCode == READ_STORAGE_PERMISSION_CODE){
                if(grantResults.isNotEmpty()&& grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    showImageChooser()
                }
                else{
                    Toast.makeText(this, "Give Files Access settings from settings",Toast.LENGTH_LONG).show()
                }
            }
        }


    }

    private fun setActionBar(){
        toolbar_my_profile_activity=findViewById(R.id.toolbar_my_profile_activity)
        setSupportActionBar(toolbar_my_profile_activity)
        val actionBar=supportActionBar
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.white_color_back_24dp)
            actionBar.title=resources.getString(R.string.nav_my_profile)
        }
        toolbar_my_profile_activity.setNavigationOnClickListener {

        }
    }


    private fun showImageChooser(){
        var galleryIntent=Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== Activity.RESULT_OK && requestCode== PICK_IMAGE_REQUEST_CODE &&data!=null){
            mSelectedImagerUri=data.data

            try {
                Glide
                    .with(this@MyProfileActivity)
                    .load(mSelectedImagerUri)
                    .centerCrop()
                    .placeholder(R.drawable.ic_user_image_holder_foreground)
                    .into(iv_user_my_profie_image)
            }
            catch (e:IOException){
                e.printStackTrace()
            }
        }
    }


    fun setUserDetailsUI(user: Users){
        et_name=findViewById(R.id.et_name)
        et_mobile=findViewById(R.id.et_mobile)
        Glide
            .with(this@MyProfileActivity)
            .load(user.image)
            .centerCrop()
            .placeholder(R.drawable.ic_user_image_holder_foreground)
            .into(iv_user_my_profie_image)

        et_name.setText(user.name)
        if(user.modile!=0L){
            et_mobile.setText(user.modile.toString())
        }

    }
}