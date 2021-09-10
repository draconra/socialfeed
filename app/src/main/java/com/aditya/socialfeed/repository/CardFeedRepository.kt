package com.aditya.socialfeed.repository

import androidx.annotation.WorkerThread
import com.aditya.socialfeed.data.*
import kotlinx.coroutines.flow.Flow

class CardFeedRepository(
    private val cardFeedDao: CardFeedDao,
    private val itemFeedsDao: ItemFeedDao) {

    val allCards: Flow<List<CardFeed>> = cardFeedDao.getAlphabetizedCardFeed()
    val allItemFeeds: Flow<List<ItemFeed>> = itemFeedsDao.getItems()
    val allCardAndItemFeeds: Flow<List<CardAndItemFeeds>> = cardFeedDao.getCardWithItemFeed()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertCardFeed(cardFeed: CardFeed){
        cardFeedDao.insertCard(cardFeed)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertItemsFeeds(itemFeed: ItemFeed){
        itemFeedsDao.insertItem(itemFeed)
    }
}