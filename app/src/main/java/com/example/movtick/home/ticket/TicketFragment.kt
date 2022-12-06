package com.example.movtick.home.ticket

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movtick.R
import com.example.movtick.home.dashboard.ComingSoonAdapter
import com.example.movtick.model.Film
import com.example.movtick.utils.Preferences
import com.google.firebase.database.*

class TicketFragment : Fragment() {

    private lateinit var rvTicket: RecyclerView
    private lateinit var tvTotal: TextView

    private lateinit var preferences: Preferences
    private lateinit var mDatabase: DatabaseReference
    private var dataList = ArrayList<Film>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ticket, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        preferences = Preferences(requireContext())
        mDatabase = FirebaseDatabase.getInstance().getReference("Film")
        rvTicket = requireView().findViewById(R.id.rv_ticket)
        tvTotal = requireView().findViewById(R.id.tv_total)

        rvTicket.layoutManager = LinearLayoutManager(context)
        getData()
    }

    private fun getData() {
        mDatabase.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                dataList.clear()
                for (getDataSnapshot in snapshot.children){
                    val film = getDataSnapshot.getValue(Film::class.java)
                    dataList.add(film!!)
                }

                rvTicket.adapter = ComingSoonAdapter(dataList){
                    val intent = Intent(context, TicketActivity::class.java).putExtra("data", it)
                    startActivity(intent)
                }

                tvTotal.setText("${dataList.size} Movies")
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, ""+error.message, Toast.LENGTH_SHORT).show()
            }

        })
    }
}