package com.dicoding.myfirstsubmissionfundamental.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.myfirstsubmissionfundamental.database.FavoriteUser
import com.dicoding.myfirstsubmissionfundamental.databinding.ItemReviewBinding
import com.dicoding.myfirstsubmissionfundamental.ui.activity.DetailActivity
import com.dicoding.myfirstsubmissionfundamental.ui.helper.NoteDiffCallback

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavViewHolder>() {
    private val listNotes = ArrayList<FavoriteUser>()

    fun setListNotes(listNotes: List<FavoriteUser>) {
        val diffCallback = NoteDiffCallback(this.listNotes, listNotes)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listNotes.clear()
        this.listNotes.addAll(listNotes)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        holder.bind(listNotes[position])
        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.LOGIN, listNotes[position].username)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return listNotes.size
    }

    inner class FavViewHolder(private val binding: ItemReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(note: FavoriteUser) {
            with(binding) {
                binding.tvItemName.text = note.username
                Glide.with(binding.root.context)
                    .load(note.avatarUrl)
                    .into(binding.imgItemPhoto)
            }
        }
    }
}