package com.pranshulgg.recordmaster.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.pranshulgg.recordmaster.R
import java.io.File
import java.util.Locale
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private data class FolderEntry(
    val dir: File,
    val recordingsCount: Int
)

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun DrawerContent(
    currentTab: String,
    selectedFolderName: String?,
    rootDirKey: Long,
    musicDir: File,
    onSelectTab: (tab: String, folder: String?) -> Unit,
    onRequestCreateFolder: () -> Unit,
    onRequestDeleteFolder: (folderName: String) -> Unit,
    onfoldersExpanded: (Boolean) -> Unit,
    foldersExpanded: Boolean,
    navController: NavController,
    closeDrawer: () -> Unit
) {
    val folders by produceState(initialValue = emptyList<FolderEntry>(), rootDirKey, musicDir.absolutePath) {
        value = withContext(Dispatchers.IO) {
            (musicDir.listFiles() ?: emptyArray())
                .filter { it.isDirectory && it.name != "garbage" }
                .sortedBy { it.name.lowercase(Locale.getDefault()) }
                .map { dir ->
                    FolderEntry(
                        dir = dir,
                        recordingsCount = dir.listFiles()?.count(File::isFile) ?: 0
                    )
                }
        }
    }

    ModalDrawerSheet(
        modifier = Modifier.width(280.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                item {
                    Text(
                        "RecordMaster",
                        modifier = Modifier.padding(16.dp, bottom = 0.dp, end = 16.dp, top = 16.dp),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Column(Modifier.padding(14.dp)) {
                        NavigationDrawerItem(
                            label = { Text("Recordings") },
                            selected = currentTab == "home",
                            icon = {
                                if (currentTab == "home") {
                                    Symbol(R.drawable.home_24px, color = MaterialTheme.colorScheme.onSecondaryContainer)
                                } else {
                                    Symbol(
                                        R.drawable.home_outlined_24px,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            },
                            onClick = {
                                onSelectTab("home", null)
                            }
                        )

                        NavigationDrawerItem(
                            label = { Text("Recently deleted") },
                            selected = currentTab == "garbage",
                            icon = {
                                if (currentTab == "garbage") {
                                    Symbol(
                                        R.drawable.folder_delete_24px,
                                        color = MaterialTheme.colorScheme.onSecondaryContainer
                                    )
                                } else {
                                    Symbol(
                                        R.drawable.folder_delete_outlined_24px,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            },
                            onClick = {
                                onSelectTab("garbage", null)
                            }
                        )

                        NavigationDrawerItem(
                            label = { Text("Settings") },
                            selected = false,
                            icon = {
                                Symbol(
                                    R.drawable.settings_outlined_24px,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            },
                            onClick = {
                                navController.navigate("OpenSettings")
                                closeDrawer()
                            }
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        if (folders.isNotEmpty()) {
                            Text(
                                "Folders",
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.W700
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        folders.forEach { folder ->
                            NavigationDrawerItem(
                                label = { Text(folder.dir.name) },
                                selected = currentTab == "folder" && selectedFolderName == folder.dir.name,
                                icon = {
                                    if (currentTab == "folder" && selectedFolderName == folder.dir.name) {
                                        Symbol(
                                            R.drawable.folder_24px,
                                            color = MaterialTheme.colorScheme.onSecondaryContainer
                                        )
                                    } else {
                                        Symbol(
                                            R.drawable.folder_outlined_24px,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                },
                                badge = {
                                    Text(
                                        folder.recordingsCount.toString(),
                                        fontSize = 15.sp,
                                        color = MaterialTheme.colorScheme.secondary
                                    )
                                },
                                onClick = { onSelectTab("folder", folder.dir.name) }
                            )
                        }
                        Spacer(Modifier.height(80.dp))
                    }
                }
            }

            FloatingActionButton(
                onClick = { onRequestCreateFolder() },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Symbol(R.drawable.create_new_folder_24px, color = MaterialTheme.colorScheme.onPrimaryContainer)
            }
        }
    }
}
