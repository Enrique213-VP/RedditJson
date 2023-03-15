package com.svape.redditjson.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.svape.redditjson.R
import com.svape.redditjson.databinding.ItemRedditBinding
import com.svape.redditjson.response.RedditJson
import com.svape.redditjson.utils.BASE_URL
import javax.inject.Inject

class RedditAdapter @Inject() constructor() : RecyclerView.Adapter<RedditAdapter.ViewHolder>() {

    private lateinit var binding: ItemRedditBinding
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemRedditBinding.inflate(inflater, parent, false)
        context = parent.context
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun getItemViewType(position: Int): Int = position
    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: RedditJson.Data.Children.DataX) {
            binding.apply {
                tvMovieName.text = item.name
                tvMovieDateRelease.text = item.submitText
                tvRate.text = item.description
                val moviePosterURL = BASE_URL
                imgMovie.load(moviePosterURL) {
                    crossfade(true)
                    placeholder(R.drawable.poster_placeholder)
                    scale(Scale.FILL)
                }
                tvLang.text = item.displayName

                root.setOnClickListener {
                    onItemClickListener?.let {
                        it(item)
                    }
                }
            }


        }
    }

    private var onItemClickListener: ((RedditJson.Data.Children.DataX) -> Unit)? = null

    fun setOnItemClickListener(listener: (RedditJson.Data.Children.DataX) -> Unit) {
        onItemClickListener = listener
    }

    private val differCallback = object : DiffUtil.ItemCallback<RedditJson.Data.Children.DataX>() {
        override fun areItemsTheSame(
            oldItem: RedditJson.Data.Children.DataX,
            newItem: RedditJson.Data.Children.DataX
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: RedditJson.Data.Children.DataX,
            newItem: RedditJson.Data.Children.DataX
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
}