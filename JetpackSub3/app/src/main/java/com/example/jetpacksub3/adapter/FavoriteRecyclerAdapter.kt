package com.example.jetpacksub3.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.jetpacksub3.data.source.DataFilm
import com.example.jetpacksub3.databinding.ItemListBinding

class FavoriteRecyclerAdapter internal constructor(): PagedListAdapter<DataFilm, FavoriteRecyclerAdapter.FilmViewHolder>(
    DIFF_CALLBACK
) {

    companion object{
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataFilm>(){
            override fun areItemsTheSame(oldItem: DataFilm, newItem: DataFilm): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DataFilm, newItem: DataFilm): Boolean {
                return oldItem == newItem
            }
        }
    }

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class FilmViewHolder(private val binding : ItemListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(dataFilm: DataFilm){
            with(itemView){
                Glide.with(itemView.context)
                    .load(dataFilm.imagePath)
                    .centerInside()
                    .into(binding.itemImage)
                binding.itemTitle.text = dataFilm.title
                binding.itemReleaseDate.text = dataFilm.releaseDate
                binding.itemOverview.text = dataFilm.overView
                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(dataFilm) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val itemListBinding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilmViewHolder(itemListBinding)
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val film = getItem(position)
        if (film != null) holder.bind(film)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: DataFilm)
    }
}