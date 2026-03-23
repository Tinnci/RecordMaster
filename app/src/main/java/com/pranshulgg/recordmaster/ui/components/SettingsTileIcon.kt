package com.pranshulgg.recordmaster.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
@Suppress("ktlint:standard:function-naming")
fun SettingsTileIcon(icon: Int, dangerColor: Boolean = false) {
    Symbol(
        icon,
        color = if (dangerColor) {
            MaterialTheme.colorScheme.onErrorContainer
        } else {
            MaterialTheme.colorScheme.onSurfaceVariant
        }
    )
}
