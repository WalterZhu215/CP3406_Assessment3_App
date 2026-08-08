package com.example.natureexplorer.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.natureexplorer.ui.viewmodels.CollectionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    trailName: String,
    imageUrl: String,
    onBackClick: () -> Unit,
    collectionViewModel: CollectionViewModel // 接收共享数据仓库
) {
    // 监听收藏列表的数据变化
    val collectionState by collectionViewModel.uiState.collectAsState()

    // 判断当前景点是否在收藏列表中
    val isFavorite = collectionState.savedTrails.any { it.name == trailName }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Trail Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    // 点击执行收藏/取消收藏
                    IconButton(onClick = { collectionViewModel.toggleFavorite(trailName, imageUrl) }) {
                        Icon(
                            // 动态切换实心和空心爱心图标
                            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = "Save",
                            // 动态切换颜色（实心为主题色，空心为默认色）
                            tint = if (isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
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
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = "Image of $trailName",
                modifier = Modifier.fillMaxWidth().height(250.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = trailName,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.LocationOn,
                        contentDescription = "Location",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Approx. 5 km away",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Text(
                    text = "About this trail",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "This is a beautiful natural trail featuring diverse flora and fauna. Perfect for a weekend hike to reconnect with nature. Remember to stay on the designated paths to protect the local ecosystem.",
                    style = MaterialTheme.typography.bodyLarge,
                    lineHeight = MaterialTheme.typography.bodyLarge.lineHeight * 1.2f
                )
            }
        }
    }
}

