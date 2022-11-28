package com.example.movtick.home.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movtick.R
import com.example.movtick.model.Film

class ComingSoonAdapter(private var data: List<Film>, private val listener: (Film) -> Unit) : RecyclerView.Adapter<ComingSoonAdapter.ViewHolder>() {
    lateinit var contextAdapter: Context
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val tvTitle = view.findViewById<TextView>(R.id.tv_kursi)
        private val tvGenre = view.findViewById<TextView>(R.id.tv_genre)
        private val tvRate = view.findViewById<TextView>(R.id.tv_rate)
        private val ivPoster = view.findViewById<ImageView>(R.id.iv_poster_img)

        fun bindItem(data: Film, listener: (Film) -> Unit, context: Context){
            tvTitle.setText(data.judul)
            tvGenre.setText(data.genre)
            tvRate.setText(data.rating)

            Glide.with(context)
                .load(data.poster)
                .into(ivPoster)

            itemView.setOnClickListener {
                listener(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflatedView = layoutInflater.inflate(R.layout.row_item_coming_soon, parent, false)
        return ViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(data[position], listener, contextAdapter)
    }

    override fun getItemCount(): Int = data.size
}
