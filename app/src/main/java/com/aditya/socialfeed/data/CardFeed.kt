package com.aditya.socialfeed.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "card_table")
    data class CardFeed(
    @PrimaryKey(autoGenerate = true)
        val id: Long = 0,
        val cardUrl: String,
        val cardType: String
    ){
        var visibility: Boolean = false
}

