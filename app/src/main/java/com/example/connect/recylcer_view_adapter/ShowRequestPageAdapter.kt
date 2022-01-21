package com.example.connect.recylcer_view_adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.connect.Dashboard.Companion.name
import com.example.connect.R
import com.example.connect.model.HomeDataClassItem
import com.example.connect.model.ShowFollowRequestDataClass
import okhttp3.ResponseBody

class ShowRequestPageAdapter () : RecyclerView.Adapter<ShowRequestPageAdapter.HomeViewHolder>()  {
    var Posts= ArrayList<ShowFollowRequestDataClass>()
    private var mlistner1: onItemClickListener1? = null
    private var mlistner2: onItemClickListener2? = null
    interface onItemClickListener1 {

        fun onItemClick(position: Int)
    }
    interface onItemClickListener2 {

        fun onItemClick2(position: Int)
    }

    fun setOnItemClickListener(listener1: onItemClickListener1) {
        mlistner1 = listener1
    }
    fun setOnItemClickListener2(listener2: onItemClickListener2){
        mlistner2=listener2
    }
    fun setUpdatedData(Posts: ArrayList<ShowFollowRequestDataClass>){
        this.Posts=Posts
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.request_items, parent, false)
        return HomeViewHolder(view,mlistner1,mlistner2)

    }
    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {

        val post = Posts[position]
        holder.userName.text= post.from_user_username
        holder.profileImage.load(post.from_user_profile_photo){
            crossfade(true)
            placeholder(R.drawable.ic_launcher_background)
        }

    }

    override fun getItemCount(): Int {
        return Posts.size
    }

    class HomeViewHolder(itemView: View,listener1: onItemClickListener1?,listener2: onItemClickListener2?) :
        RecyclerView.ViewHolder(itemView) {

        var profileImage=itemView.findViewById<ImageView>(R.id.requestingProfile)
        var userName=itemView.findViewById<TextView>(R.id.user2)
        var Confirm=itemView.findViewById<Button>(R.id.confirmBtn)
        var Delete=itemView.findViewById<Button>(R.id.deleteBtn)


        init {
            Confirm.setOnClickListener {
                listener1?.onItemClick(adapterPosition)
            }

            Delete.setOnClickListener {
                listener2?.onItemClick2(adapterPosition)
            }
        }
    }

    }