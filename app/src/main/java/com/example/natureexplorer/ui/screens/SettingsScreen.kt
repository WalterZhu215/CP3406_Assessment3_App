package com.example.natureexplorer.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    isEnglish: Boolean,
    quizDifficulty: String,
    onLanguageChange: (Boolean) -> Unit,
    onDifficultyChange: (String) -> Unit,
    onBackClick: () -> Unit
) {

    val difficultyOptions =
        listOf(
            "Easy",
            "Medium",
            "Hard"
        )


    Scaffold(

        topBar = {

            TopAppBar(

                title = {

                    Text(
                        text =
                            if (isEnglish)
                                "Learning Settings"
                            else
                                "学习设置"
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
                                if (isEnglish)
                                    "Back"
                                else
                                    "返回"
                        )
                    }
                }
            )
        }

    ) { innerPadding ->


        LazyColumn(

            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 20.dp),

            verticalArrangement =
                Arrangement.spacedBy(16.dp)

        ) {


            item {

                Spacer(
                    modifier =
                        Modifier.height(4.dp)
                )


                Text(

                    text =
                        if (isEnglish)
                            "Personalise Your Learning"
                        else
                            "个性化你的学习体验",

                    style =
                        MaterialTheme
                            .typography
                            .headlineSmall,

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
                            "Adjust the quiz difficulty and language to match your learning needs."
                        else
                            "根据你的学习需求调整测验难度和语言。",

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
             * Quiz difficulty settings
             */
            item {

                Card(

                    modifier =
                        Modifier.fillMaxWidth(),

                    shape =
                        RoundedCornerShape(16.dp),

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
                            Modifier.padding(18.dp)
                    ) {


                        Row(
                            verticalAlignment =
                                Alignment.CenterVertically
                        ) {

                            Icon(
                                imageVector =
                                    Icons.Filled.School,
                                contentDescription =
                                    null,
                                tint =
                                    MaterialTheme
                                        .colorScheme
                                        .primary
                            )


                            Text(

                                text =
                                    if (isEnglish)
                                        "Quiz Difficulty"
                                    else
                                        "测验难度",

                                style =
                                    MaterialTheme
                                        .typography
                                        .titleLarge,

                                fontWeight =
                                    FontWeight.Bold,

                                modifier =
                                    Modifier.padding(
                                        start = 12.dp
                                    )
                            )
                        }


                        Spacer(
                            modifier =
                                Modifier.height(8.dp)
                        )


                        Text(

                            text =
                                if (isEnglish)
                                    "Choose the difficulty level used in Nature Learning Quiz."
                                else
                                    "选择自然学习测验中使用的题目难度。",

                            style =
                                MaterialTheme
                                    .typography
                                    .bodyMedium,

                            color =
                                MaterialTheme
                                    .colorScheme
                                    .onSurfaceVariant
                        )


                        Spacer(
                            modifier =
                                Modifier.height(12.dp)
                        )


                        difficultyOptions
                            .forEachIndexed {
                                    index,
                                    difficulty ->


                                DifficultyOption(

                                    difficulty =
                                        difficulty,

                                    selected =
                                        quizDifficulty ==
                                                difficulty,

                                    isEnglish =
                                        isEnglish,

                                    onClick = {

                                        onDifficultyChange(
                                            difficulty
                                        )
                                    }
                                )


                                if (
                                    index <
                                    difficultyOptions.lastIndex
                                ) {

                                    HorizontalDivider()
                                }
                            }
                    }
                }
            }


            /*
             * Language settings
             */
            item {

                Card(

                    modifier =
                        Modifier.fillMaxWidth(),

                    shape =
                        RoundedCornerShape(16.dp),

                    colors =
                        CardDefaults.cardColors(
                            containerColor =
                                MaterialTheme
                                    .colorScheme
                                    .surfaceVariant
                        )
                ) {


                    Row(

                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(18.dp),

                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {


                        Icon(
                            imageVector =
                                Icons.Filled.Language,
                            contentDescription =
                                null,
                            tint =
                                MaterialTheme
                                    .colorScheme
                                    .primary
                        )


                        Column(

                            modifier =
                                Modifier
                                    .weight(1f)
                                    .padding(
                                        horizontal = 14.dp
                                    )
                        ) {


                            Text(

                                text =
                                    if (isEnglish)
                                        "Language"
                                    else
                                        "语言",

                                style =
                                    MaterialTheme
                                        .typography
                                        .titleMedium,

                                fontWeight =
                                    FontWeight.SemiBold
                            )


                            Text(

                                text =
                                    if (isEnglish)
                                        "English / 中文"
                                    else
                                        "中文 / English",

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


                        Switch(

                            checked =
                                !isEnglish,

                            onCheckedChange = {
                                    useChinese ->

                                onLanguageChange(
                                    !useChinese
                                )
                            }
                        )
                    }
                }
            }


            /*
             * Ethical and privacy information
             */
            item {

                Card(

                    modifier =
                        Modifier.fillMaxWidth(),

                    shape =
                        RoundedCornerShape(16.dp),

                    colors =
                        CardDefaults.cardColors(
                            containerColor =
                                MaterialTheme
                                    .colorScheme
                                    .secondaryContainer
                        )
                ) {


                    Column(

                        modifier =
                            Modifier.padding(18.dp)
                    ) {


                        Row(
                            verticalAlignment =
                                Alignment.CenterVertically
                        ) {


                            Icon(
                                imageVector =
                                    Icons.Filled.Shield,
                                contentDescription =
                                    null,
                                tint =
                                    MaterialTheme
                                        .colorScheme
                                        .onSecondaryContainer
                            )


                            Text(

                                text =
                                    if (isEnglish)
                                        "Privacy & Responsible Learning"
                                    else
                                        "隐私与负责任学习",

                                style =
                                    MaterialTheme
                                        .typography
                                        .titleMedium,

                                fontWeight =
                                    FontWeight.Bold,

                                modifier =
                                    Modifier.padding(
                                        start = 12.dp
                                    )
                            )
                        }


                        Spacer(
                            modifier =
                                Modifier.height(12.dp)
                        )


                        Text(

                            text =
                                if (isEnglish)
                                    "Quiz results and saved trails are stored locally on this device. The app does not require students to enter personal information to complete learning activities."
                                else
                                    "测验成绩和收藏路线保存在本地设备中。学生无需输入个人信息即可完成学习活动。",

                            style =
                                MaterialTheme
                                    .typography
                                    .bodyMedium
                        )


                        Spacer(
                            modifier =
                                Modifier.height(10.dp)
                        )


                        Text(

                            text =
                                if (isEnglish)
                                    "Learning settings can be changed at any time, allowing students to control the difficulty and language of their learning experience."
                                else
                                    "学习设置可以随时修改，让学生能够自主控制学习难度和语言。",

                            style =
                                MaterialTheme
                                    .typography
                                    .bodyMedium
                        )
                    }
                }
            }


            item {

                Spacer(
                    modifier =
                        Modifier.height(24.dp)
                )
            }
        }
    }
}


@Composable
private fun DifficultyOption(
    difficulty: String,
    selected: Boolean,
    isEnglish: Boolean,
    onClick: () -> Unit
) {

    val description =

        when (difficulty) {

            "Easy" ->

                if (isEnglish)
                    "3 questions • Basic ecology"
                else
                    "3 道题 • 基础生态知识"


            "Hard" ->

                if (isEnglish)
                    "7 questions • Advanced ecology"
                else
                    "7 道题 • 高级生态知识"


            else ->

                if (isEnglish)
                    "5 questions • Standard learning"
                else
                    "5 道题 • 标准学习难度"
        }


    Row(

        modifier =
            Modifier
                .fillMaxWidth()
                .clickable {
                    onClick()
                }
                .padding(
                    vertical = 10.dp
                ),

        verticalAlignment =
            Alignment.CenterVertically
    ) {


        RadioButton(

            selected =
                selected,

            onClick =
                onClick
        )


        Column(

            modifier =
                Modifier.padding(
                    start = 8.dp
                )
        ) {


            Text(

                text = difficulty,

                style =
                    MaterialTheme
                        .typography
                        .titleMedium,

                fontWeight =
                    if (selected)
                        FontWeight.Bold
                    else
                        FontWeight.Normal,

                color =
                    if (selected)
                        MaterialTheme
                            .colorScheme
                            .primary
                    else
                        MaterialTheme
                            .colorScheme
                            .onSurface
            )


            Text(

                text = description,

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

