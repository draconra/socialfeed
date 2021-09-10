package com.aditya.socialfeed.application

import android.app.Application
import com.aditya.socialfeed.data.FeedRoomDataBase
import com.aditya.socialfeed.repository.CardFeedRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class SocialFeedApplication: Application() {

    private val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { FeedRoomDataBase.getDatabase(this, applicationScope) }
    val repository by lazy { CardFeedRepository(database.cardFeedDao(),database.itemFeedDao()) }
}