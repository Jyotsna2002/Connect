package com.example.connect.recylcer_view_adapter

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.connect.R
import com.example.connect.model.CommentDataClass
import com.example.connect.model.HomeStoryDataClass
import com.example.connect.model.Reply

class CreateChildCommentAdapter() : RecyclerView.Adapter<CreateChildCommentAdapter.HomeViewHolder>() {
    var Posts = ArrayList<Reply>()
    private var mlistner: onItemClickListener? = null

    interface onItemClickListener {

        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mlistner = listener
    }

    fun setUpdatedData(Posts: ArrayList<Reply>) {
        Log.i("Reply2", "Respone $Posts")
        this.Posts = Posts
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.child_comment_list_item, parent, false)
        return HomeViewHolder(view, mlistner)

    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {

        val post = Posts[position]
        holder.userName.text = post.author
        holder.content.text = post.content

        holder.pic.load(post.profile_picture) {
            ImageView.ScaleType.CENTER_CROP
            crossfade(true)
            placeholder(R.drawable.ic_baseline_circle_24)

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

        var pic = itemView.findViewById<ImageView>(R.id.pic2)
        var userName = itemView.findViewById<TextView>(R.id.Username2)
        var content = itemView.findViewById<TextView>(R.id.comment2)
        var child=itemView.findViewById<RecyclerView>(R.id.childComment)


        init {
            itemView.setOnClickListener {
                listener?.onItemClick(adapterPosition)
            }
        }
    }

}