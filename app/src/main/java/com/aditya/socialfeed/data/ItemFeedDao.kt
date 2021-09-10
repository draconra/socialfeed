package com.aditya.socialfeed.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemFeedDao {
    @Query("SELECT * FROM item_list")
    fun getItems(): Flow<List<ItemFeed>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertItem(itemTask: ItemFeed)

    @Query("DELETE FROM item_list")
    suspend fun deleteAllItem()
}