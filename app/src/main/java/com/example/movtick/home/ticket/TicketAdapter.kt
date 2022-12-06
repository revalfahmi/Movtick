package com.example.movtick.home.ticket

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movtick.R
import com.example.movtick.model.Checkout

class TicketAdapter(private var dataList: List<Checkout>, private val listener: (Checkout) -> Unit) : RecyclerView.Adapter<TicketAdapter.ViewHolder>() {

    lateinit var contextAdapter: Context
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val tvSeat = view.findViewById<TextView>(R.id.tv_seat)

        fun bindItem(data: Checkout, listener: (Checkout) -> Unit, context: Context){
            tvSeat.setText("Seat No. " + data.seat)

            itemView.setOnClickListener {
                listener(data)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflatedView = layoutInflater.inflate(R.layout.row_item_checkout_tiket, parent, false)
        return ViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(dataList[position], listener, contextAdapter)
    }

    override fun getItemCount(): Int = dataList.size
}
