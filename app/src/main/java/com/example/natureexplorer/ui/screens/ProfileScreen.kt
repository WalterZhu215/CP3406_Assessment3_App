package com.example.natureexplorer.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    isEnglish: Boolean,
    onLanguageChange: (Boolean) -> Unit,
    onSettingsClick: () -> Unit
) {

    var showPrivacySheet by
    remember {
        mutableStateOf(false)
    }

    val sheetState =
        rememberModalBottomSheetState(
            skipPartiallyExpanded = true
        )

    var preciseLocationEnabled by
    remember {
        mutableStateOf(false)
    }

    var analyticsEnabled by
    remember {
        mutableStateOf(false)
    }


    val pageTitle =
        if (isEnglish)
            "Profile"
        else
            "个人中心"

    val levelText =
        if (isEnglish)
            "Local Explorer • Level 5"
        else
            "本地向导 • Lv.5"

    val statContribLabel =
        if (isEnglish)
            "Contributions"
        else
            "贡献内容"

    val statTrailsLabel =
        if (isEnglish)
            "Trails Hiked"
        else
            "探索足迹"

    val statBadgesLabel =
        if (isEnglish)
            "Badges"
        else
            "获得徽章"

    val achieveTitle =
        if (isEnglish)
            "Recent Achievements"
        else
            "近期成就"

    val badge1 =
        if (isEnglish)
            "First Hike"
        else
            "初次徒步"

    val badge2 =
        if (isEnglish)
            "Local Guide"
        else
            "本地向导"

    val badge3 =
        if (isEnglish)
            "Trail Mapper"
        else
            "路线测绘"

    val badge4 =
        if (isEnglish)
            "Helpful Reviewer"
        else
            "热心点评"

    val settingsTitle =
        if (isEnglish)
            "Account & Learning"
        else
            "账号与学习设置"

    val learningSettingsTitle =
        if (isEnglish)
            "Learning Settings"
        else
            "学习设置"

    val learningSettingsDescription =
        if (isEnglish)
            "Difficulty and language preferences"
        else
            "调整难度和语言偏好"

    val privacyTitle =
        if (isEnglish)
            "Privacy & Permissions"
        else
            "隐私与权限"

    val privacyDescription =
        if (isEnglish)
            "Manage location and data sharing"
        else
            "管理位置与数据共享"

    val languageTitle =
        if (isEnglish)
            "Quick Language Switch"
        else
            "快速语言切换"

    val languageDescription =
        if (isEnglish)
            "Switch to Chinese"
        else
            "切换为英文"


    Column(

        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp)
    ) {


        Text(

            text =
                pageTitle,

            style =
                MaterialTheme
                    .typography
                    .headlineMedium,

            fontWeight =
                FontWeight.Bold,

            modifier =
                Modifier.padding(
                    bottom = 24.dp,
                    top = 24.dp
                )
        )


        Row(

            verticalAlignment =
                Alignment.CenterVertically,

            modifier =
                Modifier.padding(
                    bottom = 24.dp
                )
        ) {


            Box(

                modifier =
                    Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(
                            MaterialTheme
                                .colorScheme
                                .primary
                        ),

                contentAlignment =
                    Alignment.Center
            ) {


                Text(

                    text = "ZZ",

                    style =
                        MaterialTheme
                            .typography
                            .headlineLarge,

                    color =
                        MaterialTheme
                            .colorScheme
                            .onPrimary
                )
            }


            Spacer(
                modifier =
                    Modifier.width(16.dp)
            )


            Column {


                Text(

                    text = "Zhiwei Zhu",

                    style =
                        MaterialTheme
                            .typography
                            .titleLarge,

                    fontWeight =
                        FontWeight.Bold
                )


                Text(

                    text =
                        levelText,

                    style =
                        MaterialTheme
                            .typography
                            .bodyMedium,

                    color =
                        MaterialTheme
                            .colorScheme
                            .primary,

                    fontWeight =
                        FontWeight.SemiBold
                )
            }
        }


        Row(

            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(
                        bottom = 32.dp
                    ),

            horizontalArrangement =
                Arrangement.SpaceEvenly
        ) {


            StatItem(
                label =
                    statContribLabel,
                value = "42"
            )


            StatItem(
                label =
                    statTrailsLabel,
                value = "15"
            )


            StatItem(
                label =
                    statBadgesLabel,
                value = "8"
            )
        }


        Text(

            text =
                achieveTitle,

            style =
                MaterialTheme
                    .typography
                    .titleMedium,

            fontWeight =
                FontWeight.SemiBold,

            modifier =
                Modifier.padding(
                    bottom = 12.dp
                )
        )


        LazyRow(

            horizontalArrangement =
                Arrangement.spacedBy(
                    16.dp
                ),

            modifier =
                Modifier.padding(
                    bottom = 32.dp
                )
        ) {


            item {

                BadgeItem(
                    icon =
                        Icons.Filled.Star,
                    title =
                        badge1
                )
            }


            item {

                BadgeItem(
                    icon =
                        Icons.Filled.Place,
                    title =
                        badge2
                )
            }


            item {

                BadgeItem(
                    icon =
                        Icons.Filled.Map,
                    title =
                        badge3
                )
            }


            item {

                BadgeItem(
                    icon =
                        Icons.Filled.ThumbUp,
                    title =
                        badge4
                )
            }
        }


        Text(

            text =
                settingsTitle,

            style =
                MaterialTheme
                    .typography
                    .titleMedium,

            fontWeight =
                FontWeight.SemiBold,

            modifier =
                Modifier.padding(
                    bottom = 8.dp
                )
        )


        Card(

            modifier =
                Modifier.fillMaxWidth(),

            shape =
                RoundedCornerShape(12.dp),

            colors =
                CardDefaults.cardColors(
                    containerColor =
                        MaterialTheme
                            .colorScheme
                            .surfaceVariant
                )
        ) {


            Column {


                /*
                 * Opens the dedicated Settings screen.
                 */
                ListItem(

                    modifier =
                        Modifier.clickable {
                            onSettingsClick()
                        },

                    headlineContent = {

                        Text(
                            learningSettingsTitle
                        )
                    },

                    supportingContent = {

                        Text(
                            learningSettingsDescription
                        )
                    },

                    leadingContent = {

                        Icon(
                            imageVector =
                                Icons.Filled.School,
                            contentDescription =
                                "Learning Settings"
                        )
                    },

                    colors =
                        ListItemDefaults.colors(
                            containerColor =
                                Color.Transparent
                        )
                )


                HorizontalDivider()


                /*
                 * Privacy controls.
                 */
                ListItem(

                    modifier =
                        Modifier.clickable {

                            showPrivacySheet =
                                true
                        },

                    headlineContent = {

                        Text(
                            privacyTitle
                        )
                    },

                    supportingContent = {

                        Text(
                            privacyDescription
                        )
                    },

                    leadingContent = {

                        Icon(
                            imageVector =
                                Icons.Filled.Shield,
                            contentDescription =
                                "Privacy"
                        )
                    },

                    colors =
                        ListItemDefaults.colors(
                            containerColor =
                                Color.Transparent
                        )
                )


                HorizontalDivider()


                /*
                 * Quick language switch.
                 */
                ListItem(

                    headlineContent = {

                        Text(
                            languageTitle
                        )
                    },

                    supportingContent = {

                        Text(
                            languageDescription
                        )
                    },

                    leadingContent = {

                        Icon(
                            imageVector =
                                Icons.Filled.Language,
                            contentDescription =
                                "Language"
                        )
                    },

                    trailingContent = {

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
                    },

                    colors =
                        ListItemDefaults.colors(
                            containerColor =
                                Color.Transparent
                        )
                )
            }
        }
    }


    if (showPrivacySheet) {


        val sheetTitle =
            if (isEnglish)
                "Privacy Controls"
            else
                "隐私控制"

        val sheetDescription =
            if (isEnglish)
                "We use data minimisation principles. You control what you share."
            else
                "我们遵循数据最小化原则，你可以控制自己共享的数据。"

        val locationTitle =
            if (isEnglish)
                "Precise Location"
            else
                "精确定位"

        val locationDescription =
            if (isEnglish)
                "Allow exact GPS information for navigation features."
            else
                "允许导航功能使用精确 GPS 信息。"

        val analyticsTitle =
            if (isEnglish)
                "Anonymous Analytics"
            else
                "匿名数据分析"

        val analyticsDescription =
            if (isEnglish)
                "Help improve the app without personal identifiers."
            else
                "在不包含个人身份信息的情况下帮助改进应用。"

        val saveButton =
            if (isEnglish)
                "Save Preferences"
            else
                "保存设置"


        ModalBottomSheet(

            onDismissRequest = {

                showPrivacySheet =
                    false
            },

            sheetState =
                sheetState
        ) {


            Column(

                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 24.dp,
                            vertical = 16.dp
                        )
            ) {


                Text(

                    text =
                        sheetTitle,

                    style =
                        MaterialTheme
                            .typography
                            .headlineSmall,

                    fontWeight =
                        FontWeight.Bold
                )


                Spacer(
                    modifier =
                        Modifier.height(8.dp)
                )


                Text(

                    text =
                        sheetDescription,

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
                        Modifier.height(24.dp)
                )


                Row(

                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(
                                bottom = 16.dp
                            ),

                    horizontalArrangement =
                        Arrangement.SpaceBetween,

                    verticalAlignment =
                        Alignment.CenterVertically
                ) {


                    Column(

                        modifier =
                            Modifier
                                .weight(1f)
                                .padding(
                                    end = 16.dp
                                )
                    ) {


                        Text(

                            text =
                                locationTitle,

                            style =
                                MaterialTheme
                                    .typography
                                    .titleMedium,

                            fontWeight =
                                FontWeight.SemiBold
                        )


                        Text(

                            text =
                                locationDescription,

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
                            preciseLocationEnabled,

                        onCheckedChange = {

                            preciseLocationEnabled =
                                it
                        }
                    )
                }


                HorizontalDivider()


                Spacer(
                    modifier =
                        Modifier.height(8.dp)
                )


                Row(

                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(
                                bottom = 24.dp
                            ),

                    horizontalArrangement =
                        Arrangement.SpaceBetween,

                    verticalAlignment =
                        Alignment.CenterVertically
                ) {


                    Column(

                        modifier =
                            Modifier
                                .weight(1f)
                                .padding(
                                    end = 16.dp
                                )
                    ) {


                        Text(

                            text =
                                analyticsTitle,

                            style =
                                MaterialTheme
                                    .typography
                                    .titleMedium,

                            fontWeight =
                                FontWeight.SemiBold
                        )


                        Text(

                            text =
                                analyticsDescription,

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
                            analyticsEnabled,

                        onCheckedChange = {

                            analyticsEnabled =
                                it
                        }
                    )
                }


                Button(

                    onClick = {

                        showPrivacySheet =
                            false
                    },

                    modifier =
                        Modifier.fillMaxWidth(),

                    shape =
                        RoundedCornerShape(12.dp)
                ) {


                    Text(
                        saveButton
                    )
                }


                Spacer(
                    modifier =
                        Modifier.height(24.dp)
                )
            }
        }
    }
}


@Composable
fun StatItem(
    label: String,
    value: String
) {

    Column(

        horizontalAlignment =
            Alignment.CenterHorizontally
    ) {


        Text(

            text =
                value,

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


        Text(

            text =
                label,

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


@Composable
fun BadgeItem(
    icon: ImageVector,
    title: String
) {

    Column(

        horizontalAlignment =
            Alignment.CenterHorizontally,

        modifier =
            Modifier.width(80.dp)
    ) {


        Box(

            modifier =
                Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(
                        MaterialTheme
                            .colorScheme
                            .secondaryContainer
                    ),

            contentAlignment =
                Alignment.Center
        ) {


            Icon(

                imageVector =
                    icon,

                contentDescription =
                    title,

                tint =
                    MaterialTheme
                        .colorScheme
                        .onSecondaryContainer,

                modifier =
                    Modifier.size(32.dp)
            )
        }


        Spacer(
            modifier =
                Modifier.height(8.dp)
        )


        Text(

            text =
                title,

            style =
                MaterialTheme
                    .typography
                    .bodySmall,

            textAlign =
                TextAlign.Center,

            maxLines = 2
        )
    }
}