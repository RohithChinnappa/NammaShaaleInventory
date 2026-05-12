package com.nammashaale.inventory.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
fun HealthCheckScreen(viewModel: MainViewModel, onBack: () -> Unit) {
    val assets by viewModel.assets.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Monthly Health Check") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            item {
                Text(
                    "Tap the button to update each asset's condition:",
                    Modifier.padding(vertical = 8.dp),
                    color = Color.Gray,
                    fontSize = 13.sp
                )
            }
            items(assets) { asset ->
                HealthCheckRow(asset) { condition ->
                    viewModel.updateAssetCondition(asset, condition)
                }
            }
            if (assets.isEmpty()) {
                item {
                    Box(
                        Modifier.fillMaxWidth().padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No assets found. Add assets first.", color = Color.Gray)
                    }
                }
            }
        }
    }
}

@Composable
fun HealthCheckRow(asset: Asset, onConditionChange: (Condition) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        Modifier.fillMaxWidth().padding(vertical = 6.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f)) {
                Text(asset.name, fontSize = 16.sp)
                Text(asset.serialNumber, fontSize = 12.sp, color = Color.Gray)
            }
            Box {
                val condColor = when (asset.condition) {
                    Condition.WORKING      -> Color(0xFF2E7D32)
                    Condition.NEEDS_REPAIR -> Color(0xFFF57F17)
                    Condition.BROKEN       -> Color(0xFFC62828)
                }
                Button(
                    onClick = { expanded = true },
                    colors = ButtonDefaults.buttonColors(containerColor = condColor),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        asset.condition.name.replace("_", " "),
                        fontSize = 12.sp
                    )
                }
                DropdownMenu(expanded, { expanded = false }) {
                    Condition.values().forEach { c ->
                        DropdownMenuItem(
                            text = { Text(c.name.replace("_", " ")) },
                            onClick = {
                                onConditionChange(c)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}