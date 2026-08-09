package com.example.natureexplorer.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.natureexplorer.data.QuizRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


data class QuizUiState(
    val currentQuestionIndex: Int = 0,
    val score: Int = 0,
    val showResult: Boolean = false,
    val resultSaved: Boolean = false
)


class QuizViewModel(
    private val repository: QuizRepository
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(QuizUiState())

    val uiState: StateFlow<QuizUiState> =
        _uiState.asStateFlow()


    fun submitAnswer(
        selectedAnswerIndex: Int,
        correctAnswerIndex: Int,
        totalQuestions: Int,
        trailName: String
    ) {

        if (_uiState.value.showResult) {
            return
        }

        val isCorrect =
            selectedAnswerIndex == correctAnswerIndex

        val newScore =
            _uiState.value.score +
                    if (isCorrect) 1 else 0

        val isLastQuestion =
            _uiState.value.currentQuestionIndex >=
                    totalQuestions - 1


        if (isLastQuestion) {

            _uiState.value =
                _uiState.value.copy(
                    score = newScore,
                    showResult = true
                )

            saveQuizResult(
                trailName = trailName,
                score = newScore,
                totalQuestions = totalQuestions
            )

        } else {

            _uiState.value =
                _uiState.value.copy(
                    currentQuestionIndex =
                        _uiState.value.currentQuestionIndex + 1,
                    score = newScore
                )
        }
    }


    fun restartQuiz() {

        _uiState.value =
            QuizUiState()
    }


    private fun saveQuizResult(
        trailName: String,
        score: Int,
        totalQuestions: Int
    ) {

        viewModelScope.launch {

            repository.saveQuizResult(
                trailName = trailName,
                score = score,
                totalQuestions = totalQuestions
            )

            _uiState.value =
                _uiState.value.copy(
                    resultSaved = true
                )
        }
    }
}


class QuizViewModelFactory(
    private val repository: QuizRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        if (
            modelClass.isAssignableFrom(
                QuizViewModel::class.java
            )
        ) {

            @Suppress("UNCHECKED_CAST")
            return QuizViewModel(repository) as T
        }

        throw IllegalArgumentException(
            "Unknown ViewModel class"
        )
    }
}

