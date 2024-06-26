package com.am.coroutinesusecases.usecases.coroutines.usecase7

import androidx.lifecycle.viewModelScope
import com.am.coroutinesusecases.base.BaseViewModel
import com.am.coroutinesusecases.mock.MockApi
import kotlinx.coroutines.*
import timber.log.Timber

class TimeoutAndRetryViewModel(
    private val mockApi: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performNetworkRequest() {
        uiState.value = UiState.Loading
        val numberOfRetries = 2
        val timeout = 1000L

        val oreoDiffered = viewModelScope.async {
            retryWithTimeout(numberOfRetries, timeout) {
                mockApi.getAndroidVersionFeatures(27)
            }
        }

        val pieDiffered = viewModelScope.async {
            retryWithTimeout(numberOfRetries, timeout) {
                mockApi.getAndroidVersionFeatures(28)
            }
        }

        viewModelScope.launch {
            try {
                val versionFeatures = listOf(
                    oreoDiffered,
                    pieDiffered
                ).awaitAll()
                uiState.value = UiState.Success(versionFeatures)
            } catch (e: Exception) {
                uiState.value = UiState.Error("Network request failed")
            }
        }
    }

    private suspend fun <T> retryWithTimeout(
        numberOfRetries: Int,
        timeout: Long,
        block: suspend () -> T
    ) = retry(numberOfRetries) {
        withTimeout(timeout) {
            block()
        }
    }
    private suspend fun <T> retry(
        numberOfRetries: Int,
        delayBetweenRetries: Long = 100,
        block: suspend () -> T): T {
        repeat(numberOfRetries) {
            try {
                return block()
            } catch (e: Exception) {
                Timber.e(e)
            }
            delay(delayBetweenRetries)
        }
        return block()
    }
}