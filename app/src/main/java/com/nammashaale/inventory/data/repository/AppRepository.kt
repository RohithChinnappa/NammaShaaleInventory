package com.nammashaale.inventory.data.repository

import com.nammashaale.inventory.data.db.AppDatabase
import com.nammashaale.inventory.data.model.Asset
import com.nammashaale.inventory.data.model.HealthCheck
import com.nammashaale.inventory.data.model.IssueLog

class AppRepository(db: AppDatabase) {
    private val assetDao = db.assetDao()
    private val healthDao = db.healthCheckDao()
    private val issueDao = db.issueLogDao()

    val allAssets = assetDao.getAllAssets()
    val totalCount = assetDao.totalCount()
    val needsRepairCount = assetDao.needsRepairCount()
    val workingCount = assetDao.workingCount()

    suspend fun addAsset(asset: Asset) = assetDao.insert(asset)
    suspend fun updateAsset(asset: Asset) = assetDao.update(asset)
    suspend fun deleteAsset(asset: Asset) = assetDao.delete(asset)
    suspend fun getAssetById(id: Int) = assetDao.getById(id)

    fun healthChecksFor(assetId: Int) = healthDao.getForAsset(assetId)
    suspend fun addHealthCheck(hc: HealthCheck) = healthDao.insert(hc)

    val allIssues = issueDao.getAll()
    suspend fun addIssue(log: IssueLog) = issueDao.insert(log)
    suspend fun deleteIssue(log: IssueLog) = issueDao.delete(log)
}