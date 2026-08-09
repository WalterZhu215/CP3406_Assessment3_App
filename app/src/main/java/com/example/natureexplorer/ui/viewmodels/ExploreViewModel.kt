package com.example.natureexplorer.ui.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class Trail(
    val name: String,
    val distance: String,
    val imageUrl: String,
    val category: String
)

data class ExploreUiState(
    val searchQuery: String = "",
    val selectedCategory: String = "All",
    val categories: List<String> = listOf("All", "Hiking", "Camping", "Cycling"),
    val nearbyTrails: List<Trail> = emptyList()
)

class ExploreViewModel : ViewModel() {

    private var allTrails = mutableListOf(
        Trail("Botanical Garden Trail", "2.1 km", "https://images.unsplash.com/photo-1551632811-561732d1e306?w=400&q=80", "Hiking"),
        Trail("Sunrise Peak", "5.4 km", "https://images.unsplash.com/photo-1600298882283-40b4dcb8b211?w=400&q=80", "Hiking"),
        Trail("Hidden Waterfall", "7.2 km", "https://images.unsplash.com/photo-1433086966358-54859d0ed716?w=400&q=80", "Camping"),
        Trail("Pine Forest Path", "8.9 km", "https://images.unsplash.com/photo-1511497584788-876760111969?w=400&q=80", "Cycling"),
        Trail("Crystal Lake", "12.5 km", "https://images.unsplash.com/photo-1501785888041-af3ef285b470?w=400&q=80", "Camping")
    )

    private val _uiState = MutableStateFlow(ExploreUiState(nearbyTrails = allTrails))
    val uiState: StateFlow<ExploreUiState> = _uiState.asStateFlow()

    fun updateSearchQuery(newQuery: String) {
        _uiState.value = _uiState.value.copy(searchQuery = newQuery)
        applyFilters()
    }

    fun updateCategory(newCategory: String) {
        _uiState.value = _uiState.value.copy(selectedCategory = newCategory)
        applyFilters()
    }

    fun addNewTrail(name: String, distance: String, category: String) {
        val newTrail = Trail(
            name = name,
            distance = distance,
            imageUrl = "https://images.unsplash.com/photo-1441974231531-c6227db76b6e?w=400&q=80",
            category = category
        )
        allTrails.add(0, newTrail)
        applyFilters()
    }


    fun removeTrail(trailName: String) {
        allTrails.removeAll { it.name == trailName }
        applyFilters()
    }

    private fun applyFilters() {
        val currentQuery = _uiState.value.searchQuery
        val currentCategory = _uiState.value.selectedCategory

        val filteredTrails = allTrails.filter { trail ->
            val matchesSearch = if (currentQuery.isBlank()) true else trail.name.contains(currentQuery, ignoreCase = true)
            val matchesCategory = if (currentCategory == "All") true else trail.category == currentCategory

            matchesSearch && matchesCategory
        }
        _uiState.value = _uiState.value.copy(nearbyTrails = filteredTrails)
    }
}