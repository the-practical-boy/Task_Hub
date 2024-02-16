package com.example.taskhub.TaskTrek.activities

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatCallback
import androidx.appcompat.widget.AppCompatButton
import com.example.taskhub.R
import com.example.taskhub.TaskTrek.models.BaseActivity
import com.example.taskhub.TaskTrek.models.Users
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : BaseActivity() {

    lateinit var auth: FirebaseAuth

    lateinit var et_email: EditText
    lateinit var et_paswrd: EditText
    lateinit var btn_signin: Button

    lateinit var toolbar_sign_in_activity: androidx.appcompat.widget.Toolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_sign_in)


        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        toolbar_sign_in_activity = findViewById(R.id.toolbar_sign_in_activity)
        setupActionBar()
        btn_signin = findViewById(R.id.btn_log_in)



        auth = FirebaseAuth.getInstance()
        btn_signin.setOnClickListener {
            signInRegisteredUser()
            startActivity(Intent(this, MainActivity::class.java))
            finish()

        }
    }

    private fun setupActionBar() {
        setSupportActionBar(toolbar_sign_in_activity)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24)
        }

        toolbar_sign_in_activity.setNavigationOnClickListener { onBackPressed() }
    }


    private fun signInRegisteredUser() {
        et_email = findViewById(R.id.et_email_signin)

        et_paswrd = findViewById(R.id.et_paswrd_signin)


        val email: String = et_email.text.toString().trim { it <= ' ' }
        val password: String = et_paswrd.text.toString().trim { it <= ' ' }


        if (validateForm(email, password)) {

            showProgressDialog("Please Wait...")

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    hideProgressDialog()
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("Sign In", "createUserWithEmail:success")
                        val user = auth.currentUser


                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("SignIn", "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()

                    }
                }
        }
    }

    fun validateForm(email: String, password: String): Boolean {
        return when {

            TextUtils.isEmpty(email) -> {
                showErrorSnackBar("Please enter an email address")
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

    fun signInSuccess(user: Users) {
        hideProgressDialog()
        startActivity(Intent(this, MainActivity::class.java))
        finish()


    }
}