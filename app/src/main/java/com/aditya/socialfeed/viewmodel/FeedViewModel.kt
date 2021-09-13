package com.aditya.socialfeed.viewmodel

import androidx.lifecycle.*
import com.aditya.socialfeed.data.CardFeed
import com.aditya.socialfeed.repository.CardFeedRepository
import kotlinx.coroutines.launch

class FeedViewModel(private val repositoryCard: CardFeedRepository) : ViewModel() {

    val allFeeds: LiveData<List<CardFeed>> = repositoryCard.allCards.asLiveData()

    fun insertCard(cardTask: CardFeed) = viewModelScope.launch {
        repositoryCard.insertCardFeed(cardTask)
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

