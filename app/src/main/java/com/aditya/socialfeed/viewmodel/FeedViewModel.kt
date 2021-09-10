package com.aditya.socialfeed.viewmodel

import androidx.lifecycle.*
import com.aditya.socialfeed.data.CardAndItemFeeds
import com.aditya.socialfeed.data.CardFeed
import com.aditya.socialfeed.data.ItemFeed
import com.aditya.socialfeed.repository.CardFeedRepository
import kotlinx.coroutines.launch

class FeedViewModel(private val repositoryCard: CardFeedRepository) : ViewModel() {

    val allFeeds: LiveData<List<CardFeed>> = repositoryCard.allCards.asLiveData()
    val allCardsAndItemFeeds: LiveData<List<CardAndItemFeeds>> =
        repositoryCard.allCardAndItemFeeds.asLiveData()

    fun insertCard(cardTask: CardFeed) = viewModelScope.launch {
        repositoryCard.insertCardFeed(cardTask)
    }

    fun insertItems(itemTask: List<ItemFeed>) = viewModelScope.launch {
        itemTask.forEach {
            repositoryCard.insertItemsFeeds(it)
        }
    }

    fun insertAll(cardTask: CardFeed, itemTask: List<ItemFeed>) = viewModelScope.launch {
        repositoryCard.insertCardFeed(cardTask)
        itemTask.forEach {
            repositoryCard.insertItemsFeeds(it)
        }
    }
}

class CardFeedViewModelFactory(private val repositoryCard: CardFeedRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FeedViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FeedViewModel(repositoryCard) as T
        }
        throw IllegalArgumentException("Unknow ViewModel class")
    }

}

