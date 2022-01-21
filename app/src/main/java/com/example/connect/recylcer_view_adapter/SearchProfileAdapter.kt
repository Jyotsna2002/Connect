package com.example.connect.recylcer_view_adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.connect.R
import com.example.connect.model.SearchProfileDataClassItem

class SearchProfileAdapter () : RecyclerView.Adapter<SearchProfileAdapter.HomeViewHolder>()  {
    var Posts= ArrayList<SearchProfileDataClassItem>()
    private var mlistner: onItemClickListener? = null
    interface onItemClickListener {

        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mlistner = listener
    }
    fun setUpdatedData( Posts: ArrayList<SearchProfileDataClassItem>){
        this.Posts=Posts
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.accunts_items, parent, false)
        return HomeViewHolder(view,mlistner)

    }
    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {

        val post = Posts[position]
        holder.user_name.text=post.username
        holder.searchprofilepic.load(post.profile_photo){
            crossfade(true)
            placeholder(R.drawable.ic_launcher_background)
        }
    }

    override fun getItemCount(): Int {
        return Posts.size
    }



    class HomeViewHolder(itemView: View,listener: onItemClickListener?) :
        RecyclerView.ViewHolder(itemView) {

        var searchprofilepic=itemView.findViewById<ImageView>(R.id.user_account_image)
        var user_name=itemView.findViewById<TextView>(R.id.account)



        init {
            itemView.setOnClickListener {
                listener?.onItemClick(adapterPosition)
            }
        }
    }

}