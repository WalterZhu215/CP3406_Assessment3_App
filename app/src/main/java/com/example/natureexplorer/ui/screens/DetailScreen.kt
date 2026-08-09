package com.example.natureexplorer.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.natureexplorer.LocalIsEnglish
import com.example.natureexplorer.ui.viewmodels.CollectionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    trailName: String,
    imageUrl: String,
    onBackClick: () -> Unit,
    onQuizClick: () -> Unit,
    collectionViewModel: CollectionViewModel
) {
    val collectionState by collectionViewModel.uiState.collectAsState()
    val isFavorite = collectionState.savedTrails.any { it.name == trailName }
    val scrollState = rememberScrollState()
    val isEnglish = LocalIsEnglish.current

    val appBarTitle = if (isEnglish) "Trail Details" else "路线详情"
    val approxDist = if (isEnglish) "Approx. 5 km away" else "距您约 5 公里"
    val aboutTitle = if (isEnglish) "About this trail" else "关于此路线"
    val aboutDesc = if (isEnglish) {
        "This is a beautiful natural trail featuring diverse flora and fauna. Perfect for a weekend hike to reconnect with nature. Remember to stay on the designated paths to protect the local ecosystem."
    } else {
        "这是一条美丽的自然路线，拥有丰富的动植物资源。非常适合周末徒步，重新与大自然建立联系。请记住留在指定的路径上，以保护当地的生态系统。"
    }
    val mapTitle = if (isEnglish) "Location Map" else "位置地图"
    val mapPlaceholderTitle = if (isEnglish) "Interactive Map Ready" else "交互式地图已就绪"
    val mapPlaceholderDesc = if (isEnglish) "(Google Maps API Key Required)" else "(需要 Google Maps API 密钥)"
    val quizBtnText = if (isEnglish) "Test Your Knowledge!" else "测试你的自然知识！" // 新增按钮文案

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(appBarTitle) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) { Icon(Icons.Filled.ArrowBack, contentDescription = "Back") }
                },
                actions = {
                    IconButton(onClick = { collectionViewModel.toggleFavorite(trailName, imageUrl) }) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = "Save",
                            tint = if (isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding).verticalScroll(scrollState)) {
            AsyncImage(model = imageUrl, contentDescription = null, modifier = Modifier.fillMaxWidth().height(250.dp), contentScale = ContentScale.Crop)

            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = trailName, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 16.dp)) {
                    Icon(Icons.Filled.LocationOn, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = approxDist, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }

                Text(text = aboutTitle, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(bottom = 8.dp))
                Text(text = aboutDesc, style = MaterialTheme.typography.bodyLarge, lineHeight = MaterialTheme.typography.bodyLarge.lineHeight * 1.2f, modifier = Modifier.padding(bottom = 24.dp))


                Button(
                    onClick = onQuizClick,
                    modifier = Modifier.fillMaxWidth().height(56.dp).padding(bottom = 12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Text(quizBtnText, style = MaterialTheme.typography.titleMedium)
                }
                Spacer(modifier = Modifier.height(12.dp))

                Text(text = mapTitle, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(bottom = 12.dp))
                Box(modifier = Modifier.fillMaxWidth().height(220.dp).clip(RoundedCornerShape(16.dp))) {
                    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.secondaryContainer), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Filled.LocationOn, null, modifier = Modifier.size(48.dp), tint = MaterialTheme.colorScheme.primary)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = mapPlaceholderTitle, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSecondaryContainer)
                            Text(text = mapPlaceholderDesc, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSecondaryContainer)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

