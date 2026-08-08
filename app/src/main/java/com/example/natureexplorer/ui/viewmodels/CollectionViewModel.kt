package com.example.natureexplorer.ui.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class SavedTrail(val id: String, val name: String, val addedDate: String)


data class CollectionUiState(
    val savedTrails: List<SavedTrail> = emptyList()
)

// 3. ViewModel
class CollectionViewModel : ViewModel() {


    private val initialTrails = listOf(
        SavedTrail("1", "Botanical Garden Trail", "Added 2 days ago"),
        SavedTrail("2", "Hidden Waterfall", "Added 1 week ago"),
        SavedTrail("3", "Pine Forest Path", "Added 2 weeks ago")
    )

    private val _uiState = MutableStateFlow(CollectionUiState(savedTrails = initialTrails))
    val uiState: StateFlow<CollectionUiState> = _uiState.asStateFlow()


    fun removeTrail(trailId: String) {
        val currentList = _uiState.value.savedTrails

        _uiState.value = _uiState.value.copy(
            savedTrails = currentList.filter { it.id != trailId }
        )
    }
}
