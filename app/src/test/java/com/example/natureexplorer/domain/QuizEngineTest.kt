package com.example.natureexplorer.domain

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test


class QuizEngineTest {

    private val question = QuizQuestion(
        question = "What does biodiversity mean?",
        answers = listOf(
            "The variety of living things",
            "Only the number of trees",
            "The temperature of an ecosystem"
        ),
        correctAnswerIndex = 0
    )


    @Test
    fun correctAnswer_returnsTrue() {

        val result =
            QuizEngine.isAnswerCorrect(
                question = question,
                selectedAnswerIndex = 0
            )

        assertTrue(result)
    }


    @Test
    fun wrongAnswer_returnsFalse() {

        val result =
            QuizEngine.isAnswerCorrect(
                question = question,
                selectedAnswerIndex = 1
            )

        assertFalse(result)
    }


    @Test
    fun calculateScore_returnsCorrectTotal() {

        val questions = listOf(

            QuizQuestion(
                question = "Question 1",
                answers = listOf(
                    "A",
                    "B",
                    "C"
                ),
                correctAnswerIndex = 0
            ),

            QuizQuestion(
                question = "Question 2",
                answers = listOf(
                    "A",
                    "B",
                    "C"
                ),
                correctAnswerIndex = 1
            ),

            QuizQuestion(
                question = "Question 3",
                answers = listOf(
                    "A",
                    "B",
                    "C"
                ),
                correctAnswerIndex = 2
            )
        )


        val selectedAnswers =
            listOf(
                0,
                1,
                0
            )


        val score =
            QuizEngine.calculateScore(
                questions = questions,
                selectedAnswers = selectedAnswers
            )


        assertEquals(
            2,
            score
        )
    }


    @Test
    fun calculatePercentage_returnsCorrectPercentage() {

        val percentage =
            QuizEngine.calculatePercentage(
                score = 4,
                totalQuestions = 5
            )

        assertEquals(
            80,
            percentage
        )
    }


    @Test
    fun calculatePercentage_withZeroQuestions_returnsZero() {

        val percentage =
            QuizEngine.calculatePercentage(
                score = 0,
                totalQuestions = 0
            )

        assertEquals(
            0,
            percentage
        )
    }


    @Test
    fun easyDifficulty_returnsThreeQuestions() {

        val questions =
            QuizQuestionBank.getQuestions(
                trailName = "Hidden Waterfall",
                difficulty = "Easy",
                isEnglish = true
            )

        assertEquals(
            3,
            questions.size
        )
    }


    @Test
    fun mediumDifficulty_returnsFiveQuestions() {

        val questions =
            QuizQuestionBank.getQuestions(
                trailName = "Hidden Waterfall",
                difficulty = "Medium",
                isEnglish = true
            )

        assertEquals(
            5,
            questions.size
        )
    }


    @Test
    fun hardDifficulty_returnsSevenQuestions() {

        val questions =
            QuizQuestionBank.getQuestions(
                trailName = "Hidden Waterfall",
                difficulty = "Hard",
                isEnglish = true
            )

        assertEquals(
            7,
            questions.size
        )
    }
}

