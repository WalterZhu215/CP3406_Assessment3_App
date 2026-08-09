package com.example.natureexplorer.domain

import com.example.natureexplorer.data.QuizResultEntity


data class LearningStatistics(
    val quizzesCompleted: Int = 0,
    val averageScore: Int = 0,
    val bestScore: Int = 0,
    val correctAnswers: Int = 0
)


object StatisticsCalculator {

    fun calculate(
        results: List<QuizResultEntity>
    ): LearningStatistics {

        if (results.isEmpty()) {

            return LearningStatistics()
        }


        val percentages =

            results.map { result ->

                if (result.totalQuestions <= 0) {

                    0

                } else {

                    (result.score * 100) /
                            result.totalQuestions
                }
            }


        val averageScore =

            percentages
                .average()
                .toInt()


        val bestScore =

            percentages
                .maxOrNull()
                ?: 0


        val correctAnswers =

            results.sumOf {
                it.score
            }


        return LearningStatistics(

            quizzesCompleted =
                results.size,

            averageScore =
                averageScore,

            bestScore =
                bestScore,

            correctAnswers =
                correctAnswers
        )
    }
}

