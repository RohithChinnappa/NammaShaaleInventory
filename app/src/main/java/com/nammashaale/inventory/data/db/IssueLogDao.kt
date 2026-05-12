package com.nammashaale.inventory.data.db

import androidx.room.*
import com.nammashaale.inventory.data.model.IssueLog
import kotlinx.coroutines.flow.Flow

@Dao
interface IssueLogDao {
    @Query("SELECT * FROM issue_logs ORDER BY reportedAt DESC")
    fun getAll(): Flow<List<IssueLog>>

    @Insert
    suspend fun insert(log: IssueLog)

    @Delete
    suspend fun delete(log: IssueLog)
}