package com.example.natureexplorer.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
// 接收全局传进来的语言状态和修改函数
fun ProfileScreen(
    isEnglish: Boolean,
    onLanguageChange: (Boolean) -> Unit
) {
    var showPrivacySheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var preciseLocationEnabled by remember { mutableStateOf(false) }
    var analyticsEnabled by remember { mutableStateOf(false) }

    val pageTitle = if (isEnglish) "Profile" else "个人中心"
    val levelText = if (isEnglish) "Local Explorer • Level 5" else "本地向导 • Lv.5"
    val statContribLabel = if (isEnglish) "Contributions" else "贡献内容"
    val statTrailsLabel = if (isEnglish) "Trails Hiked" else "探索足迹"
    val statBadgesLabel = if (isEnglish) "Badges" else "获得徽章"
    val achieveTitle = if (isEnglish) "Recent Achievements" else "近期成就"
    val badge1 = if (isEnglish) "First Hike" else "初次徒步"
    val badge2 = if (isEnglish) "Local Guide" else "本地向导"
    val badge3 = if (isEnglish) "Trail Mapper" else "路线测绘"
    val badge4 = if (isEnglish) "Helpful Reviewer" else "热心点评"
    val settingsTitle = if (isEnglish) "Account Settings" else "账号设置"
    val privacyTitle = if (isEnglish) "Privacy & Permissions" else "隐私与权限"
    val privacyDesc = if (isEnglish) "Manage location and data sharing" else "管理位置与数据共享"
    val langTitle = if (isEnglish) "Language" else "语言设置"
    val langDesc = if (isEnglish) "Switch to Chinese" else "切换为英文"

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = pageTitle, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 24.dp, top = 24.dp))
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 24.dp)) {
            Box(modifier = Modifier.size(80.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primary), contentAlignment = Alignment.Center) {
                Text("ZZ", style = MaterialTheme.typography.headlineLarge, color = MaterialTheme.colorScheme.onPrimary)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text("Zhiwei Zhu", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Text(text = levelText, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold)
            }
        }
        Row(modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
            StatItem(label = statContribLabel, value = "42")
            StatItem(label = statTrailsLabel, value = "15")
            StatItem(label = statBadgesLabel, value = "8")
        }
        Text(text = achieveTitle, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(bottom = 12.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.padding(bottom = 32.dp)) {
            item { BadgeItem(icon = Icons.Filled.Star, title = badge1) }
            item { BadgeItem(icon = Icons.Filled.Place, title = badge2) }
            item { BadgeItem(icon = Icons.Filled.Map, title = badge3) }
            item { BadgeItem(icon = Icons.Filled.ThumbUp, title = badge4) }
        }
        Text(text = settingsTitle, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(bottom = 8.dp))
        Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
            Column {
                ListItem(
                    modifier = Modifier.clickable { showPrivacySheet = true },
                    headlineContent = { Text(privacyTitle) },
                    supportingContent = { Text(privacyDesc) },
                    leadingContent = { Icon(Icons.Filled.Shield, contentDescription = "Privacy") },
                    colors = ListItemDefaults.colors(containerColor = androidx.compose.ui.graphics.Color.Transparent)
                )
                HorizontalDivider()
                ListItem(
                    headlineContent = { Text(langTitle) },
                    supportingContent = { Text(langDesc) },
                    leadingContent = { Icon(Icons.Filled.Settings, contentDescription = "Language") },
                    trailingContent = {
                        Switch(
                            checked = !isEnglish,
                            onCheckedChange = { onLanguageChange(!isEnglish) }, // 呼叫外层更改全局状态
                            thumbContent = if (!isEnglish) { { Text("中", style = MaterialTheme.typography.labelSmall) } } else { { Text("EN", style = MaterialTheme.typography.labelSmall) } }
                        )
                    },
                    colors = ListItemDefaults.colors(containerColor = androidx.compose.ui.graphics.Color.Transparent)
                )
            }
        }
    }

    if (showPrivacySheet) {
        val sheetTitle = if (isEnglish) "Privacy Controls" else "隐私控制"
        val sheetDesc = if (isEnglish) "We believe in data minimization. You control what you share." else "我们坚信数据最小化原则。您的数据由您做主。"
        val locTitle = if (isEnglish) "Precise Location" else "精确定位"
        val locDesc = if (isEnglish) "Use exact GPS for navigation." else "使用精确的 GPS 坐标进行导航。"
        val anaTitle = if (isEnglish) "Anonymous Analytics" else "匿名数据分析"
        val anaDesc = if (isEnglish) "Help us improve without personal identifiers." else "分享崩溃日志帮助改进，不含身份信息。"
        val saveBtn = if (isEnglish) "Save Preferences" else "保存设置"

        ModalBottomSheet(onDismissRequest = { showPrivacySheet = false }, sheetState = sheetState) {
            Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 16.dp)) {
                Text(text = sheetTitle, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
                Text(text = sheetDesc, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 24.dp))
                Row(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.weight(1f).padding(end = 16.dp)) {
                        Text(text = locTitle, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                        Text(text = locDesc, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Switch(checked = preciseLocationEnabled, onCheckedChange = { preciseLocationEnabled = it })
                }
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                Row(modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.weight(1f).padding(end = 16.dp)) {
                        Text(text = anaTitle, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                        Text(text = anaDesc, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Switch(checked = analyticsEnabled, onCheckedChange = { analyticsEnabled = it })
                }
                Button(onClick = { showPrivacySheet = false }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp)) {
                    Text(saveBtn)
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun StatItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
        Text(text = label, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
fun BadgeItem(icon: ImageVector, title: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(80.dp)) {
        Box(
            modifier = Modifier.size(64.dp).clip(CircleShape).background(MaterialTheme.colorScheme.secondaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = icon, contentDescription = title, tint = MaterialTheme.colorScheme.onSecondaryContainer, modifier = Modifier.size(32.dp))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = title, style = MaterialTheme.typography.bodySmall, textAlign = TextAlign.Center, maxLines = 2)
    }
}