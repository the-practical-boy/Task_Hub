package com.example.taskhub.TaskTrek.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import com.example.taskhub.R
import com.example.taskhub.TaskTrek.models.BaseActivity

class IntroActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        val btn_sign_up_intro:Button=findViewById(R.id.btn_register)
        val btn_sign_in:Button=findViewById(R.id.btn_sign_in)


        window.setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        btn_sign_up_intro.setOnClickListener{

            startActivity(Intent(this, SignUpActivity::class.java))
        }

        btn_sign_in.setOnClickListener{

            startActivity(Intent(this, SignInActivity::class.java))
        }

    }



}