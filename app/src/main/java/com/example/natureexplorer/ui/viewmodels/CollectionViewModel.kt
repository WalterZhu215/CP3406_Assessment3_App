package com.example.natureexplorer.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.natureexplorer.data.SavedTrailEntity
import com.example.natureexplorer.data.TrailRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


data class CollectionUiState(
    val savedTrails: List<SavedTrailEntity> = emptyList()
)


class CollectionViewModel(private val repository: TrailRepository) : ViewModel() {


    val uiState: StateFlow<CollectionUiState> = repository.allSavedTrails
        .map { CollectionUiState(savedTrails = it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = CollectionUiState()
        )

    fun toggleFavorite(name: String, imageUrl: String) {
        viewModelScope.launch {

            val exists = uiState.value.savedTrails.any { it.name == name }
            if (exists) {
                repository.removeTrailFromFavorites(name)
            } else {
                repository.addTrailToFavorites(name, imageUrl)
            }
        }
    }

    fun removeTrailByName(name: String) {
        viewModelScope.launch {
            repository.removeTrailFromFavorites(name)
        }
    }
}


class CollectionViewModelFactory(private val repository: TrailRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CollectionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CollectionViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
