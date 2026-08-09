package com.example.natureexplorer.ui.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.natureexplorer.ui.theme.NatureExplorerTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test


class SettingsScreenTest {

    @get:Rule
    val composeTestRule =
        createComposeRule()


    @Test
    fun settingsScreen_displaysMainContent() {

        composeTestRule.setContent {

            NatureExplorerTheme {

                SettingsScreen(
                    isEnglish = true,
                    quizDifficulty = "Medium",
                    onLanguageChange = {},
                    onDifficultyChange = {},
                    onBackClick = {}
                )
            }
        }


        composeTestRule
            .onNodeWithText(
                "Learning Settings"
            )
            .assertIsDisplayed()


        composeTestRule
            .onNodeWithText(
                "Quiz Difficulty"
            )
            .assertIsDisplayed()


        composeTestRule
            .onNodeWithText(
                "Easy"
            )
            .assertIsDisplayed()


        composeTestRule
            .onNodeWithText(
                "Medium"
            )
            .assertIsDisplayed()


        composeTestRule
            .onNodeWithText(
                "Hard"
            )
            .assertIsDisplayed()


        composeTestRule
            .onNodeWithText(
                "Privacy & Responsible Learning"
            )
            .assertIsDisplayed()
    }


    @Test
    fun clickingHardDifficulty_callsDifficultyCallback() {

        var selectedDifficulty =
            "Medium"


        composeTestRule.setContent {

            NatureExplorerTheme {

                SettingsScreen(
                    isEnglish = true,
                    quizDifficulty = selectedDifficulty,
                    onLanguageChange = {},
                    onDifficultyChange = {

                        selectedDifficulty = it
                    },
                    onBackClick = {}
                )
            }
        }


        composeTestRule
            .onNodeWithText(
                "Hard"
            )
            .performClick()


        composeTestRule.runOnIdle {

            assertEquals(
                "Hard",
                selectedDifficulty
            )
        }
    }


    @Test
    fun chineseMode_displaysChineseSettingsTitle() {

        composeTestRule.setContent {

            NatureExplorerTheme {

                SettingsScreen(
                    isEnglish = false,
                    quizDifficulty = "Easy",
                    onLanguageChange = {},
                    onDifficultyChange = {},
                    onBackClick = {}
                )
            }
        }


        composeTestRule
            .onNodeWithText(
                "学习设置"
            )
            .assertIsDisplayed()


        composeTestRule
            .onNodeWithText(
                "测验难度"
            )
            .assertIsDisplayed()
    }
}

