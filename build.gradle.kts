// Top-level build file where you can add configuration options common to all sub-projects/modules.
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    // Android 应用插件，版本 8.4.0 与 Gradle 8.7 兼容
    id("com.android.application") version "8.4.0" apply false

    // Kotlin Android 插件，版本 1.9.23 是目前非常稳定的版本
    id("org.jetbrains.kotlin.android") version "1.9.23" apply false

    // Kotlin Compose 插件 (如果你在用 Compose)
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0" apply false
}