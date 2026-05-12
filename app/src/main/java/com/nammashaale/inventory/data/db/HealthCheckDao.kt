package com.nammashaale.inventory.data.db

import androidx.room.*
import com.nammashaale.inventory.data.model.HealthCheck
import kotlinx.coroutines.flow.Flow

@Dao
interface HealthCheckDao {
    @Query("SELECT * FROM health_checks WHERE assetId = :assetId ORDER BY checkedAt DESC")
    fun getForAsset(assetId: Int): Flow<List<HealthCheck>>

    @Insert
    suspend fun insert(hc: HealthCheck)
}