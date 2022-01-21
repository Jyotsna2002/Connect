package com.example.connect.recylcer_view_adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.connect.Dashboard.Companion.name
import com.example.connect.R
import com.example.connect.model.HomeDataClassItem
import com.example.connect.model.OthersPost

class OthersProfileAdapter () : RecyclerView.Adapter<OthersProfileAdapter.HomeViewHolder>()  {
    var Posts= ArrayList<OthersPost>()
    private var mlistner: onItemClickListener? = null
    interface onItemClickListener {

        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mlistner = listener
    }
    fun setUpdatedData( Posts: ArrayList<OthersPost>){
        this.Posts=Posts
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.profile_items, parent, false)
        return HomeViewHolder(view,mlistner)

    }
    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {

        val post = Posts[position]
        holder.otherspost.load(post.post_image?.get(0)?.images){
            crossfade(true)
          //  placeholder(R.drawable.i)
        }

    }

    override fun getItemCount(): Int {
        return Posts.size
    }

    class HomeViewHolder(itemView: View,listener: onItemClickListener?) :
        RecyclerView.ViewHolder(itemView) {

        var otherspost=itemView.findViewById<ImageView>(R.id.otherspost)



        init {
            itemView.setOnClickListener {
                listener?.onItemClick(adapterPosition)
            }
        }
    }

    }