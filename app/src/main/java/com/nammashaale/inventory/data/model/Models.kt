package com.nammashaale.inventory.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class Condition { WORKING, NEEDS_REPAIR, BROKEN }

@Entity(tableName = "assets")
data class Asset(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val serialNumber: String,
    val category: String,
    val condition: Condition = Condition.WORKING,
    val photoPath: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "health_checks")
data class HealthCheck(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val assetId: Int,
    val condition: Condition,
    val notes: String = "",
    val checkedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "issue_logs")
data class IssueLog(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val assetId: Int,
    val assetName: String,
    val description: String,
    val reportedAt: Long = System.currentTimeMillis()
)