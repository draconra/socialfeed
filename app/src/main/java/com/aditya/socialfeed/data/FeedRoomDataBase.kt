package com.aditya.socialfeed.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [CardFeed::class], version = 1, exportSchema = false)
abstract class FeedRoomDataBase : RoomDatabase() {

    abstract fun cardFeedDao(): CardFeedDao

    companion object{
        @Volatile
        private var INSTANCE: FeedRoomDataBase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): FeedRoomDataBase {
            return INSTANCE?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FeedRoomDataBase::class.java,
                    "feed_database"
                )
                    .addCallback(FeedDatabaseCallback(scope))
                .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class FeedDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback(){

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.cardFeedDao())
                }
            }
        }

        suspend fun populateDatabase(cardFeedDao: CardFeedDao){
            cardFeedDao.deleteAll()
        }
    }

}