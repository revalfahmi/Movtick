package com.example.movtick.checkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movtick.R
import com.example.movtick.SuccessActivity
import com.example.movtick.model.Checkout
import com.example.movtick.utils.Preferences

class CheckoutActivity : AppCompatActivity() {
    private lateinit var tvDate: TextView
    private lateinit var tvCinema: TextView
    private lateinit var rvItems: RecyclerView
    private lateinit var tvBalance: TextView
    private lateinit var btnCancel: Button
    private lateinit var btnPayNow: Button

    private var dataList = ArrayList<Checkout>()
    private var total:Int = 0
    private lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        tvDate = findViewById(R.id.tv_date)
        tvCinema = findViewById(R.id.tv_cinema)
        rvItems = findViewById(R.id.rv_seat)
        tvBalance = findViewById(R.id.tv_balance)
        btnCancel = findViewById(R.id.btn_cancel)
        btnPayNow = findViewById(R.id.btn_pay_now)

        preferences = Preferences(this)
        dataList = intent.getSerializableExtra("data") as ArrayList<Checkout>

        for (data in dataList.indices){
            total += dataList[data].price!!.toInt()
        }

        dataList.add(Checkout("Total to be paid", total.toString()))

        rvItems.layoutManager = LinearLayoutManager(this)
        rvItems.adapter = CheckoutAdapter(dataList){

        }

        btnPayNow.setOnClickListener {
            val intent = Intent(this@CheckoutActivity, SuccessActivity::class.java)
            startActivity(intent)
        }

        btnCancel.setOnClickListener {
            val intent = Intent(this@CheckoutActivity, ChooseSeatActivity::class.java)
            startActivity(intent)
        }

    }
}