package com.nammashaale.inventory.data.db

import androidx.room.*
import com.nammashaale.inventory.data.model.Asset
import kotlinx.coroutines.flow.Flow

@Dao
interface AssetDao {
    @Query("SELECT * FROM assets ORDER BY createdAt DESC")
    fun getAllAssets(): Flow<List<Asset>>

    @Query("SELECT COUNT(*) FROM assets")
    fun totalCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM assets WHERE condition = 'NEEDS_REPAIR' OR condition = 'BROKEN'")
    fun needsRepairCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM assets WHERE condition = 'WORKING'")
    fun workingCount(): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(asset: Asset): Long

    @Update
    suspend fun update(asset: Asset)

    @Delete
    suspend fun delete(asset: Asset)

    @Query("SELECT * FROM assets WHERE id = :id")
    suspend fun getById(id: Int): Asset?
}