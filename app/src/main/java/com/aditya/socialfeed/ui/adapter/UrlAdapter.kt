package com.aditya.socialfeed.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aditya.socialfeed.data.CardFeed
import com.aditya.socialfeed.databinding.ItemUrlBinding
import java.util.*

class UrlAdapter(
    private var modelList: ArrayList<CardFeed>,
    private val listener: UrlAdapterListener?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun updateList(modelList: ArrayList<CardFeed>) {
        this.modelList = modelList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UrlViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUrlBinding.inflate(inflater, parent, false)
        return UrlViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is UrlViewHolder) {
            val model = getItem(position)
            holder.bind(model)
        }
    }

    override fun getItemCount(): Int {
        return modelList.size
    }

    private fun getItem(position: Int): CardFeed {
        return modelList[position]
    }

    inner class UrlViewHolder(private val binding: ItemUrlBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(list: CardFeed) {
            binding.tvUrl.text = list.cardUrl
            binding.ivDelete.setOnClickListener {
                modelList.removeAt(absoluteAdapterPosition)
                if(modelList.size < 10){
                    listener?.onEmptyObject()
                }
                notifyDataSetChanged()
            }
        }
    }

    interface UrlAdapterListener {
        fun onEmptyObject()
    }
}