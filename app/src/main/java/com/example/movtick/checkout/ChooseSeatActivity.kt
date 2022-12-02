package com.example.movtick.checkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.movtick.DetailActivity
import com.example.movtick.R
import com.example.movtick.model.Checkout
import com.example.movtick.model.Film

class ChooseSeatActivity : AppCompatActivity() {
    private lateinit var ivBack: ImageView
    private lateinit var ivSeatD3: ImageView
    private lateinit var ivSeatD4: ImageView
    private lateinit var btnChooseSeat: Button
    private lateinit var tvFilmName: TextView

    var statusD3: Boolean = false
    var statusD4: Boolean = false
    var total: Int = 0

    private var dataList = ArrayList<Checkout>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_seat)

        ivBack = findViewById(R.id.iv_back)
        ivSeatD3 = findViewById(R.id.seat_d3)
        ivSeatD4 = findViewById(R.id.seat_d4)
        btnChooseSeat = findViewById(R.id.btn_choose_seat)
        tvFilmName = findViewById(R.id.tv_film_name)

        val data = intent.getParcelableExtra<Film>("data")
        tvFilmName.text = data?.judul ?: ""

        ivBack.setOnClickListener {
            val intent = Intent(this@ChooseSeatActivity, DetailActivity::class.java)
            startActivity(intent)
        }

        ivSeatD3.setOnClickListener {
            if (statusD3){
                ivSeatD3.setImageResource(R.drawable.ic_empty)
                statusD3 = false
                total -= 1
                buyTicket(total)
            } else {
                ivSeatD3.setImageResource(R.drawable.ic_selected)
                statusD3 = true
                total += 1
                buyTicket(total)

                val data = Checkout("A3", "50000")
                dataList.add(data)
            }
        }

        ivSeatD4.setOnClickListener {
            if (statusD4){
                ivSeatD4.setImageResource(R.drawable.ic_empty)
                statusD4 = false
                total -= 1
                buyTicket(total)
            } else {
                ivSeatD4.setImageResource(R.drawable.ic_selected)
                statusD4 = true
                total += 1
                buyTicket(total)

                val data = Checkout("A4 ", "50000")
                dataList.add(data)
            }
        }

        btnChooseSeat.setOnClickListener {
            val intent = Intent(this@ChooseSeatActivity, CheckoutActivity::class.java).putExtra("data", dataList)
            startActivity(intent)
        }
    }

    private fun buyTicket(total: Int) {
        if (total == 0){
            btnChooseSeat.setText("Buy Ticket")
            btnChooseSeat.visibility = View.INVISIBLE
        }else{
            btnChooseSeat.setText("Buy Ticket ("+total+")")
            btnChooseSeat.visibility = View.VISIBLE
        }
    }
}