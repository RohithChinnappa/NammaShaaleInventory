package com.nammashaale.inventory.data.db

import android.content.Context
import androidx.room.*
import com.nammashaale.inventory.data.model.Asset
import com.nammashaale.inventory.data.model.HealthCheck
import com.nammashaale.inventory.data.model.IssueLog

@Database(
    entities = [Asset::class, HealthCheck::class, IssueLog::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun assetDao(): AssetDao
    abstract fun healthCheckDao(): HealthCheckDao
    abstract fun issueLogDao(): IssueLogDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "namma_shaale_db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}