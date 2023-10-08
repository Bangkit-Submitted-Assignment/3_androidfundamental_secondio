package com.dicoding.myfirstsubmissionfundamental.ui.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.myfirstsubmissionfundamental.data.response.ItemsItem
import com.dicoding.myfirstsubmissionfundamental.databinding.ItemReviewBinding
import com.dicoding.myfirstsubmissionfundamental.ui.activity.DetailActivity
import com.dicoding.myfirstsubmissionfundamental.ui.activity.MainActivity

class UserAdapter(private val mainActivity: MainActivity) : ListAdapter<ItemsItem, UserAdapter.MyViewHolder>(
    DIFF_CALLBACK
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
        holder.itemView.setOnClickListener{
            val progressBar = mainActivity.getLoadingProgress()
            progressBar.visibility = View.VISIBLE
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.LOGIN, user.login)
            holder.itemView.context.startActivity(intent)
        }
    }

    class MyViewHolder (val binding: ItemReviewBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(user: ItemsItem){
            val img = user.avatarUrl
            Log.d("UserAdapter", "Username: ${user.login}")
            binding.tvItemName.text = user.login
            Glide.with(binding.root.context)
                .load(img)
                .into(binding.imgItemPhoto)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>()
        {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}