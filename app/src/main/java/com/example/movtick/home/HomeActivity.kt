package com.example.movtick.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.movtick.R
import com.example.movtick.home.dashboard.DashboardFragment

class HomeActivity : AppCompatActivity() {
    private lateinit var btnDashboard: ImageView
    private lateinit var btnTicket: ImageView
    private lateinit var btnSettings: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        btnDashboard = findViewById(R.id.btn_home)
        btnTicket = findViewById(R.id.btn_ticket)
        btnSettings = findViewById(R.id.btn_settings)

        val fragmentHome = DashboardFragment()
        val fragmentTicket = TicketFragment()
        val fragmentSetting = SettingFragment()

        setFragment(fragmentHome)

        btnDashboard.setOnClickListener {
            setFragment(fragmentHome)

            changeIcon(btnDashboard, R.drawable.ic_home_active)
            changeIcon(btnTicket, R.drawable.ic_tiket)
            changeIcon(btnSettings, R.drawable.ic_profile)
        }

        btnTicket.setOnClickListener {
            setFragment(fragmentTicket)

            changeIcon(btnDashboard, R.drawable.ic_home)
            changeIcon(btnTicket, R.drawable.ic_tiket_active)
            changeIcon(btnSettings, R.drawable.ic_profile)
        }

        btnSettings.setOnClickListener {
            setFragment(fragmentSetting)

            changeIcon(btnDashboard, R.drawable.ic_home)
            changeIcon(btnTicket, R.drawable.ic_tiket)
            changeIcon(btnSettings, R.drawable.ic_profile_active)
        }
    }

    private fun changeIcon(image: ImageView, resId: Int){
        image.setImageResource(resId)
    }

    private fun setFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.layout_frame, fragment)
        fragmentTransaction.commit()
    }
}