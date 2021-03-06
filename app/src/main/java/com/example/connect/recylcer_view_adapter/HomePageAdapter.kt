package com.example.connect.recylcer_view_adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.connect.R
import com.example.connect.Views.Dashboard.Home_Fragment.Companion.Text5
import com.example.connect.model.HomeDataClassItem


class HomePageAdapter : RecyclerView.Adapter<HomePageAdapter.HomeViewHolder>()  {
    var Posts= ArrayList<HomeDataClassItem>()
    private var mlistner: onItemClickListener? = null
    private var mlistner2: onItemClickListener2? = null
    private var mlistner3: onItemClickListener3? = null
    private var mlistner4: onItemClickListener4? = null
    private var mlistner5: onItemClickListener5? = null

    interface onItemClickListener {

        fun onItemClick(position: Int)
    }
    interface onItemClickListener2 {

        fun onItemClick2(position: Int)
    }

    interface onItemClickListener3 {

        fun onItemClick3(position: Int)
    }
    interface onItemClickListener4 {

        fun onItemClick4(position: Int)
    }
    interface onItemClickListener5 {

        fun onItemClick5(position: Int)
    }
    fun setOnItemClickListener(listener: onItemClickListener) {
        mlistner = listener
    }
    fun setOnItemClickListener2(listener2: onItemClickListener2){
        mlistner2=listener2
    }
    fun setOnItemClickListener3(listener3: onItemClickListener3){
        mlistner3=listener3
    }
    fun setOnItemClickListener4(listener4: onItemClickListener4){
        mlistner4=listener4
    }
    fun setOnItemClickListener5(listener5: onItemClickListener5){
        mlistner5=listener5
    }
    fun setUpdatedData( Posts: ArrayList<HomeDataClassItem>){
        if (Posts.isEmpty())
        {
            Text5.visibility=View.VISIBLE
        }
        else
        {
            Text5.visibility=View.GONE
        }
        this.Posts=Posts
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post, parent, false)
        return HomeViewHolder(view,mlistner,mlistner2,mlistner3,mlistner4,mlistner5)

    }
    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {

        val post = Posts[position]
      holder.caption.text=post.caption
        holder.name.text= post.user_name
        holder.name2.text= post.user_name
        holder.likedBy.text=post.no_of_likes.toString()
        holder.postImage.load(post.post_image?.get(0)?.images?.trim()){
            crossfade(true)
            placeholder(R.drawable.ic_launcher_background)
        }
        if(post.profile_picture==null){
            holder.userImage.setImageResource(R.drawable.photo)
        }else {
            holder.userImage.load(post.profile_picture) {
                crossfade(true)
                placeholder(R.drawable.ic_baseline_circle_24)
            }
        }
        if(post.Like == true) {
            holder.like.setImageResource(R.drawable.ic_thumbs_up2)
        }
        else{
            holder.like.setImageResource(R.drawable.ic_thumbs_up)
        }
        if(post.bookmarked == true) {
            holder.Bookmark.setImageResource(R.drawable.ic_bookmark2)
        }
        else{
            holder.Bookmark.setImageResource(R.drawable.ic_bookmark)
        }

    }

    override fun getItemCount(): Int {
        return Posts.size
    }

    class HomeViewHolder(itemView: View,listener: onItemClickListener?,listener2: onItemClickListener2?,listener3: onItemClickListener3?,listener4: onItemClickListener4?,listener5: onItemClickListener5?) :
        RecyclerView.ViewHolder(itemView) {

        var postImage=itemView.findViewById<ImageView>(R.id.postImage)
        var caption=itemView.findViewById<TextView>(R.id.caption)
        var name=itemView.findViewById<TextView>(R.id.name)
        var name2=itemView.findViewById<TextView>(R.id.name2)
        var likedBy=itemView.findViewById<TextView>(R.id.likedByPeople)
        var like=itemView.findViewById<ImageView>(R.id.Like)
        var share=itemView.findViewById<ImageView>(R.id.ShareBtn)
        val comment=itemView.findViewById<ImageView>(R.id.commentImg)
        val Bookmark=itemView.findViewById<ImageView>(R.id.imageView5)
        val userImage=itemView.findViewById<ImageView>(R.id.user_image)


        init {

            name2.setOnClickListener {
                listener?.onItemClick(adapterPosition)
            }
            like.setOnClickListener {

                listener2?.onItemClick2(adapterPosition)

            }
            share.setOnClickListener {
                listener3?.onItemClick3(adapterPosition)

            }
            comment.setOnClickListener {
                listener4?.onItemClick4(adapterPosition)

            }
            Bookmark.setOnClickListener {
                listener5?.onItemClick5(adapterPosition)

            }
        }
    }

    }