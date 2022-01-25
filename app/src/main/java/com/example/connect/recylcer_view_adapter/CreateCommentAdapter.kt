package com.example.connect.recylcer_view_adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.connect.R
import com.example.connect.model.CommentDataClass
import com.example.connect.model.Reply

class CreateCommentAdapter() : RecyclerView.Adapter<CreateCommentAdapter.HomeViewHolder>() {
    var Posts = ArrayList<CommentDataClass>()
    var reply = ArrayList<Reply>()
    private var mlistner: onItemClickListener? = null

    interface onItemClickListener {

        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mlistner = listener
    }

    fun setUpdatedData(Posts: ArrayList<CommentDataClass>) {
        this.Posts = Posts
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.comment_list_items, parent, false)
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
        if(post.replies?.isNotEmpty() == true)
        {
            for (i in post.replies.listIterator(0))
            {
                reply.add(i)
            }
            val commentAdapter=CreateChildCommentAdapter()
            holder.child.adapter=commentAdapter
            Log.i("Reply", "Respone "+reply)
            commentAdapter.setUpdatedData(reply)
        }



    }

    override fun getItemCount(): Int {
        return Posts.size
    }

    class HomeViewHolder(itemView: View, listener: onItemClickListener?) :
        RecyclerView.ViewHolder(itemView) {

        var pic = itemView.findViewById<ImageView>(R.id.pic)
        var userName = itemView.findViewById<TextView>(R.id.Username)
        var content = itemView.findViewById<TextView>(R.id.comment)
        var reply = itemView.findViewById<TextView>(R.id.Reply)
        var child=itemView.findViewById<RecyclerView>(R.id.childComment)


        init {
            reply.setOnClickListener {
                listener?.onItemClick(adapterPosition)
            }
        }
    }

}