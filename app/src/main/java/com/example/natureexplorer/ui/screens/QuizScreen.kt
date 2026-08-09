package com.example.natureexplorer.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.natureexplorer.LocalIsEnglish

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    trailName: String,
    onBackClick: () -> Unit
) {
    val isEnglish = LocalIsEnglish.current


    var currentQuestionIndex by remember { mutableStateOf(0) }
    var score by remember { mutableStateOf(0) }
    var showResult by remember { mutableStateOf(false) }


    val questions = listOf(
        Pair(
            if (isEnglish) "What type of ecosystem is typically protected in $trailName?" else "$trailName 通常保护哪种类型的生态系统？",
            listOf(if (isEnglish) "Native Forest" else "原生森林", if (isEnglish) "Desert" else "沙漠", if (isEnglish) "Tundra" else "苔原")
        ),
        Pair(
            if (isEnglish) "Why is it important to stay on the designated paths?" else "为什么留在指定的路径上很重要？",
            listOf(if (isEnglish) "To protect local flora and fauna" else "为了保护当地动植物", if (isEnglish) "To walk faster" else "为了走得更快", if (isEnglish) "To avoid getting lost" else "为了避免迷路")
        ),
        Pair(
            if (isEnglish) "What is a key principle of 'Leave No Trace'?" else "“无痕山林”的一个关键原则是什么？",
            listOf(if (isEnglish) "Take only pictures, leave only footprints" else "只带走照片，只留下脚印", if (isEnglish) "Feed the wildlife" else "喂食野生动物", if (isEnglish) "Pick beautiful flowers" else "采摘美丽的花朵")
        )
    )

    val correctAnswers = listOf(0, 0, 0)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEnglish) "Nature Quiz" else "自然生态测验") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (showResult) {

                Text(
                    text = if (isEnglish) "Quiz Completed!" else "测验完成！",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = if (isEnglish) "Your Score: $score / ${questions.size}" else "你的得分: $score / ${questions.size}",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = onBackClick,
                    modifier = Modifier.fillMaxWidth().height(50.dp)
                ) {
                    Text(if (isEnglish) "Return to Details" else "返回详情页")
                }
            } else {

                LinearProgressIndicator(
                    progress = { (currentQuestionIndex + 1).toFloat() / questions.size },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp)
                )

                Text(
                    text = if (isEnglish) "Question ${currentQuestionIndex + 1}" else "第 ${currentQuestionIndex + 1} 题",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = questions[currentQuestionIndex].first,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(32.dp))


                questions[currentQuestionIndex].second.forEachIndexed { index, answer ->
                    OutlinedButton(
                        onClick = {
                            if (index == correctAnswers[currentQuestionIndex]) {
                                score++
                            }
                            if (currentQuestionIndex < questions.size - 1) {
                                currentQuestionIndex++
                            } else {
                                showResult = true
                            }
                        },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp).height(56.dp)
                    ) {
                        Text(answer, style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        }
    }
}

