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
import com.nammashaale.inventory.data.model.Condition
import com.nammashaale.inventory.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssetRegisterScreen(viewModel: MainViewModel, onBack: () -> Unit) {
    val assets by viewModel.assets.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Asset Register") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, "Add Asset")
            }
        }
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            items(assets) { asset -> AssetCard(asset, viewModel) }
            if (assets.isEmpty()) {
                item {
                    Box(
                        Modifier.fillMaxWidth().padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No assets yet. Tap + to add one.", color = Color.Gray)
                    }
                }
            }
        }
    }

    if (showDialog) {
        AddAssetDialog(
            onDismiss = { showDialog = false },
            onConfirm = { name, serial, category ->
                viewModel.addAsset(name, serial, category)
                showDialog = false
            }
        )
    }
}

@Composable
fun AssetCard(asset: Asset, viewModel: MainViewModel) {
    val conditionColor = when (asset.condition) {
        Condition.WORKING      -> Color(0xFF2E7D32)
        Condition.NEEDS_REPAIR -> Color(0xFFF57F17)
        Condition.BROKEN       -> Color(0xFFC62828)
    }
    Card(
        Modifier.fillMaxWidth().padding(vertical = 6.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text(asset.name, fontSize = 16.sp)
                Text("S/N: ${asset.serialNumber}", fontSize = 12.sp, color = Color.Gray)
                Text(asset.category, fontSize = 12.sp, color = Color.Gray)
            }
            Surface(color = conditionColor, shape = RoundedCornerShape(8.dp)) {
                Text(
                    asset.condition.name.replace("_", " "),
                    color = Color.White, fontSize = 11.sp,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
            IconButton(onClick = { viewModel.deleteAsset(asset) }) {
                Icon(Icons.Default.Delete, "Delete", tint = Color.Gray)
            }
        }
    }
}

@Composable
fun AddAssetDialog(onDismiss: () -> Unit, onConfirm: (String, String, String) -> Unit) {
    var name by remember { mutableStateOf("") }
    var serial by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Asset") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    name, { name = it },
                    label = { Text("Asset Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    serial, { serial = it },
                    label = { Text("Serial Number") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    category, { category = it },
                    label = { Text("Category (Lab / Sports / Tech)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(onClick = { if (name.isNotBlank()) onConfirm(name, serial, category) }) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}