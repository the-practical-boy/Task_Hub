package com.example.taskhub.TaskTrek.activities

import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.taskhub.R
import com.example.taskhub.TaskTrek.Firebase.FirestoreClass
import com.example.taskhub.TaskTrek.models.BaseActivity
import com.example.taskhub.TaskTrek.models.Users
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SignUpActivity : BaseActivity() {

    lateinit var et_name1: EditText
    lateinit var et_email: EditText
    lateinit var et_paswrd:EditText
    lateinit var btn_signup: Button
    val ttt:String="Please Wait!!!"

    lateinit var toolbar_sign_up_activity:androidx.appcompat.widget.Toolbar
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)


            btn_signup=findViewById(R.id.btn_sign_up)

            toolbar_sign_up_activity=findViewById(R.id.toolbar_sign_up_activity)

            window.setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
            setupActionBar()
    }

    private fun setupActionBar(){
        setSupportActionBar(toolbar_sign_up_activity)
        val actionBar=supportActionBar
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24)
        }

        toolbar_sign_up_activity.setNavigationOnClickListener { onBackPressed() }

        btn_signup.setOnClickListener{
            registerUser()
        }

    }


    fun userRegisteredSuccess(){
        Toast.makeText(this,"You have successfully registered",Toast.LENGTH_LONG).show()
        hideProgressDialog()
        FirebaseAuth.getInstance().signOut()
        finish()
    }





    //-----------------------------------------------------

    private fun registerUser(){
        et_name1=findViewById<EditText>(R.id.et_name)
        et_email=findViewById<EditText>(R.id.et_email)
        et_paswrd=findViewById<EditText>(R.id.et_paswrd)

        val name:String=et_name1.text.toString().trim{it<=' '}
        val email:String=et_email.text.toString().trim{it<=' '}
        val password:String=et_paswrd.text.toString().trim{it<=' '}

        if(validateForm(name,email, password)){

            FirebaseApp.initializeApp(this)
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->
                hideProgressDialog()
                if (task.isSuccessful) {
                    val firebaseUser: FirebaseUser = task.result!!.user!!
                    val registeredemail = firebaseUser.email!!

                    val user= Users(firebaseUser.uid,name,registeredemail)

                    FirestoreClass().registerUser(this,user)

                } else {
                    Toast.makeText(this, "Registration failed: An error occurred", Toast.LENGTH_SHORT).show()

                }

            }
        }

    }




    //--------------------------------

    private fun validateForm(name:String,email:String,password:String): Boolean {
        return when {

            TextUtils.isEmpty(email) -> {
                showErrorSnackBar("Please enter an email address")
                false
            }
            TextUtils.isEmpty(name) -> {
                showErrorSnackBar("Please enter a name")
                false
            }
            TextUtils.isEmpty(password) -> {
                showErrorSnackBar("Please enter a password")
                false
            }
            else -> {
                true
            }
        }

        }
    }


