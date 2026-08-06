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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


data class ReviewPlace(val name: String, val rating: String, val review: String)

@Composable
fun HomeScreen() {

    val communityReviews = listOf(
        ReviewPlace("Pine Valley Trail", "4.8", "Great for beginners!"),
        ReviewPlace("Lake Serenity", "4.9", "Beautiful sunset views."),
        ReviewPlace("Echo Canyon", "4.5", "A bit rocky, wear good shoes.")
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        item {
            Column(modifier = Modifier.padding(top = 16.dp)) {
                Text(
                    text = "Welcome back, Explorer!",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Ready for your next adventure?",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }


        item {
            Text(
                text = "Featured Today",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.tertiaryContainer)
                    )

                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Redwood National Park",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onTertiaryContainer,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Discover the ancient giants.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onTertiaryContainer
                        )
                    }
                }
            }
        }

        item {
            Text(
                text = "Community Contributions",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(communityReviews) { place ->
                    Card(
                        modifier = Modifier.width(220.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = place.name,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(vertical = 4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Star,
                                    contentDescription = "Rating",
                                    tint = Color(0xFFFFB300), // 简单的金色星星
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = place.rating,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                            Text(
                                text = "\"${place.review}\"",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}