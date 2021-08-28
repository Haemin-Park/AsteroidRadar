package com.example.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.asteroidradar.R
import com.example.asteroidradar.data.Asteroid
import com.example.asteroidradar.databinding.ItemAsteroidBinding

class AsteroidAdapter(private val onItemClickListener: OnItemClickListener) : ListAdapter<Asteroid, AsteroidAdapter.ViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClickListener)
    }

    class DiffCallback : DiffUtil.ItemCallback<Asteroid>() {
        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean =
            oldItem == newItem
    }

    class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_asteroid, parent, false)
    ) {
        private val binding: ItemAsteroidBinding = DataBindingUtil.bind(itemView)!!
        fun bind(item: Asteroid, listener: OnItemClickListener) {
            binding.asteroid = item
            binding.listener = listener
        }
    }

    class OnItemClickListener(val itemClickListener: (asteroid: Asteroid) -> Unit) {
        fun onItemClick(asteroid: Asteroid) = itemClickListener(asteroid)
    }
}