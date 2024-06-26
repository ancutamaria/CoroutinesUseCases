package com.am.coroutinesusecases.usecases.coroutines.usecase4

import androidx.lifecycle.viewModelScope
import com.am.coroutinesusecases.base.BaseViewModel
import com.am.coroutinesusecases.mock.MockApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class VariableAmountOfNetworkRequestsViewModel(
    private val mockApi: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performNetworkRequestsSequentially() {

        uiState.value = UiState.Loading

        viewModelScope.launch {
            val recentVersions = mockApi.getRecentAndroidVersions()
            val versionFeatures = recentVersions.map { androidVersion ->
                mockApi.getAndroidVersionFeatures(androidVersion.apiLevel)
            }
            uiState.value = UiState.Success(versionFeatures)
        }
    }

    fun performNetworkRequestsConcurrently() {

        uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val recentVersions = mockApi.getRecentAndroidVersions()
                val versionFeatures = recentVersions.map { androidVersion ->
                    async {
                        mockApi.getAndroidVersionFeatures(androidVersion.apiLevel)
                    }
                }.awaitAll()
                uiState.value = UiState.Success(versionFeatures)
            } catch (e: Exception) {
                uiState.value = UiState.Error("Request failed")
            }
        }
    }
}