package com.nammashaale.inventory.ui.screens

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nammashaale.inventory.data.model.Condition
import com.nammashaale.inventory.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SummaryReportScreen(viewModel: MainViewModel, onBack: () -> Unit) {
    val assets by viewModel.assets.collectAsState()
    val issues by viewModel.issues.collectAsState()
    val context = LocalContext.current
    val date = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date())

    val working = assets.count { it.condition == Condition.WORKING }
    val repair  = assets.count { it.condition == Condition.NEEDS_REPAIR }
    val broken  = assets.count { it.condition == Condition.BROKEN }

    val reportText = buildString {
        appendLine("NAMMA-SHAALE INVENTORY REPORT")
        appendLine("Generated: $date")
        appendLine("================================")
        appendLine("SUMMARY")
        appendLine("Total Assets   : ${assets.size}")
        appendLine("Working        : $working")
        appendLine("Needs Repair   : $repair")
        appendLine("Broken         : $broken")
        appendLine()
        appendLine("ASSET LIST")
        assets.forEach { appendLine("- ${it.name} [${it.condition}] S/N: ${it.serialNumber}") }
        appendLine()
        appendLine("ISSUES LOGGED: ${issues.size}")
        issues.forEach { appendLine("- ${it.assetName}: ${it.description}") }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Summary Report") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        val intent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT, reportText)
                            putExtra(Intent.EXTRA_SUBJECT, "Namma-Shaale Inventory Report - $date")
                        }
                        context.startActivity(Intent.createChooser(intent, "Share Report"))
                    }) {
                        Icon(Icons.Default.Share, "Share")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Inventory Report", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text("Generated: $date", color = Color.Gray, fontSize = 13.sp)

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                ReportStatCard("Total",   "${assets.size}", Color(0xFF1565C0), Modifier.weight(1f))
                ReportStatCard("Working", "$working",       Color(0xFF2E7D32), Modifier.weight(1f))
                ReportStatCard("Repair",  "$repair",        Color(0xFFF57F17), Modifier.weight(1f))
                ReportStatCard("Broken",  "$broken",        Color(0xFFC62828), Modifier.weight(1f))
            }

            Divider()
            Text("Asset List", fontWeight = FontWeight.SemiBold)

            assets.forEach { asset ->
                val bgColor = when (asset.condition) {
                    Condition.WORKING      -> Color(0xFFE8F5E9)
                    Condition.NEEDS_REPAIR -> Color(0xFFFFF8E1)
                    Condition.BROKEN       -> Color(0xFFFFEBEE)
                }
                Card(
                    Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = bgColor)
                ) {
                    Column(Modifier.padding(10.dp)) {
                        Text(asset.name, fontWeight = FontWeight.Medium)
                        Text(
                            "${asset.category} • S/N: ${asset.serialNumber}",
                            fontSize = 12.sp, color = Color.Gray
                        )
                    }
                }
            }

            if (issues.isNotEmpty()) {
                Divider()
                Text("Issues Logged", fontWeight = FontWeight.SemiBold)
                issues.forEach { issue ->
                    Text("• ${issue.assetName}: ${issue.description}", fontSize = 13.sp)
                }
            }

            Spacer(Modifier.height(8.dp))
            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, reportText)
                        putExtra(Intent.EXTRA_SUBJECT, "Namma-Shaale Inventory Report - $date")
                    }
                    context.startActivity(Intent.createChooser(intent, "Share Report"))
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Share, null)
                Spacer(Modifier.width(8.dp))
                Text("Share Report")
            }
        }
    }
}

@Composable
fun ReportStatCard(label: String, value: String, color: Color, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = color)
    ) {
        Column(Modifier.padding(8.dp)) {
            Text(value, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(label, color = Color.White.copy(alpha = 0.85f), fontSize = 10.sp)
        }
    }
}