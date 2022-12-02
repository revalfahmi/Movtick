package com.example.movtick.checkout

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movtick.R
import com.example.movtick.model.Checkout
import java.text.NumberFormat
import java.util.Locale

class CheckoutAdapter(private var dataList: List<Checkout>, private val listerner: (Checkout) -> Unit) : RecyclerView.Adapter<CheckoutAdapter.ViewHolder>() {

    lateinit var context: Context
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val tvSeat: TextView = view.findViewById(R.id.tv_seat)
        private val tvPrice: TextView = view.findViewById(R.id.tv_price)

        fun bindItem(data: Checkout, listerner: (Checkout) -> Unit, context: Context){

            val localID = Locale("id", "ID")
            val format = NumberFormat.getInstance(localID)
            tvPrice.setText(format.format(data.price!!.toDouble()))
            tvSeat.setText(data.seat)

            if (data.seat!!.startsWith("Total")){
                tvSeat.setText(data.seat)
                tvSeat.setCompoundDrawables(null, null, null, null)
            } else {
                tvSeat.setText("Seat No" + data.seat)
            }

            itemView.setOnClickListener {
                listerner(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        context = parent.context
        val layoutInflatedView = layoutInflater.inflate(R.layout.row_item_checkout, parent, false)
        return ViewHolder(layoutInflatedView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(dataList[position], listerner, context)
    }

    override fun getItemCount(): Int = dataList.size
}
