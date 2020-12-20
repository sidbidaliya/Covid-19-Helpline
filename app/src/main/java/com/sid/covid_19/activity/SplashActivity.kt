package com.sid.covid_19.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import com.sid.covid_19.R

class SplashActivity : AppCompatActivity() {

    private val splashTimeOut : Long = 3000 // 1 sec
    lateinit var llSplash : LinearLayout
    lateinit var sid : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        llSplash = findViewById(R.id.llSplash)
        sid = findViewById(R.id.sid)

        llSplash.animation = AnimationUtils.loadAnimation(this,R.anim.bottom_anim)
        sid.animation = AnimationUtils.loadAnimation(this,R.anim.up_anim)

        Handler().postDelayed({
            // This method will be executed once the timer is over
            // Start your app main activity

            startActivity(Intent(this@SplashActivity,MainActivity::class.java))

            // close this activity
            finish()
        }, splashTimeOut)

    }
}
