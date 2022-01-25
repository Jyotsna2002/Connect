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
import com.example.connect.Views.Dashboard.search.TagsSearch_Fragment.Companion.Text3
import com.example.connect.model.HomeDataClassItem
import com.example.connect.model.OthersPost
import com.example.connect.model.SearchProfileDataClassItem
import com.example.connect.model.SearchTagDataClass

class SearchTagAdapter () : RecyclerView.Adapter<SearchTagAdapter.HomeViewHolder>() {
    var Posts = ArrayList<SearchTagDataClass>()
    private var mlistner: onItemClickListener? = null

    interface onItemClickListener {

        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mlistner = listener
    }

    fun setUpdatedData(Posts: ArrayList<SearchTagDataClass>) {
        if (Posts.isEmpty())
        {
            Text3.visibility=View.VISIBLE
        }
        else
        {
            Text3.visibility=View.GONE
        }
        this.Posts = Posts
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tags_items, parent, false)
        return HomeViewHolder(view, mlistner)

    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {

        val post = Posts[position]
        holder.tagName.text = "#"+post.tag

    }

    override fun getItemCount(): Int {
        return Posts.size
    }


    class HomeViewHolder(itemView: View, listener: onItemClickListener?) :
        RecyclerView.ViewHolder(itemView) {


        var tagName = itemView.findViewById<TextView>(R.id.tag)


        init {
            tagName.setOnClickListener {
                listener?.onItemClick(adapterPosition)
            }
        }
    }
}
