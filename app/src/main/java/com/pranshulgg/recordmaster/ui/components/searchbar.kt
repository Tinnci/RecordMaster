@file:Suppress("ktlint:standard:filename")

package com.pranshulgg.recordmaster.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pranshulgg.recordmaster.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Suppress("ktlint:standard:function-naming")
fun RecorderSearchBar(
    text: String,
    onTextChange: (String) -> Unit,
    onSearch: () -> Unit,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    showMenu: () -> Unit
) {
    val horizontalPadding by animateDpAsState(
        targetValue = if (expanded) 0.dp else 16.dp,
        animationSpec = tween(durationMillis = 300)
    )

    Box(modifier = Modifier.fillMaxWidth()) {
        SearchBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(horizontal = horizontalPadding),
            inputField = {
                SearchBarDefaults.InputField(
                    query = text,
                    onQueryChange = onTextChange,
                    onSearch = { onSearch() },
                    expanded = expanded,
                    onExpandedChange = onExpandedChange,
                    placeholder = { Text("Search recordings..") },
                    leadingIcon = {
                        IconButton(onClick = showMenu) {
                            Symbol(R.drawable.menu_24px, color = MaterialTheme.colorScheme.onSurface)
                        }
                    },
                    trailingIcon = {
                        IconButton(onClick = {}) {
                            Symbol(R.drawable.settings_24px, color = MaterialTheme.colorScheme.onSurface)
                        }
                    }
                )
            },
            expanded = expanded,
            onExpandedChange = onExpandedChange
        ) {
            // suggestions/content slot - we keep empty because HomeScreen will render the expanded list itself
        }
    }
}
