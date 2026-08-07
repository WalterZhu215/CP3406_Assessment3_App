package com.example.natureexplorer.ui.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


data class ReviewPlace(val name: String, val rating: String, val review: String)


data class HomeUiState(
    val featuredTitle: String = "Redwood National Park",
    val featuredSubtitle: String = "Discover the ancient giants.",
    val communityReviews: List<ReviewPlace> = emptyList()
)


class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())

    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadHomeData()
    }


    private fun loadHomeData() {

        val mockReviews = listOf(
            ReviewPlace("Pine Valley Trail", "4.8", "Great for beginners!"),
            ReviewPlace("Lake Serenity", "4.9", "Beautiful sunset views."),
            ReviewPlace("Echo Canyon", "4.5", "A bit rocky, wear good shoes.")
        )


        _uiState.value = _uiState.value.copy(
            communityReviews = mockReviews
        )
    }
}

