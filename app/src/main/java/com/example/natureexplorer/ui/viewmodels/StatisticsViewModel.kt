package com.example.natureexplorer.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.natureexplorer.data.QuizRepository
import com.example.natureexplorer.data.QuizResultEntity
import com.example.natureexplorer.data.TrailRepository
import com.example.natureexplorer.domain.LearningStatistics
import com.example.natureexplorer.domain.StatisticsCalculator
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn


data class StatisticsUiState(

    val learningStatistics:
    LearningStatistics =
        LearningStatistics(),

    val savedTrails: Int = 0,

    val recentQuizResults:
    List<QuizResultEntity> =
        emptyList()
)


class StatisticsViewModel(

    quizRepository:
    QuizRepository,

    trailRepository:
    TrailRepository

) : ViewModel() {


    val uiState:
            StateFlow<StatisticsUiState> =

        combine(

            quizRepository
                .allQuizResults,

            trailRepository
                .allSavedTrails

        ) { quizResults, savedTrails ->


            StatisticsUiState(

                learningStatistics =
                    StatisticsCalculator
                        .calculate(
                            quizResults
                        ),

                savedTrails =
                    savedTrails.size,

                recentQuizResults =
                    quizResults.take(5)
            )
        }
            .stateIn(

                scope =
                    viewModelScope,

                started =
                    SharingStarted
                        .WhileSubscribed(
                            5000
                        ),

                initialValue =
                    StatisticsUiState()
            )
}


class StatisticsViewModelFactory(

    private val quizRepository:
    QuizRepository,

    private val trailRepository:
    TrailRepository

) : ViewModelProvider.Factory {


    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {


        if (
            modelClass.isAssignableFrom(
                StatisticsViewModel::class.java
            )
        ) {

            @Suppress("UNCHECKED_CAST")

            return StatisticsViewModel(
                quizRepository =
                    quizRepository,
                trailRepository =
                    trailRepository
            ) as T
        }


        throw IllegalArgumentException(
            "Unknown ViewModel class"
        )
    }
}

