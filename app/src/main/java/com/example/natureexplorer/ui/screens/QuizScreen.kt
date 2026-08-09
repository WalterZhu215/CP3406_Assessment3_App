package com.example.natureexplorer.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.natureexplorer.LocalIsEnglish
import com.example.natureexplorer.domain.QuizQuestionBank
import com.example.natureexplorer.ui.viewmodels.QuizViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    trailName: String,
    difficulty: String,
    viewModel: QuizViewModel,
    onBackClick: () -> Unit
) {

    val isEnglish =
        LocalIsEnglish.current

    val uiState by
    viewModel.uiState.collectAsState()


    val questions = remember(
        trailName,
        difficulty,
        isEnglish
    ) {

        QuizQuestionBank.getQuestions(
            trailName = trailName,
            difficulty = difficulty,
            isEnglish = isEnglish
        )
    }


    val currentQuestionIndex =
        uiState.currentQuestionIndex.coerceIn(
            0,
            questions.lastIndex
        )

    val currentQuestion =
        questions[currentQuestionIndex]


    Scaffold(

        topBar = {

            TopAppBar(

                title = {

                    Text(
                        text =
                            if (isEnglish)
                                "Nature Learning Quiz"
                            else
                                "自然学习测验"
                    )
                },

                navigationIcon = {

                    IconButton(
                        onClick = onBackClick
                    ) {

                        Icon(
                            imageVector =
                                Icons.Filled.ArrowBack,
                            contentDescription =
                                "Back"
                        )
                    }
                }
            )
        }

    ) { innerPadding ->


        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),

            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {


            if (uiState.showResult) {

                QuizResultContent(
                    score = uiState.score,
                    totalQuestions = questions.size,
                    difficulty = difficulty,
                    resultSaved =
                        uiState.resultSaved,
                    isEnglish = isEnglish,
                    onRestartClick = {
                        viewModel.restartQuiz()
                    },
                    onBackClick =
                        onBackClick
                )

            } else {

                Text(
                    text = trailName,
                    style =
                        MaterialTheme
                            .typography
                            .titleMedium,
                    fontWeight =
                        FontWeight.Bold
                )


                Spacer(
                    modifier =
                        Modifier.height(8.dp)
                )


                Text(
                    text =
                        if (isEnglish)
                            "Difficulty: $difficulty"
                        else
                            "难度：$difficulty",

                    color =
                        MaterialTheme
                            .colorScheme
                            .primary
                )


                Spacer(
                    modifier =
                        Modifier.height(20.dp)
                )


                LinearProgressIndicator(

                    progress = {

                        (currentQuestionIndex + 1)
                            .toFloat() /
                                questions.size
                    },

                    modifier =
                        Modifier.fillMaxWidth()
                )


                Spacer(
                    modifier =
                        Modifier.height(10.dp)
                )


                Text(
                    text =
                        if (isEnglish) {

                            "Question ${currentQuestionIndex + 1} of ${questions.size}"

                        } else {

                            "第 ${currentQuestionIndex + 1} 题，共 ${questions.size} 题"
                        },

                    style =
                        MaterialTheme
                            .typography
                            .bodyMedium
                )


                Spacer(
                    modifier =
                        Modifier.height(28.dp)
                )


                Text(
                    text =
                        currentQuestion.question,

                    style =
                        MaterialTheme
                            .typography
                            .headlineSmall,

                    fontWeight =
                        FontWeight.SemiBold
                )


                Spacer(
                    modifier =
                        Modifier.height(30.dp)
                )


                currentQuestion.answers
                    .forEachIndexed {
                            index,
                            answer ->


                        OutlinedButton(

                            onClick = {

                                viewModel.submitAnswer(

                                    selectedAnswerIndex =
                                        index,

                                    correctAnswerIndex =
                                        currentQuestion
                                            .correctAnswerIndex,

                                    totalQuestions =
                                        questions.size,

                                    trailName =
                                        trailName
                                )
                            },

                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        vertical = 7.dp
                                    )
                                    .height(56.dp)
                        ) {

                            Text(
                                text = answer,
                                style =
                                    MaterialTheme
                                        .typography
                                        .bodyLarge
                            )
                        }
                    }
            }
        }
    }
}


@Composable
private fun QuizResultContent(
    score: Int,
    totalQuestions: Int,
    difficulty: String,
    resultSaved: Boolean,
    isEnglish: Boolean,
    onRestartClick: () -> Unit,
    onBackClick: () -> Unit
) {

    val percentage =

        if (totalQuestions == 0) {

            0

        } else {

            (score * 100) /
                    totalQuestions
        }


    Text(

        text =
            if (isEnglish)
                "Quiz Completed!"
            else
                "测验完成！",

        style =
            MaterialTheme
                .typography
                .headlineMedium,

        fontWeight =
            FontWeight.Bold,

        color =
            MaterialTheme
                .colorScheme
                .primary
    )


    Spacer(
        modifier =
            Modifier.height(20.dp)
    )


    Text(

        text =
            if (isEnglish)
                "Your Score"
            else
                "你的得分",

        style =
            MaterialTheme
                .typography
                .titleMedium
    )


    Spacer(
        modifier =
            Modifier.height(8.dp)
    )


    Text(

        text =
            "$score / $totalQuestions",

        style =
            MaterialTheme
                .typography
                .displaySmall,

        fontWeight =
            FontWeight.Bold,

        color =
            MaterialTheme
                .colorScheme
                .primary
    )


    Spacer(
        modifier =
            Modifier.height(8.dp)
    )


    Text(

        text = "$percentage%",

        style =
            MaterialTheme
                .typography
                .headlineSmall
    )


    Spacer(
        modifier =
            Modifier.height(14.dp)
    )


    Text(

        text =
            if (isEnglish)
                "Difficulty: $difficulty"
            else
                "难度：$difficulty"
    )


    if (resultSaved) {

        Spacer(
            modifier =
                Modifier.height(12.dp)
        )


        Text(

            text =
                if (isEnglish)
                    "Your result has been saved."
                else
                    "你的测验成绩已经保存。",

            color =
                MaterialTheme
                    .colorScheme
                    .primary
        )
    }


    Spacer(
        modifier =
            Modifier.height(32.dp)
    )


    OutlinedButton(

        onClick =
            onRestartClick,

        modifier =
            Modifier
                .fillMaxWidth()
                .height(52.dp)
    ) {

        Icon(
            imageVector =
                Icons.Filled.Replay,
            contentDescription = null
        )


        Text(
            text =
                if (isEnglish)
                    "  Try Again"
                else
                    "  再试一次"
        )
    }


    Spacer(
        modifier =
            Modifier.height(12.dp)
    )


    Button(

        onClick =
            onBackClick,

        modifier =
            Modifier
                .fillMaxWidth()
                .height(52.dp)
    ) {

        Text(
            text =
                if (isEnglish)
                    "Return to Details"
                else
                    "返回详情页"
        )
    }
}

