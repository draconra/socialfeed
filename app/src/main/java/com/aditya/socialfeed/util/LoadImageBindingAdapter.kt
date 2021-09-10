package com.aditya.socialfeed.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.aditya.socialfeed.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

class LoadImageBindingAdapter {
    companion object{
        @JvmStatic
        @BindingAdapter(value = ["thumbnail", "error"], requireAll = false)
        fun loadImage(view: ImageView, profileImage: String?, error: Int) {
            if (!profileImage.isNullOrEmpty()) {
                Glide.with(view.context)
                    .setDefaultRequestOptions(
                        RequestOptions()
                        .placeholder(R.drawable.bg_button_rounded_blue)
                        .error(R.drawable.bg_button_rounded_blue))
                    .load(profileImage)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(view)
            }
        }
    }
}