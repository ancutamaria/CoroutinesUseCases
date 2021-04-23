package com.am.coroutinesusecases.usecases.coroutines.usecase6

import androidx.lifecycle.viewModelScope
import com.am.coroutinesusecases.base.BaseViewModel
import com.am.coroutinesusecases.mock.MockApi
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

    private suspend fun <T> retry(numberOfRetries: Int, block: suspend () -> T): T {
        repeat(numberOfRetries) {
            try {
                return block()
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
        return block()
    }

    private suspend fun loadRecentAndroidVersions() {
        val recentAndroidVersions = mockApi.getRecentAndroidVersions()
        uiState.value = UiState.Success(recentAndroidVersions)
    }

}