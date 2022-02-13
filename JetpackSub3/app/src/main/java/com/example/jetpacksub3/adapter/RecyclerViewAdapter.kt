package com.example.jetpacksub3.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.jetpacksub3.data.source.DataFilm
import com.example.jetpacksub3.databinding.ItemListBinding

class RecyclerViewAdapter: RecyclerView.Adapter<RecyclerViewAdapter.ListViewHolder>()  {

    private val lists = ArrayList<DataFilm>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ListViewHolder(private val binding : ItemListBinding) : RecyclerView.ViewHolder(binding.root) {
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

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val itemListBinding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(itemListBinding)
    }


    fun setData(list: List<DataFilm>){
        lists.clear()
        lists.addAll(list)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) = holder.bind(lists[position])

    override fun getItemCount(): Int = lists.size

    interface OnItemClickCallback {
        fun onItemClicked(data: DataFilm)
    }
}