package com.example.natureexplorer.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.natureexplorer.LocalIsEnglish
import com.example.natureexplorer.data.QuizResultEntity
import com.example.natureexplorer.ui.viewmodels.StatisticsViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun StatisticsScreen(
    viewModel: StatisticsViewModel
) {

    val isEnglish =
        LocalIsEnglish.current


    val uiState by
    viewModel
        .uiState
        .collectAsState()


    val statistics =
        uiState.learningStatistics


    LazyColumn(

        modifier =
            Modifier
                .fillMaxSize()
                .padding(
                    horizontal = 20.dp
                ),

        verticalArrangement =
            Arrangement.spacedBy(
                16.dp
            )

    ) {


        item {

            Spacer(
                modifier =
                    Modifier.height(20.dp)
            )


            Text(

                text =
                    if (isEnglish)
                        "Learning Statistics"
                    else
                        "学习统计",

                style =
                    MaterialTheme
                        .typography
                        .headlineMedium,

                fontWeight =
                    FontWeight.Bold
            )


            Spacer(
                modifier =
                    Modifier.height(6.dp)
            )


            Text(

                text =
                    if (isEnglish)
                        "Track your learning progress and quiz performance."
                    else
                        "查看你的学习进度和测验表现。",

                style =
                    MaterialTheme
                        .typography
                        .bodyMedium,

                color =
                    MaterialTheme
                        .colorScheme
                        .onSurfaceVariant
            )
        }


        /*
         * First row of learning statistics.
         */
        item {

            Row(

                modifier =
                    Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.spacedBy(
                        12.dp
                    )
            ) {


                StatisticCard(

                    title =
                        if (isEnglish)
                            "Quizzes"
                        else
                            "测验次数",

                    value =
                        statistics
                            .quizzesCompleted
                            .toString(),

                    modifier =
                        Modifier.weight(1f)
                )


                StatisticCard(

                    title =
                        if (isEnglish)
                            "Average"
                        else
                            "平均分",

                    value =
                        "${statistics.averageScore}%",

                    modifier =
                        Modifier.weight(1f)
                )
            }
        }


        /*
         * Second row.
         */
        item {

            Row(

                modifier =
                    Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.spacedBy(
                        12.dp
                    )
            ) {


                StatisticCard(

                    title =
                        if (isEnglish)
                            "Best Score"
                        else
                            "最高分",

                    value =
                        "${statistics.bestScore}%",

                    modifier =
                        Modifier.weight(1f)
                )


                StatisticCard(

                    title =
                        if (isEnglish)
                            "Correct"
                        else
                            "答对题数",

                    value =
                        statistics
                            .correctAnswers
                            .toString(),

                    modifier =
                        Modifier.weight(1f)
                )
            }
        }


        /*
         * Saved trails.
         */
        item {

            Card(

                modifier =
                    Modifier.fillMaxWidth(),

                shape =
                    RoundedCornerShape(
                        16.dp
                    ),

                colors =
                    CardDefaults.cardColors(

                        containerColor =
                            MaterialTheme
                                .colorScheme
                                .primaryContainer
                    )
            ) {


                Row(

                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(
                                20.dp
                            ),

                    horizontalArrangement =
                        Arrangement.SpaceBetween,

                    verticalAlignment =
                        Alignment.CenterVertically
                ) {


                    Column {


                        Text(

                            text =
                                if (isEnglish)
                                    "Saved Learning Trails"
                                else
                                    "收藏的学习路线",

                            style =
                                MaterialTheme
                                    .typography
                                    .titleMedium,

                            fontWeight =
                                FontWeight.SemiBold
                        )


                        Spacer(
                            modifier =
                                Modifier.height(
                                    4.dp
                                )
                        )


                        Text(

                            text =
                                if (isEnglish)
                                    "Trails saved for future exploration"
                                else
                                    "保存以便之后继续探索的路线",

                            style =
                                MaterialTheme
                                    .typography
                                    .bodySmall
                        )
                    }


                    Text(

                        text =
                            uiState
                                .savedTrails
                                .toString(),

                        style =
                            MaterialTheme
                                .typography
                                .headlineLarge,

                        fontWeight =
                            FontWeight.Bold,

                        color =
                            MaterialTheme
                                .colorScheme
                                .primary
                    )
                }
            }
        }


        /*
         * Recent results title.
         */
        item {

            Spacer(
                modifier =
                    Modifier.height(
                        4.dp
                    )
            )


            Text(

                text =
                    if (isEnglish)
                        "Recent Quiz Results"
                    else
                        "最近测验成绩",

                style =
                    MaterialTheme
                        .typography
                        .titleLarge,

                fontWeight =
                    FontWeight.Bold
            )
        }


        /*
         * Empty history message.
         */
        if (
            uiState
                .recentQuizResults
                .isEmpty()
        ) {


            item {

                Card(

                    modifier =
                        Modifier.fillMaxWidth(),

                    shape =
                        RoundedCornerShape(
                            16.dp
                        )
                ) {


                    Column(

                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(
                                    24.dp
                                ),

                        horizontalAlignment =
                            Alignment.CenterHorizontally
                    ) {


                        Text(

                            text =
                                if (isEnglish)
                                    "No quiz results yet"
                                else
                                    "暂时没有测验记录",

                            style =
                                MaterialTheme
                                    .typography
                                    .titleMedium,

                            fontWeight =
                                FontWeight.SemiBold
                        )


                        Spacer(
                            modifier =
                                Modifier.height(
                                    6.dp
                                )
                        )


                        Text(

                            text =
                                if (isEnglish)
                                    "Complete a Nature Learning Quiz to start tracking your progress."
                                else
                                    "完成一次自然学习测验后，这里将开始记录你的学习进度。",

                            style =
                                MaterialTheme
                                    .typography
                                    .bodyMedium,

                            color =
                                MaterialTheme
                                    .colorScheme
                                    .onSurfaceVariant
                        )
                    }
                }
            }

        } else {


            items(
                uiState.recentQuizResults
            ) { result ->


                QuizResultCard(

                    result =
                        result,

                    isEnglish =
                        isEnglish
                )
            }
        }


        item {

            Spacer(
                modifier =
                    Modifier.height(
                        100.dp
                    )
            )
        }
    }
}


@Composable
private fun StatisticCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {

    Card(

        modifier =
            modifier,

        shape =
            RoundedCornerShape(
                16.dp
            ),

        colors =
            CardDefaults.cardColors(

                containerColor =
                    MaterialTheme
                        .colorScheme
                        .surfaceVariant
            )
    ) {


        Column(

            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(
                        18.dp
                    ),

            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {


            Text(

                text =
                    value,

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
                    Modifier.height(
                        4.dp
                    )
            )


            Text(

                text =
                    title,

                style =
                    MaterialTheme
                        .typography
                        .bodyMedium,

                color =
                    MaterialTheme
                        .colorScheme
                        .onSurfaceVariant
            )
        }
    }
}


@Composable
private fun QuizResultCard(
    result: QuizResultEntity,
    isEnglish: Boolean
) {

    val percentage =

        if (
            result.totalQuestions <= 0
        ) {

            0

        } else {

            (result.score * 100) /
                    result.totalQuestions
        }


    val formattedDate =

        SimpleDateFormat(
            "dd MMM yyyy, HH:mm",
            Locale.getDefault()
        )
            .format(
                Date(
                    result.completedAt
                )
            )


    Card(

        modifier =
            Modifier.fillMaxWidth(),

        shape =
            RoundedCornerShape(
                16.dp
            ),

        colors =
            CardDefaults.cardColors(

                containerColor =
                    MaterialTheme
                        .colorScheme
                        .surfaceVariant
            )
    ) {


        Column(

            modifier =
                Modifier.padding(
                    18.dp
                )
        ) {


            Row(

                modifier =
                    Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.SpaceBetween,

                verticalAlignment =
                    Alignment.CenterVertically
            ) {


                Text(

                    text =
                        result.trailName,

                    style =
                        MaterialTheme
                            .typography
                            .titleMedium,

                    fontWeight =
                        FontWeight.Bold,

                    modifier =
                        Modifier.weight(
                            1f
                        )
                )


                Text(

                    text =
                        "$percentage%",

                    style =
                        MaterialTheme
                            .typography
                            .titleLarge,

                    fontWeight =
                        FontWeight.Bold,

                    color =
                        MaterialTheme
                            .colorScheme
                            .primary
                )
            }


            Spacer(
                modifier =
                    Modifier.height(
                        8.dp
                    )
            )


            HorizontalDivider()


            Spacer(
                modifier =
                    Modifier.height(
                        8.dp
                    )
            )


            Text(

                text =
                    if (isEnglish)
                        "Score: ${result.score} / ${result.totalQuestions}"
                    else
                        "得分：${result.score} / ${result.totalQuestions}",

                style =
                    MaterialTheme
                        .typography
                        .bodyMedium
            )


            Spacer(
                modifier =
                    Modifier.height(
                        4.dp
                    )
            )


            Text(

                text =
                    formattedDate,

                style =
                    MaterialTheme
                        .typography
                        .bodySmall,

                color =
                    MaterialTheme
                        .colorScheme
                        .onSurfaceVariant
            )
        }
    }
}
