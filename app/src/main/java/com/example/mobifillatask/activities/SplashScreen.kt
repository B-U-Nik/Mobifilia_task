package com.example.mobifillatask.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mobifillatask.R
import com.example.mobifillatask.utils.ConstantApp
import com.example.mobifillatask.utils.SessionManager

class SplashScreen : AppCompatActivity() {


    lateinit var sessionManager: SessionManager

    private var translateAnimation: Animation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        sessionManager = SessionManager(this)
        val imageView: ImageView = findViewById(R.id.imageView)

        // Load the translate animation from the XML file
        translateAnimation = AnimationUtils.loadAnimation(this, R.anim.translate_anim)

        // Start the animation on the ImageView
        imageView.startAnimation(translateAnimation)

        if (ConstantApp.checkInternetConnection(this)) {

            openScreen()

        } else {
            Toast.makeText(this, "Please Check Your Internet Connection !", Toast.LENGTH_LONG)
                .show()

            openScreen()
        }
    }

    fun openScreen() {
        Handler(Looper.getMainLooper()).postDelayed({

          val   sharedPreferences = getSharedPreferences("LOGIN", MODE_PRIVATE)
            val isLogin = sharedPreferences.getBoolean("isLogin",false)
             if (isLogin) {

                val toMain = Intent(this, MainActivity::class.java)
                startActivity(toMain)
                finish()

            } else {

                val toLogin = Intent(this, LoginScreen::class.java)
                startActivity(toLogin)
                finish()
            }
        }, 1000 + (translateAnimation?.duration!!))
    }


}
