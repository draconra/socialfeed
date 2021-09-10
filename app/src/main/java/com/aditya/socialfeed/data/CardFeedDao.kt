package com.aditya.socialfeed.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CardFeedDao {

    @Query("SELECT * FROM card_table ORDER BY cardUrl ASC")
    fun getAlphabetizedCardFeed(): Flow<List<CardFeed>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCard(cardTask: CardFeed)

    @Query("DELETE FROM card_table")
    suspend fun deleteAll()

    @Transaction
    @Query("SELECT * FROM card_table")
    fun getCardWithItemFeed(): Flow<List<CardAndItemFeeds>>

}