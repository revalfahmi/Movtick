package com.example.movtick.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.movtick.R
import com.example.movtick.sign.signin.SignInActivity

class OnboardingThreeActivity : AppCompatActivity() {
    private lateinit var btnStart: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_three)

        btnStart = findViewById(R.id.btn_start)

        btnStart.setOnClickListener {
            val intent = Intent(this@OnboardingThreeActivity, SignInActivity::class.java)
            startActivity(intent)

            finishAffinity()
        }
    }
}