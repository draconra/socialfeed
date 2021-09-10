package com.aditya.socialfeed.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item_list")
data class ItemFeed(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val cardRef: Long,
    val itemTitle: String,
    var checked: Boolean
)
