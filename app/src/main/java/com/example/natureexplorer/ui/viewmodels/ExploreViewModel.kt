package com.example.natureexplorer.ui.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class Trail(val name: String, val distance: String)

data class ExploreUiState(
    val searchQuery: String = "",
    val nearbyTrails: List<Trail> = emptyList()
)

class ExploreViewModel : ViewModel() {


    private val allTrails = listOf(
        Trail("Botanical Garden Trail", "2.1 km"),
        Trail("Sunrise Peak", "5.4 km"),
        Trail("Hidden Waterfall", "7.2 km"),
        Trail("Pine Forest Path", "8.9 km")
    )

    private val _uiState = MutableStateFlow(ExploreUiState(nearbyTrails = allTrails))
    val uiState: StateFlow<ExploreUiState> = _uiState.asStateFlow()


    fun updateSearchQuery(newQuery: String) {

        val filteredTrails = if (newQuery.isBlank()) {
            allTrails
        } else {
            allTrails.filter { it.name.contains(newQuery, ignoreCase = true) }
        }


        _uiState.value = _uiState.value.copy(
            searchQuery = newQuery,
            nearbyTrails = filteredTrails
        )
    }
}

