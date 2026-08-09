package com.example.natureexplorer.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.natureexplorer.LocalIsEnglish
import com.example.natureexplorer.ui.viewmodels.ExploreViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun ExploreScreen(
    viewModel: ExploreViewModel = viewModel(),
    onTrailClick: (String, String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val locationPermissionState = rememberPermissionState(android.Manifest.permission.ACCESS_FINE_LOCATION)
    val isEnglish = LocalIsEnglish.current

    var showAddDialog by remember { mutableStateOf(false) }
    var newName by remember { mutableStateOf("") }
    var newDist by remember { mutableStateOf("") }
    var newCat by remember { mutableStateOf("Hiking") }

    val pageTitle = if (isEnglish) "Discover Nature" else "探索自然"
    val searchHint = if (isEnglish) "Search for trails, parks..." else "搜索路线、公园..."
    val nearbyTitle = if (isEnglish) "Nearby Trails" else "附近路线"

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add Trail")
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp)) {
            Text(text = pageTitle, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 16.dp, top = 8.dp))
            OutlinedTextField(
                value = uiState.searchQuery, onValueChange = { viewModel.updateSearchQuery(it) },
                placeholder = { Text(searchHint) }, leadingIcon = { Icon(Icons.Filled.Search, null) },
                modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(bottom = 16.dp)) {
                items(uiState.categories) { category ->
                    val isSelected = category == uiState.selectedCategory
                    FilterChip(
                        selected = isSelected, onClick = { viewModel.updateCategory(category) },
                        label = { Text(category) }, shape = RoundedCornerShape(16.dp)
                    )
                }
            }
            if (locationPermissionState.status.isGranted) {
                Text(text = nearbyTitle, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 12.dp))
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(items = uiState.nearbyTrails, key = { it.name }) { trail ->


                        val dismissState = rememberSwipeToDismissBoxState(
                            confirmValueChange = { dismissValue ->
                                if (dismissValue == SwipeToDismissBoxValue.EndToStart) {
                                    viewModel.removeTrail(trail.name)
                                    true
                                } else {
                                    false
                                }
                            }
                        )


                        SwipeToDismissBox(
                            state = dismissState,
                            enableDismissFromStartToEnd = false,
                            backgroundContent = {
                                val color = if (dismissState.dismissDirection == SwipeToDismissBoxValue.EndToStart) MaterialTheme.colorScheme.errorContainer else Color.Transparent
                                Box(
                                    modifier = Modifier.fillMaxSize().background(color, RoundedCornerShape(12.dp)).padding(horizontal = 20.dp),
                                    contentAlignment = Alignment.CenterEnd
                                ) {
                                    Icon(Icons.Default.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.onErrorContainer)
                                }
                            }
                        ) {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                                onClick = { onTrailClick(trail.name, trail.imageUrl) }
                            ) {
                                Row(modifier = Modifier.padding(12.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                                    AsyncImage(model = trail.imageUrl, contentDescription = null, contentScale = ContentScale.Crop, modifier = Modifier.size(72.dp).clip(RoundedCornerShape(8.dp)).background(MaterialTheme.colorScheme.secondaryContainer))
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(text = trail.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, maxLines = 1)
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(Icons.Filled.LocationOn, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(14.dp))
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text(text = trail.distance, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.primary)
                                        }
                                        Text(text = trail.category, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(top = 4.dp))
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                Button(onClick = { locationPermissionState.launchPermissionRequest() }) { Text(if(isEnglish) "Enable Location" else "开启定位") }
            }
        }
    }

    if (showAddDialog) {
        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text(if (isEnglish) "Add New Trail" else "添加新路线") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(value = newName, onValueChange = { newName = it }, label = { Text(if (isEnglish) "Name" else "名称") }, singleLine = true)
                    OutlinedTextField(value = newDist, onValueChange = { newDist = it }, label = { Text(if (isEnglish) "Distance (e.g. 5 km)" else "距离") }, singleLine = true)
                    OutlinedTextField(value = newCat, onValueChange = { newCat = it }, label = { Text(if (isEnglish) "Category" else "分类") }, singleLine = true)
                }
            },
            confirmButton = {
                Button(onClick = {
                    if (newName.isNotBlank()) viewModel.addNewTrail(newName, newDist, newCat)
                    showAddDialog = false
                    newName = ""; newDist = ""; newCat = "Hiking"
                }) { Text(if (isEnglish) "Save" else "保存") }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialog = false }) { Text(if (isEnglish) "Cancel" else "取消") }
            }
        )
    }
}