// ViewModel - manages UI state using MVVM
package com.nammashaale.inventory.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.nammashaale.inventory.data.db.AppDatabase
import com.nammashaale.inventory.data.model.*
import com.nammashaale.inventory.data.repository.AppRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = AppRepository(AppDatabase.getDatabase(application))

    val assets = repo.allAssets.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val totalCount = repo.totalCount.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)
    val needsRepairCount = repo.needsRepairCount.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)
    val workingCount = repo.workingCount.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)
    val issues = repo.allIssues.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addAsset(name: String, serial: String, category: String) {
        viewModelScope.launch {
            repo.addAsset(Asset(name = name, serialNumber = serial, category = category))
        }
    }

    fun updateAssetCondition(asset: Asset, condition: Condition, notes: String = "") {
        viewModelScope.launch {
            repo.updateAsset(asset.copy(condition = condition))
            repo.addHealthCheck(HealthCheck(assetId = asset.id, condition = condition, notes = notes))
        }
    }

    fun deleteAsset(asset: Asset) {
        viewModelScope.launch { repo.deleteAsset(asset) }
    }

    fun logIssue(asset: Asset, description: String) {
        viewModelScope.launch {
            repo.addIssue(IssueLog(assetId = asset.id, assetName = asset.name, description = description))
            repo.updateAsset(asset.copy(condition = Condition.BROKEN))
        }
    }

    fun deleteIssue(log: IssueLog) {
        viewModelScope.launch { repo.deleteIssue(log) }
    }
}
