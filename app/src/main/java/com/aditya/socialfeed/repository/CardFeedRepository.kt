package com.aditya.socialfeed.repository

import androidx.annotation.WorkerThread
import com.aditya.socialfeed.data.*
import kotlinx.coroutines.flow.Flow

class CardFeedRepository(
    private val cardFeedDao: CardFeedDao) {

    val allCards: Flow<List<CardFeed>> = cardFeedDao.getCardFeed()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertCardFeed(cardFeed: CardFeed){
        cardFeedDao.insertCard(cardFeed)
    }
}