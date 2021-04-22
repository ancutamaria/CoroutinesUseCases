package com.am.coroutinesusecases.usecases.coroutines.usecase3

import androidx.lifecycle.viewModelScope
import com.am.coroutinesusecases.base.BaseViewModel
import com.am.coroutinesusecases.mock.MockApi
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class PerformNetworkRequestsConcurrentlyViewModel(
    private val mockApi: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performNetworkRequestsSequentially() {
        uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val oreo = mockApi.getAndroidVersionFeatures(27)
                val pie = mockApi.getAndroidVersionFeatures(28)
                val android10 = mockApi.getAndroidVersionFeatures(29)

                uiState.value = UiState.Success(listOf(oreo, pie, android10))
            } catch ( e: Exception) {
                uiState.value = UiState.Error("Network Request failed")
            }
        }

    }

    fun performNetworkRequestsConcurrently() {

        uiState.value = UiState.Loading
        val oreoDiffered = viewModelScope.async {
            mockApi.getAndroidVersionFeatures(27)
        }

        val pieDiffered = viewModelScope.async {
            mockApi.getAndroidVersionFeatures(28)
        }

        val android10Differed = viewModelScope.async {
            mockApi.getAndroidVersionFeatures(29)
        }

        viewModelScope.launch {
            val oreoFeatures = oreoDiffered.await()
            val pieFeatures = pieDiffered.await()
            val android10Features = android10Differed.await()

            uiState.value = UiState.Success(listOf(oreoFeatures, pieFeatures, android10Features))
        }

    }
}