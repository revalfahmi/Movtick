package com.example.movtick.home.dashboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.movtick.DetailActivity
import com.example.movtick.R
import com.example.movtick.model.Film
import com.example.movtick.utils.Preferences
import com.google.firebase.database.*
import java.text.NumberFormat
import java.util.Locale

class DashboardFragment : Fragment() {
    private lateinit var tvName: TextView
    private lateinit var tvBalance: TextView
    private lateinit var ivProfile: ImageView
    private lateinit var rvNowPlaying: RecyclerView
    private lateinit var rvComingSoon: RecyclerView

    private lateinit var preferences: Preferences
    private lateinit var mDatabase: DatabaseReference

    private var dataList = ArrayList<Film>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        tvName = requireView().findViewById(R.id.tv_name)
        tvBalance = requireView().findViewById(R.id.tv_balance)
        ivProfile = requireView().findViewById(R.id.iv_profile)
        rvNowPlaying = requireView().findViewById(R.id.rv_now_playing)
        rvComingSoon = requireView().findViewById(R.id.rv_coming_soon)

        preferences = Preferences(requireActivity().applicationContext)
        mDatabase = FirebaseDatabase.getInstance().getReference("Film")

        tvName.setText(preferences.getValue("nama"))
        if (preferences.getValue("saldo").equals("")){
            tvBalance.text = ""
        }else{
            currencyFormat(preferences.getValue("saldo")!!.toDouble(), tvBalance)
        }

        Glide.with(this)
            .load(preferences.getValue("url"))
            .apply(RequestOptions.circleCropTransform())
            .into(ivProfile)

        rvNowPlaying.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvComingSoon.layoutManager = LinearLayoutManager(context)

        getData()
    }

    private fun getData(){
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                dataList.clear()
                for (getDataSnapshot in snapshot.children){
                    val film = getDataSnapshot.getValue(Film::class.java)
                    dataList.add(film!!)
                }


                rvNowPlaying.adapter = NowPlayingAdapter(dataList){
                    val intent = Intent(context, DetailActivity::class.java).putExtra("data", it)
                }

                rvComingSoon.adapter = ComingSoonAdapter(dataList){

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, ""+error.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun currencyFormat(balance: Double, textView: TextView){
        val localID = Locale("in", "ID")
        val format = NumberFormat.getInstance(localID)
        textView.setText(format.format(balance))
    }
}