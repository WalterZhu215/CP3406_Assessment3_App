package com.example.natureexplorer.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
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
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.natureexplorer.LocalIsEnglish
import com.example.natureexplorer.ui.viewmodels.HomeViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    // 获取全局语言状态
    val isEnglish = LocalIsEnglish.current

    val welcomeTitle = if (isEnglish) "Welcome back, Explorer!" else "欢迎回来，探险家！"
    val welcomeSub = if (isEnglish) "Ready for your next adventure?" else "准备好开始下一次冒险了吗？"
    val featuredTitle = if (isEnglish) "Featured Today" else "今日精选"
    val communityTitle = if (isEnglish) "Community Contributions" else "社区点评"

    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(24.dp)) {
        item {
            Column(modifier = Modifier.padding(top = 16.dp)) {
                Text(text = welcomeTitle, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                Text(text = welcomeSub, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        item {
            Text(text = featuredTitle, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(bottom = 8.dp))
            Card(modifier = Modifier.fillMaxWidth().height(200.dp), shape = RoundedCornerShape(16.dp), elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)) {
                Box(modifier = Modifier.fillMaxSize()) {
                    AsyncImage(model = "https://images.unsplash.com/photo-1511497584788-876760111969?q=80&w=2560&auto=format&fit=crop", contentDescription = null, modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
                    Box(modifier = Modifier.fillMaxSize().background(Brush.verticalGradient(colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f)), startY = 150f)))
                    Column(modifier = Modifier.align(Alignment.BottomStart).padding(16.dp)) {
                        Text(text = uiState.featuredTitle, style = MaterialTheme.typography.titleLarge, color = Color.White, fontWeight = FontWeight.Bold)
                        Text(text = uiState.featuredSubtitle, style = MaterialTheme.typography.bodyMedium, color = Color.White.copy(alpha = 0.8f))
                    }
                }
            }
        }
        item {
            Text(text = communityTitle, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(bottom = 8.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                items(uiState.communityReviews) { place ->
                    Card(modifier = Modifier.width(220.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = place.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 4.dp)) {
                                Icon(imageVector = Icons.Filled.Star, contentDescription = null, tint = Color(0xFFFFB300), modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = place.rating, style = MaterialTheme.typography.bodyMedium)
                            }
                            Text(text = "\"${place.review}\"", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }
        }
    }
}