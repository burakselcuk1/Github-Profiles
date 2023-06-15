package com.example.githubtask.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubtask.R
import com.example.githubtask.common.ItemClickListener
import com.example.githubtask.model.GithubUser

class GithubUserAdapter(private val userList: ArrayList<GithubUser>,
                        private val itemClickListener: ItemClickListener
) : RecyclerView.Adapter<GithubUserAdapter.DataViewHolder>() {
    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: GithubUser) {
            val textViewUserName = itemView.findViewById<TextView>(R.id.textViewUserName)
            val userFollowers = itemView.findViewById<TextView>(R.id.followers)
            val userFollowing = itemView.findViewById<TextView>(R.id.following)
            val imageView = itemView.findViewById<ImageView>(R.id.imageView)

            textViewUserName.text = itemView.context.getString(R.string.username) + " " + user.login
            userFollowers.text = itemView.context.getString(R.string.followers) + " " +  user.followers.toString()
            userFollowing.text = itemView.context.getString(R.string.following) + " " +  user.following.toString()
            Glide.with(itemView.context).load(user.avatarUrl).into(imageView)


        }


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.users_item, parent,
                false
            )
        )

    override fun getItemCount(): Int = userList.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val user = userList[position]
        holder.bind(user)
        holder.itemView.setOnClickListener {
            itemClickListener.onItemClicked(user)
        }
    }

    fun addData(list: List<GithubUser>) {
        userList.addAll(list)
    }

    fun clearData() {
        userList.clear()
        notifyDataSetChanged()
    }
}
