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

// 3. ViewModel
class ExploreViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ExploreUiState())
    val uiState: StateFlow<ExploreUiState> = _uiState.asStateFlow()

    init {
        loadNearbyTrails()
    }


    private fun loadNearbyTrails() {
        val mockTrails = listOf(
            Trail("Botanical Garden Trail", "2.1 km"),
            Trail("Sunrise Peak", "5.4 km"),
            Trail("Hidden Waterfall", "7.2 km"),
            Trail("Pine Forest Path", "8.9 km")
        )

        _uiState.value = _uiState.value.copy(
            nearbyTrails = mockTrails
        )
    }

    // 处理用户在搜索框输入文字的事件
    fun updateSearchQuery(newQuery: String) {
        _uiState.value = _uiState.value.copy(
            searchQuery = newQuery
        )
    }
}

