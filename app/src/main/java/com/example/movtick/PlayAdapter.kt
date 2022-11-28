package com.example.movtick

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.movtick.model.Film
import com.example.movtick.model.Play

class PlayAdapter(private var dataList: List<Play>, private val listener: (Play) -> Unit) : RecyclerView.Adapter<PlayAdapter.ViewHolder>() {

    lateinit var contextAdapter: Context
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val ivPhoto: ImageView = view.findViewById(R.id.iv_poster_img)
        private val tvActor: TextView = view.findViewById(R.id.tv_name_actor)

        fun bindItem(data: Play, listener: (Play) -> Unit, context: Context){
            tvActor.setText(data.nama)

            Glide.with(context)
                .load(data.url)
                .apply(RequestOptions.circleCropTransform())
                .into(ivPhoto)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflatedView = layoutInflater.inflate(R.layout.row_item_actors, parent, false)
        return ViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(dataList[position], listener, contextAdapter)
    }

    override fun getItemCount(): Int = dataList.size
}
