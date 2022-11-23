package com.example.movtick.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.movtick.HomeActivity
import com.example.movtick.R
import com.example.movtick.sign.signin.SignInActivity
import com.example.movtick.utils.Preferences

class OnboardingOneActivity : AppCompatActivity() {
    private lateinit var btnNext: Button
    private lateinit var btnSkip: Button

    lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_one)

        btnNext = findViewById(R.id.btn_next)
        btnSkip = findViewById(R.id.btn_skip)

        preferences = Preferences(this)

        if (preferences.getValue("onboarding").equals("1")){
            val intent = Intent(this@OnboardingOneActivity, HomeActivity::class.java)
            startActivity(intent)

            finishAffinity()
        }
        btnNext.setOnClickListener {
            preferences.setValue("onboarding", "1")
            startActivity(Intent(this@OnboardingOneActivity, OnboardingTwoActivity::class.java))
            finishAffinity()
        }

        btnSkip.setOnClickListener {
            preferences.setValue("onboarding", "1")
            val intent = Intent(this@OnboardingOneActivity, SignInActivity::class.java)
            startActivity(intent)

            finishAffinity()
        }
    }
}