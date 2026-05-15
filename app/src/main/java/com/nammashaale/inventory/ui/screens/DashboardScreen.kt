// Dashboard - shows asset statistics
package com.nammashaale.inventory.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nammashaale.inventory.viewmodel.MainViewModel

@Composable
fun DashboardScreen(viewModel: MainViewModel, onNavigate: (String) -> Unit) {
    val total by viewModel.totalCount.collectAsState()
    val working by viewModel.workingCount.collectAsState()
    val repair by viewModel.needsRepairCount.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Namma-Shaale Inventory", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Text("School Asset Dashboard", color = Color.Gray, fontSize = 14.sp)

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            StatCard("Total Assets", "$total", Color(0xFF1565C0), Modifier.weight(1f))
            StatCard("Working", "$working", Color(0xFF2E7D32), Modifier.weight(1f))
            StatCard("Needs Repair", "$repair", Color(0xFFC62828), Modifier.weight(1f))
        }

        Divider()
        Text("Quick Actions", fontWeight = FontWeight.SemiBold)

        QuickActionButton("Asset Register",       "assets",      onNavigate)
        QuickActionButton("Monthly Health Check", "healthcheck", onNavigate)
        QuickActionButton("Issue Log",            "issues",      onNavigate)
        QuickActionButton("Repair Requests",      "repairs",     onNavigate)
        QuickActionButton("Summary Report",       "report",      onNavigate)
    }
}

@Composable
fun StatCard(label: String, value: String, color: Color, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = color)
    ) {
        Column(Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(value, color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Text(label, color = Color.White.copy(alpha = 0.85f), fontSize = 11.sp)
        }
    }
}

@Composable
fun QuickActionButton(label: String, route: String, onNavigate: (String) -> Unit) {
    OutlinedButton(
        onClick = { onNavigate(route) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(label, modifier = Modifier.padding(vertical = 4.dp))
    }
}
