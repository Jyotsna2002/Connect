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

class HomePageAdapter () : RecyclerView.Adapter<HomePageAdapter.HomeViewHolder>()  {
    var Posts= ArrayList<HomeDataClassItem>()
    fun setUpdatedData( Posts: ArrayList<HomeDataClassItem>){
        this.Posts=Posts
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post, parent, false)
        return HomeViewHolder(view)

    }
    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {

        val post = Posts[position]
      holder.caption.text=post.caption
        holder.name.text= name
        holder.name2.text= name
        holder.postImage.load(post.post_image?.get(0)?.images?.trim()){
            crossfade(true)
          //  placeholder(R.drawable.i)
        }

    }

    override fun getItemCount(): Int {
        return Posts.size
    }

    class HomeViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        var postImage=itemView.findViewById<ImageView>(R.id.postImage)
        var caption=itemView.findViewById<TextView>(R.id.caption)
        var name=itemView.findViewById<TextView>(R.id.name)
        var name2=itemView.findViewById<TextView>(R.id.name2)
        var likedBy=itemView.findViewById<TextView>(R.id.likedByPeople)
        }

    }