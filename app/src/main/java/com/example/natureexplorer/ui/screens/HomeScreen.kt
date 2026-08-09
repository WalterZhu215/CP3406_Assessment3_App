package com.example.natureexplorer.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.natureexplorer.LocalIsEnglish
import com.example.natureexplorer.ui.viewmodels.HomeViewModel



@Composable
fun HomeScreen(
    viewModel: HomeViewModel
) {


    val uiState by
    viewModel
        .uiState
        .collectAsState()


    val isEnglish =
        LocalIsEnglish.current


    val welcomeTitle =

        if (isEnglish)
            "Welcome back, Explorer!"
        else
            "欢迎回来，探险家！"


    val welcomeSubtitle =

        if (isEnglish)
            "Learn about nature while you explore."
        else
            "在探索自然的同时学习生态知识。"


    val featuredTitle =

        if (isEnglish)
            "Featured Learning Location"
        else
            "精选学习地点"


    val liveDataTitle =

        if (isEnglish)
            "Live Environment Data"
        else
            "实时环境数据"


    val communityTitle =

        if (isEnglish)
            "Community Contributions"
        else
            "社区点评"


    LazyColumn(

        modifier =
            Modifier
                .fillMaxSize()
                .padding(
                    horizontal = 16.dp
                ),

        verticalArrangement =
            Arrangement.spacedBy(
                24.dp
            )
    ) {


        /*
         * Welcome section
         */
        item {


            Column(

                modifier =
                    Modifier.padding(
                        top = 24.dp
                    )
            ) {


                Text(

                    text =
                        welcomeTitle,

                    style =
                        MaterialTheme
                            .typography
                            .headlineMedium,

                    fontWeight =
                        FontWeight.Bold
                )


                Spacer(
                    modifier =
                        Modifier.height(
                            4.dp
                        )
                )


                Text(

                    text =
                        welcomeSubtitle,

                    style =
                        MaterialTheme
                            .typography
                            .bodyLarge,

                    color =
                        MaterialTheme
                            .colorScheme
                            .onSurfaceVariant
                )
            }
        }


        /*
         * Featured learning location
         */
        item {


            Text(

                text =
                    featuredTitle,

                style =
                    MaterialTheme
                        .typography
                        .titleLarge,

                fontWeight =
                    FontWeight.SemiBold,

                modifier =
                    Modifier.padding(
                        bottom = 8.dp
                    )
            )


            Card(

                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(
                            200.dp
                        ),

                shape =
                    RoundedCornerShape(
                        16.dp
                    ),

                elevation =
                    CardDefaults
                        .cardElevation(
                            defaultElevation =
                                4.dp
                        )
            ) {


                Box(

                    modifier =
                        Modifier.fillMaxSize()
                ) {


                    AsyncImage(

                        model =
                            "https://images.unsplash.com/photo-1511497584788-876760111969?q=80&w=2560&auto=format&fit=crop",

                        contentDescription =
                            if (isEnglish)
                                "Redwood forest"
                            else
                                "红杉森林",

                        modifier =
                            Modifier.fillMaxSize(),

                        contentScale =
                            ContentScale.Crop
                    )


                    Box(

                        modifier =
                            Modifier
                                .fillMaxSize()
                                .background(

                                    Brush
                                        .verticalGradient(

                                            colors =
                                                listOf(

                                                    Color.Transparent,

                                                    Color.Black.copy(
                                                        alpha = 0.8f
                                                    )
                                                ),

                                            startY =
                                                150f
                                        )
                                )
                    )


                    Column(

                        modifier =
                            Modifier
                                .align(
                                    Alignment.BottomStart
                                )
                                .padding(
                                    16.dp
                                )
                    ) {


                        Text(

                            text =
                                uiState
                                    .featuredTitle,

                            style =
                                MaterialTheme
                                    .typography
                                    .titleLarge,

                            color =
                                Color.White,

                            fontWeight =
                                FontWeight.Bold
                        )


                        Text(

                            text =
                                uiState
                                    .featuredSubtitle,

                            style =
                                MaterialTheme
                                    .typography
                                    .bodyMedium,

                            color =
                                Color.White.copy(
                                    alpha = 0.85f
                                )
                        )
                    }
                }
            }
        }


        /*
         * LIVE API DATA
         */
        item {


            Text(

                text =
                    liveDataTitle,

                style =
                    MaterialTheme
                        .typography
                        .titleLarge,

                fontWeight =
                    FontWeight.SemiBold
            )


            Spacer(
                modifier =
                    Modifier.height(
                        8.dp
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
                                .primaryContainer
                    )
            ) {


                Column(

                    modifier =
                        Modifier.padding(
                            20.dp
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


                        Column {


                            Text(

                                text =
                                    if (isEnglish)
                                        "Redwood National Park"
                                    else
                                        "红杉国家公园",

                                style =
                                    MaterialTheme
                                        .typography
                                        .titleMedium,

                                fontWeight =
                                    FontWeight.Bold
                            )


                            Text(

                                text =
                                    if (isEnglish)
                                        "Current outdoor learning conditions"
                                    else
                                        "当前户外学习环境",

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


                        OutlinedButton(

                            onClick = {

                                viewModel
                                    .loadWeather()
                            }
                        ) {


                            Icon(

                                imageVector =
                                    Icons.Filled.Refresh,

                                contentDescription =
                                    if (isEnglish)
                                        "Refresh"
                                    else
                                        "刷新"
                            )
                        }
                    }


                    Spacer(
                        modifier =
                            Modifier.height(
                                20.dp
                            )
                    )


                    when {


                        uiState.isWeatherLoading -> {


                            Column(

                                modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            16.dp
                                        ),

                                horizontalAlignment =
                                    Alignment.CenterHorizontally
                            ) {


                                CircularProgressIndicator()


                                Spacer(
                                    modifier =
                                        Modifier.height(
                                            12.dp
                                        )
                                )


                                Text(

                                    text =
                                        if (isEnglish)
                                            "Loading live environmental data..."
                                        else
                                            "正在获取实时环境数据..."
                                )
                            }
                        }


                        uiState.weatherError != null -> {


                            Column {


                                Text(

                                    text =
                                        if (isEnglish)
                                            "Live data is currently unavailable."
                                        else
                                            "暂时无法获取实时环境数据。",

                                    color =
                                        MaterialTheme
                                            .colorScheme
                                            .error,

                                    fontWeight =
                                        FontWeight.SemiBold
                                )


                                Spacer(
                                    modifier =
                                        Modifier.height(
                                            8.dp
                                        )
                                )


                                Button(

                                    onClick = {

                                        viewModel
                                            .loadWeather()
                                    }
                                ) {


                                    Text(

                                        text =
                                            if (isEnglish)
                                                "Try Again"
                                            else
                                                "重新尝试"
                                    )
                                }
                            }
                        }


                        uiState.weather != null -> {


                            val weather =
                                uiState.weather!!


                            Text(

                                text =
                                    weather.condition,

                                style =
                                    MaterialTheme
                                        .typography
                                        .headlineSmall,

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
                                        18.dp
                                    )
                            )


                            Row(

                                modifier =
                                    Modifier.fillMaxWidth(),

                                horizontalArrangement =
                                    Arrangement.SpaceBetween
                            ) {


                                EnvironmentValue(

                                    title =
                                        if (isEnglish)
                                            "Temperature"
                                        else
                                            "温度",

                                    value =
                                        "${weather.temperature}°C",

                                    modifier =
                                        Modifier.weight(
                                            1f
                                        )
                                )


                                EnvironmentValue(

                                    title =
                                        if (isEnglish)
                                            "Humidity"
                                        else
                                            "湿度",

                                    value =
                                        "${weather.humidity}%",

                                    modifier =
                                        Modifier.weight(
                                            1f
                                        )
                                )


                                EnvironmentValue(

                                    title =
                                        if (isEnglish)
                                            "Wind"
                                        else
                                            "风速",

                                    value =
                                        "${weather.windSpeed} km/h",

                                    modifier =
                                        Modifier.weight(
                                            1f
                                        )
                                )
                            }


                            Spacer(
                                modifier =
                                    Modifier.height(
                                        18.dp
                                    )
                            )


                            Text(

                                text =
                                    if (isEnglish)
                                        "Learning note: Weather conditions can influence wildlife activity, plant water loss and the safety of outdoor fieldwork."
                                    else
                                        "学习提示：天气情况会影响野生动物活动、植物水分流失以及户外实地学习的安全性。",

                                style =
                                    MaterialTheme
                                        .typography
                                        .bodyMedium
                            )


                            Spacer(
                                modifier =
                                    Modifier.height(
                                        10.dp
                                    )
                            )


                            Text(

                                text =
                                    if (isEnglish)
                                        "Updated: ${weather.observationTime}"
                                    else
                                        "更新时间：${weather.observationTime}",

                                style =
                                    MaterialTheme
                                        .typography
                                        .bodySmall,

                                color =
                                    MaterialTheme
                                        .colorScheme
                                        .onSurfaceVariant
                            )


                            Text(

                                text =
                                    "Source: Open-Meteo",

                                style =
                                    MaterialTheme
                                        .typography
                                        .labelSmall,

                                color =
                                    MaterialTheme
                                        .colorScheme
                                        .onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }


        /*
         * Community contributions
         */
        item {


            Text(

                text =
                    communityTitle,

                style =
                    MaterialTheme
                        .typography
                        .titleLarge,

                fontWeight =
                    FontWeight.SemiBold,

                modifier =
                    Modifier.padding(
                        bottom = 8.dp
                    )
            )


            LazyRow(

                horizontalArrangement =
                    Arrangement.spacedBy(
                        16.dp
                    )
            ) {


                items(
                    uiState.communityReviews
                ) { place ->


                    Card(

                        modifier =
                            Modifier.width(
                                220.dp
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
                                    16.dp
                                )
                        ) {


                            Text(

                                text =
                                    place.name,

                                style =
                                    MaterialTheme
                                        .typography
                                        .titleMedium,

                                fontWeight =
                                    FontWeight.Bold
                            )


                            Row(

                                verticalAlignment =
                                    Alignment.CenterVertically,

                                modifier =
                                    Modifier.padding(
                                        vertical = 4.dp
                                    )
                            ) {


                                Icon(

                                    imageVector =
                                        Icons.Filled.Star,

                                    contentDescription =
                                        null,

                                    tint =
                                        Color(
                                            0xFFFFB300
                                        )
                                )


                                Spacer(
                                    modifier =
                                        Modifier.width(
                                            4.dp
                                        )
                                )


                                Text(
                                    text =
                                        place.rating
                                )
                            }


                            Text(

                                text =
                                    "\"${place.review}\"",

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
private fun EnvironmentValue(

    title: String,

    value: String,

    modifier: Modifier =
        Modifier

) {


    Column(

        modifier =
            modifier,

        horizontalAlignment =
            Alignment.CenterHorizontally
    ) {


        Text(

            text =
                value,

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
                    .bodySmall,

            color =
                MaterialTheme
                    .colorScheme
                    .onSurfaceVariant
        )
    }
}