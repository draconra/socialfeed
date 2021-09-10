package com.aditya.socialfeed.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aditya.socialfeed.data.CardAndItemFeeds
import com.aditya.socialfeed.databinding.ItemFeedBinding
import com.aditya.socialfeed.util.PlayerStateCallback
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.Player

class CardFeedAdapter(
    private val context: Context?,
    private val list: LiveData<List<CardAndItemFeeds>>
) : ListAdapter<CardAndItemFeeds, CardFeedViewHolder>(CardTaskComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardFeedViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFeedBinding.inflate(inflater, parent, false)
        return CardFeedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardFeedViewHolder, position: Int) {
        val current = getItem(position)
        if (context != null) {
            holder.bind(context, current)
        }
    }
}

class CardFeedViewHolder(private val binding: ItemFeedBinding) :
    RecyclerView.ViewHolder(binding.root), PlayerStateCallback {

    fun bind(context: Context, cardTask: CardAndItemFeeds) {

        if (cardTask.card.cardType.contains("image")) {
            Glide
                .with(context)
                .load(cardTask.card.cardUrl)
                .centerCrop()
                .into(binding.ivFeed)

            binding.ivFeed.visibility = View.VISIBLE
            binding.playerView.visibility = View.GONE
            binding.tvProcess.text = "Loading Image"
        } else {
            binding.tvProcess.text = "Loading Video"
            binding.ivFeed.visibility = View.GONE
            binding.playerView.visibility = View.VISIBLE

            binding.apply {
                dataModel = cardTask
                callback = this@CardFeedViewHolder
                index = adapterPosition
                executePendingBindings()
            }
        }
    }

    override fun onVideoDurationRetrieved(duration: Long, player: Player) {}

    override fun onVideoBuffering(player: Player) {}

    override fun onStartedPlaying(player: Player) {}

    override fun onFinishedPlaying(player: Player) {}
}


class CardTaskComparator : DiffUtil.ItemCallback<CardAndItemFeeds>() {

    override fun areItemsTheSame(
        oldItem: CardAndItemFeeds,
        newItem: CardAndItemFeeds,
    ): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(
        oldItem: CardAndItemFeeds,
        newItem: CardAndItemFeeds,
    ): Boolean {
        return oldItem.card.id == newItem.card.id
    }

}



