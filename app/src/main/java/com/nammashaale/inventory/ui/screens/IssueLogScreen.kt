package com.nammashaale.inventory.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nammashaale.inventory.data.model.Asset
import com.nammashaale.inventory.data.model.IssueLog
import com.nammashaale.inventory.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IssueLogScreen(viewModel: MainViewModel, onBack: () -> Unit) {
    val issues by viewModel.issues.collectAsState()
    val assets by viewModel.assets.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    val fmt = remember { SimpleDateFormat("dd MMM yyyy", Locale.getDefault()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Issue Log") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, "Log Issue")
            }
        }
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            items(issues) { issue ->
                Card(
                    Modifier.fillMaxWidth().padding(vertical = 6.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(Modifier.weight(1f)) {
                            Text(issue.assetName, fontSize = 16.sp)
                            Text(issue.description, fontSize = 13.sp, color = Color.DarkGray)
                            Text(
                                fmt.format(Date(issue.reportedAt)),
                                fontSize = 11.sp,
                                color = Color.Gray
                            )
                        }
                        IconButton(onClick = { viewModel.deleteIssue(issue) }) {
                            Icon(Icons.Default.Delete, "Delete", tint = Color.Gray)
                        }
                    }
                }
            }
            if (issues.isEmpty()) {
                item {
                    Box(
                        Modifier.fillMaxWidth().padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No issues reported. Tap + to log one.", color = Color.Gray)
                    }
                }
            }
        }
    }

    if (showDialog && assets.isNotEmpty()) {
        LogIssueDialog(
            assets = assets,
            onDismiss = { showDialog = false }
        ) { asset, desc ->
            viewModel.logIssue(asset, desc)
            showDialog = false
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogIssueDialog(
    assets: List<Asset>,
    onDismiss: () -> Unit,
    onConfirm: (Asset, String) -> Unit
) {
    var description by remember { mutableStateOf("") }
    var selectedAsset by remember { mutableStateOf(assets.first()) }
    var expanded by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Log an Issue") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("Select Asset", fontSize = 13.sp, color = Color.Gray)
                Box {
                    OutlinedButton(
                        onClick = { expanded = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(selectedAsset.name)
                    }
                    DropdownMenu(expanded, { expanded = false }) {
                        assets.forEach { a ->
                            DropdownMenuItem(
                                text = { Text(a.name) },
                                onClick = { selectedAsset = a; expanded = false }
                            )
                        }
                    }
                }
                OutlinedTextField(
                    description, { description = it },
                    label = { Text("Description") },
                    placeholder = { Text("e.g. Football lost during match") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 2
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                if (description.isNotBlank()) onConfirm(selectedAsset, description)
            }) { Text("Log Issue") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}