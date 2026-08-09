package com.example.natureexplorer.domain

import com.example.natureexplorer.data.QuizResultEntity
import org.junit.Assert.assertEquals
import org.junit.Test


class StatisticsCalculatorTest {


    @Test
    fun emptyResults_returnsZeroStatistics() {

        val result =
            StatisticsCalculator.calculate(
                emptyList()
            )


        assertEquals(
            0,
            result.quizzesCompleted
        )

        assertEquals(
            0,
            result.averageScore
        )

        assertEquals(
            0,
            result.bestScore
        )

        assertEquals(
            0,
            result.correctAnswers
        )
    }


    @Test
    fun calculate_returnsCorrectQuizCount() {

        val results =
            createSampleResults()


        val statistics =
            StatisticsCalculator.calculate(
                results
            )


        assertEquals(
            3,
            statistics.quizzesCompleted
        )
    }


    @Test
    fun calculate_returnsCorrectAverageScore() {

        /*
         * Quiz percentages:
         *
         * 4 / 5 = 80%
         * 3 / 5 = 60%
         * 5 / 5 = 100%
         *
         * Average = 80%
         */

        val results =
            createSampleResults()


        val statistics =
            StatisticsCalculator.calculate(
                results
            )


        assertEquals(
            80,
            statistics.averageScore
        )
    }


    @Test
    fun calculate_returnsBestScore() {

        val results =
            createSampleResults()


        val statistics =
            StatisticsCalculator.calculate(
                results
            )


        assertEquals(
            100,
            statistics.bestScore
        )
    }


    @Test
    fun calculate_returnsTotalCorrectAnswers() {

        /*
         * 4 + 3 + 5 = 12
         */

        val results =
            createSampleResults()


        val statistics =
            StatisticsCalculator.calculate(
                results
            )


        assertEquals(
            12,
            statistics.correctAnswers
        )
    }


    @Test
    fun invalidZeroQuestionResult_doesNotCrash() {

        val results =
            listOf(

                QuizResultEntity(
                    id = 1,
                    trailName = "Test Trail",
                    score = 0,
                    totalQuestions = 0,
                    completedAt = 1000L
                )
            )


        val statistics =
            StatisticsCalculator.calculate(
                results
            )


        assertEquals(
            0,
            statistics.averageScore
        )

        assertEquals(
            0,
            statistics.bestScore
        )
    }


    private fun createSampleResults():
            List<QuizResultEntity> {


        return listOf(

            QuizResultEntity(
                id = 1,
                trailName =
                    "Hidden Waterfall",
                score = 4,
                totalQuestions = 5,
                completedAt = 1000L
            ),

            QuizResultEntity(
                id = 2,
                trailName =
                    "Pine Valley Trail",
                score = 3,
                totalQuestions = 5,
                completedAt = 2000L
            ),

            QuizResultEntity(
                id = 3,
                trailName =
                    "Lake Serenity",
                score = 5,
                totalQuestions = 5,
                completedAt = 3000L
            )
        )
    }
}

