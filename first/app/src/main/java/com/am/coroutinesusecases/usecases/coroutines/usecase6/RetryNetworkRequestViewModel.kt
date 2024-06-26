package com.am.coroutinesusecases.usecases.coroutines.usecase6

import androidx.lifecycle.viewModelScope
import com.am.coroutinesusecases.base.BaseViewModel
import com.am.coroutinesusecases.mock.MockApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

class RetryNetworkRequestViewModel(
    private val mockApi: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performNetworkRequest() {
        uiState.value = UiState.Loading

        viewModelScope.launch { // coroutines started in the viewModelScope run on the main thread

            var numberOfRetries = 2

            try {
                retry(numberOfRetries) {
                    loadRecentAndroidVersions()
                }
            } catch (exception: Exception) {
                uiState.value = UiState.Error("Network failed + ${exception.message}")
            }
        }
    }

    private suspend fun <T> retry(
        numberOfRetries: Int,
        initialDelayMillies: Long = 100,
        maxDelayMillies: Long = 1000,
        factor: Double = 2.0,
        block: suspend () -> T): T {
        var currentDelay = initialDelayMillies
        repeat(numberOfRetries) {
            try {
                return block()
            } catch (e: Exception) {
                Timber.e(e)
            }
        delay(currentDelay)
        currentDelay = (currentDelay * factor).toLong().coerceAtMost(maxDelayMillies)
        }
        return block()
    }

    private suspend fun loadRecentAndroidVersions() {
        val recentAndroidVersions = mockApi.getRecentAndroidVersions()
        uiState.value = UiState.Success(recentAndroidVersions)
    }

}