package com.example.natureexplorer.ui.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


data class SavedTrail(val id: String, val name: String, val addedDate: String, val imageUrl: String)

data class CollectionUiState(
    val savedTrails: List<SavedTrail> = emptyList()
)

class CollectionViewModel : ViewModel() {


    private val initialTrails = listOf(
        SavedTrail("1", "Botanical Garden Trail", "Added 2 days ago", "https://images.unsplash.com/photo-1551632811-561732d1e306?w=400&q=80")
    )

    private val _uiState = MutableStateFlow(CollectionUiState(savedTrails = initialTrails))
    val uiState: StateFlow<CollectionUiState> = _uiState.asStateFlow()

    fun removeTrail(trailId: String) {
        val currentList = _uiState.value.savedTrails
        _uiState.value = _uiState.value.copy(
            savedTrails = currentList.filter { it.id != trailId }
        )
    }


    fun toggleFavorite(name: String, imageUrl: String) {
        val currentList = _uiState.value.savedTrails
        val exists = currentList.any { it.name == name }

        if (exists) {

            _uiState.value = _uiState.value.copy(
                savedTrails = currentList.filter { it.name != name }
            )
        } else {

            val newTrail = SavedTrail(
                id = System.currentTimeMillis().toString(),
                name = name,
                addedDate = "Added just now",
                imageUrl = imageUrl
            )
            _uiState.value = _uiState.value.copy(
                savedTrails = listOf(newTrail) + currentList
            )
        }
    }
}
