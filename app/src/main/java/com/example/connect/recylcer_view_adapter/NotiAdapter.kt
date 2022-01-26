package com.example.connect.recylcer_view_adapter

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.connect.R
import com.example.connect.Views.Dashboard.notification.LikedBy_Fragment.Companion.Text7
import com.example.connect.Views.Dashboard.notification.Request_Fragment
import com.example.connect.model.HomeStoryDataClass
import com.example.connect.model.Notificationpage

class NotiAdapter() : RecyclerView.Adapter<NotiAdapter.HomeViewHolder>() {
    var Posts = ArrayList<Notificationpage>()
    private var mlistner: onItemClickListener? = null

    interface onItemClickListener {

        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mlistner = listener
    }

    fun setUpdatedData(Posts: ArrayList<Notificationpage>) {
        if (Posts.isEmpty())
        {
            Text7.visibility=View.VISIBLE
        }
        else
        {
            Text7.visibility=View.GONE
        }
        this.Posts = Posts
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.liked_by_items, parent, false)
        return HomeViewHolder(view, mlistner)

    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {

        val post = Posts[position]
        holder.Name.text = post.sender_username
        holder.Noti.text = post.text
        holder.UserImage.load(post.profile_picture) {
            ImageView.ScaleType.CENTER_CROP
            crossfade(true)
            placeholder(R.drawable.ic_launcher_background)

        }
        holder.LikedPost.load(post.post_preview_image) {
            ImageView.ScaleType.CENTER_CROP
            crossfade(true)
            placeholder(R.drawable.ic_launcher_background)

        }
//        val gd = holder.HomeImage.background as GradientDrawable
//        //gd.setCornerRadius(10)
//        gd.setStroke(3, ContextCompat.getColor(context!!, R.color.gray))
       // holder.HomeImage.background.setTint(ContextCompat.getColor(context,R.color.gray ))
    }

    override fun getItemCount(): Int {
        return Posts.size
    }

    class HomeViewHolder(itemView: View, listener: onItemClickListener?) :
        RecyclerView.ViewHolder(itemView) {

        var UserImage = itemView.findViewById<ImageView>(R.id.othersProfile)
        var Name = itemView.findViewById<TextView>(R.id.user)
        var LikedPost = itemView.findViewById<ImageView>(R.id.likedPost)
        var Noti = itemView.findViewById<TextView>(R.id.noti)

        init {
            itemView.setOnClickListener {
                listener?.onItemClick(adapterPosition)
            }
        }
    }

}