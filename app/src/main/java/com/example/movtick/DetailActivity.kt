package com.example.movtick

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movtick.chooseseat.ChooseSeatActivity
import com.example.movtick.model.Film
import com.example.movtick.model.Play
import com.google.firebase.database.*

class DetailActivity : AppCompatActivity() {
    private lateinit var ivPoster: ImageView
    private lateinit var tvTitle: TextView
    private lateinit var tvGenre: TextView
    private lateinit var tvRating: TextView
    private lateinit var tvDesc: TextView
    private lateinit var rvActors: RecyclerView
    private lateinit var btnChooseSeat: Button

    lateinit var mDatabase: DatabaseReference
    var dataList = ArrayList<Play>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        ivPoster = findViewById(R.id.iv_poster)
        tvTitle = findViewById(R.id.tv_title)
        tvGenre = findViewById(R.id.tv_genre)
        tvRating = findViewById(R.id.tv_rate)
        tvDesc = findViewById(R.id.tv_description)
        rvActors = findViewById(R.id.rv_actor)
        btnChooseSeat = findViewById(R.id.btn_choose_seat)

        // ambil data yang dikirim dari dashboard
        val data = intent.getParcelableExtra<Film>("data")

        if (data != null) {
            mDatabase = FirebaseDatabase.getInstance().getReference("Film")
                .child(data.judul.toString())
                .child("play")

            tvTitle.text = data.judul
            tvGenre.text = data.genre
            tvRating.text = data.rating
            tvDesc.text = data.desc

            Glide.with(this)
                .load(data.poster)
                .into(ivPoster)

            rvActors.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

            getData()
        }

        btnChooseSeat.setOnClickListener {
            val intent = Intent(this@DetailActivity, ChooseSeatActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getData() {
        mDatabase.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                dataList.clear()

                for (getDataSnapshot in snapshot.children){
                    val film = getDataSnapshot.getValue(Play::class.java)
                    dataList.add(film!!)
                }

                rvActors.adapter = PlayAdapter(dataList){

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@DetailActivity, ""+error.message, Toast.LENGTH_SHORT).show()
            }

        })
    }
}