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
import com.example.connect.model.HomeStoryDataClass

class HomeStoryAdapter(private val context: Context) : RecyclerView.Adapter<HomeStoryAdapter.HomeViewHolder>() {
    var Posts = ArrayList<HomeStoryDataClass>()
    private var mlistner: onItemClickListener? = null

    interface onItemClickListener {

        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mlistner = listener
    }

    fun setUpdatedData(Posts: ArrayList<HomeStoryDataClass>) {
        this.Posts = Posts
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.home_story_items, parent, false)
        return HomeViewHolder(view, mlistner)

    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {

        val post = Posts[position]
        holder.Name.text = post.username

        if (post.profile_photo==null){
            holder.HomeImage.setImageResource(R.drawable.photo)
        }else {

            holder.HomeImage.load(post.profile_photo) {
                ImageView.ScaleType.CENTER_CROP
                crossfade(true)
                placeholder(R.drawable.ic_launcher_background)

            }
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

        var HomeImage = itemView.findViewById<ImageView>(R.id.homeImage)
        var Name = itemView.findViewById<TextView>(R.id.Name)


        init {
            itemView.setOnClickListener {
                listener?.onItemClick(adapterPosition)
            }
        }
    }

}