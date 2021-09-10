package com.aditya.socialfeed.data

import androidx.room.Embedded
import androidx.room.Relation

data class CardAndItemFeeds(
   @Embedded
    val card: CardFeed,
    @Relation(
        parentColumn = "id",
        entityColumn = "cardRef"
    )
    val items: List<ItemFeed>
)
