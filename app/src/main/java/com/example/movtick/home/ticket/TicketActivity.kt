package com.example.movtick.home.ticket

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movtick.R
import com.example.movtick.model.Checkout
import com.example.movtick.model.Film

class TicketActivity : AppCompatActivity() {
    private lateinit var tvTitle: TextView
    private lateinit var tvGenre: TextView
    private lateinit var tvRate: TextView
    private lateinit var ivPoster: ImageView
    private lateinit var rvItem: RecyclerView
    private lateinit var tvDate: TextView
    private lateinit var tvCinema: TextView
    private lateinit var ivClose: ImageView
    private lateinit var ivQr: ImageView

    private var dataList = ArrayList<Checkout>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket)

        tvTitle = findViewById(R.id.tv_title_ticket)
        tvGenre = findViewById(R.id.tv_genre_ticket)
        tvRate = findViewById(R.id.tv_rate_ticket)
        ivPoster = findViewById(R.id.iv_poster_ticket)
        rvItem = findViewById(R.id.rv_item_ticket)
        tvDate = findViewById(R.id.tv_date_ticket)
        tvCinema = findViewById(R.id.tv_cinema_ticket)
        ivClose = findViewById(R.id.iv_close)
        ivQr = findViewById(R.id.iv_qr)

        var data = intent.getParcelableExtra<Film>("data")

        if (data != null) {
            tvTitle.text = data.judul
            tvGenre.text = data.genre
            tvRate.text = data.rating

            Glide.with(this)
                .load(data.poster)
                .into(ivPoster)

            rvItem.layoutManager = LinearLayoutManager(this)
            dataList.add(Checkout("D3", ""))
            dataList.add(Checkout("D4", ""))

            rvItem.adapter = TicketAdapter(dataList){

            }
        }

        ivClose.setOnClickListener {
            finish()
        }

        ivQr.setOnClickListener {
            showDialog("Silahkan melakukan scanning pada counter tiket terdekat")
        }
    }

    private fun showDialog(title: String) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_qr)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val tvDesc = dialog.findViewById<TextView>(R.id.tv_desc)
        tvDesc.text = title

        val btnClose = dialog.findViewById<Button>(R.id.btn_close)
        btnClose.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
}