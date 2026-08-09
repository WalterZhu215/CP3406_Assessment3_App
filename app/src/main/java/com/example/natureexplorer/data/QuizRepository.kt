package com.example.natureexplorer.data

import kotlinx.coroutines.flow.Flow

class QuizRepository(
    private val quizResultDao: QuizResultDao
) {

    // Provides all saved quiz results to the statistics screen.
    val allQuizResults: Flow<List<QuizResultEntity>> =
        quizResultDao.getAllQuizResults()

    // Saves one completed quiz result into Room.
    suspend fun saveQuizResult(
        trailName: String,
        score: Int,
        totalQuestions: Int
    ) {

        val result = QuizResultEntity(
            trailName = trailName,
            score = score,
            totalQuestions = totalQuestions,
            completedAt = System.currentTimeMillis()
        )

        quizResultDao.insertQuizResult(result)
    }

    // Removes all quiz history.
    suspend fun clearQuizResults() {
        quizResultDao.deleteAllQuizResults()
    }
}

