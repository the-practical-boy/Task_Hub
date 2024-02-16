package com.example.taskhub.TaskTrek.activities


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager.LayoutParams.*

import android.widget.TextView
import com.example.taskhub.R
import com.example.taskhub.TaskTrek.Firebase.FirestoreClass
import com.example.taskhub.TaskTrek.models.BaseActivity
import com.google.firebase.auth.FirebaseAuth
import android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN as FLAG_FULLSCREEN


class SplashActivity : BaseActivity() {
    val typingSpeed = 100
    lateinit var intent2:Intent

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        intent2=Intent(this, IntroActivity::class.java)




        window.setFlags(
            FLAG_FULLSCREEN,
            FLAG_FULLSCREEN
        )

        val textView = findViewById<TextView>(R.id.tv_title1)
        val text:String="Managing tasks with task Trek"
        //val pencilAnimation = AnimationUtils.loadAnimation(this, R.anim.splash_text)




        animateText(textView,text)

        // Delay the start of the next activity
        Handler(Looper.getMainLooper()).postDelayed({

            val currentUserID=FirestoreClass().getCurrentuserID()
            if(currentUserID.isNotEmpty()){
                startActivity(Intent(this@SplashActivity,MainActivity::class.java))
            }
            else {
                startActivity(intent2)
            }
            finish() // Optional: Close the splash screen activity to prevent going back to it.
        }, ((text.length * typingSpeed)+1000).toLong())




    }




    private fun animateText(textView: TextView, text: String) {
        var index = 0
        val handler = Handler(Looper.getMainLooper())

        handler.post(object : Runnable {
            override fun run() {
                if (index < text.length) {
                    val currentText = textView.text.toString()
                    val nextChar = text[index]
                    textView.text = "$currentText$nextChar"
                    index++
                    handler.postDelayed(this, typingSpeed.toLong())
                }
            }
        })


    }








    }












