package com.example.swearvoca

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

/**
 * 메인 액티비티 전
 * 어플 이미지를 1초동안
 * 보여주는 스플래시
 */
class Splash : AppCompatActivity() {

    val SPLASH_TIME : Long = 1000
//메인액티비티 전 스플래시 화면
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, SPLASH_TIME)
    }
}