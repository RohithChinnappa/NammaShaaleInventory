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
import com.nammashaale.inventory.data.model.Condition
import com.nammashaale.inventory.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepairRequestScreen(viewModel: MainViewModel, onBack: () -> Unit) {
    val assets by viewModel.assets.collectAsState()
    val brokenAssets = assets.filter {
        it.condition == Condition.BROKEN || it.condition == Condition.NEEDS_REPAIR
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Repair Requests") },
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
                Card(
                    Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0))
                ) {
                    Text(
                        "Items below need SDMC attention. Share this list with the School Development and Monitoring Committee.",
                        Modifier.padding(12.dp),
                        fontSize = 13.sp,
                        color = Color(0xFF6D4C41)
                    )
                }
            }
            items(brokenAssets) { asset ->
                Card(
                    Modifier.fillMaxWidth().padding(vertical = 5.dp),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Row(
                        Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(Modifier.weight(1f)) {
                            Text(asset.name, fontSize = 15.sp)
                            Text("S/N: ${asset.serialNumber}", fontSize = 12.sp, color = Color.Gray)
                            Text(asset.category, fontSize = 12.sp, color = Color.Gray)
                        }
                        val color = if (asset.condition == Condition.BROKEN)
                            Color(0xFFC62828) else Color(0xFFF57F17)
                        Surface(color = color, shape = RoundedCornerShape(6.dp)) {
                            Text(
                                asset.condition.name.replace("_", " "),
                                color = Color.White,
                                fontSize = 11.sp,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }
            if (brokenAssets.isEmpty()) {
                item {
                    Box(
                        Modifier.fillMaxWidth().padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "All assets are in good condition!",
                            color = Color(0xFF2E7D32)
                        )
                    }
                }
            }
        }
    }
}