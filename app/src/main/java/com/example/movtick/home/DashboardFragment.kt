package com.example.movtick.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.movtick.R
import com.example.movtick.utils.Preferences
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
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

//        getData()
    }

    private fun getData(){

    }

    private fun currencyFormat(balance: Double, textView: TextView){
        val localID = Locale("in", "ID")
        val format = NumberFormat.getInstance(localID)
        textView.setText(format.format(balance))
    }
}