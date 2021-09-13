package com.aditya.socialfeed.ui.adapter

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aditya.socialfeed.R
import com.aditya.socialfeed.data.CardFeed
import com.aditya.socialfeed.databinding.ItemFeedBinding
import com.aditya.socialfeed.util.PlayerStateCallback
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.Player

class CardFeedAdapter(
    private val context: Context?,
    private val list: LiveData<List<CardFeed>>
) : ListAdapter<CardFeed, CardFeedViewHolder>(CardTaskComparator()) {

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

    fun bind(context: Context, cardTask: CardFeed) {

        if (cardTask.cardType.contains("image")) {
            binding.tvProcess.text = "Downloading Image"
        }else{
            binding.tvProcess.text = "Downloading Video"
        }

        binding.lottieAnimation.setAnimation(R.raw.download_animation)
        binding.lottieAnimation.repeatCount = 1
        binding.lottieAnimation.playAnimation()
        binding.lottieAnimation.addAnimatorListener(object : AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) = Unit
            override fun onAnimationCancel(animation: Animator?) = Unit
            override fun onAnimationStart(animation: Animator?) = Unit
            override fun onAnimationEnd(animation: Animator?) {
                binding.lottieAnimation.visibility = View.GONE
                binding.tvProcess.visibility = View.GONE
                showView(cardTask, context)
            }
        })
    }

    private fun CardFeedViewHolder.showView(
        cardTask: CardFeed,
        context: Context
    ) {
        if (cardTask.cardType.contains("image")) {
            Glide
                .with(context)
                .load(cardTask.cardUrl)
                .centerCrop()
                .into(binding.ivFeed)

            binding.ivFeed.visibility = View.VISIBLE
            binding.playerView.visibility = View.GONE
        } else {
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

class CardTaskComparator : DiffUtil.ItemCallback<CardFeed>() {

    override fun areItemsTheSame(
        oldItem: CardFeed,
        newItem: CardFeed,
    ): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(
        oldItem: CardFeed,
        newItem: CardFeed,
    ): Boolean {
        return oldItem.id == newItem.id
    }
}



