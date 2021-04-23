package com.am.coroutinesusecases.usecases.coroutines.usecase5

import androidx.lifecycle.viewModelScope
import com.am.coroutinesusecases.base.BaseViewModel
import com.am.coroutinesusecases.mock.MockApi
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.withTimeoutOrNull
import timber.log.Timber
import java.lang.Exception

class NetworkRequestWithTimeoutViewModel(
    private val mockApi: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performNetworkRequest(timeout: Long) {
        uiState.value = UiState.Loading

//        usingWithTimeout(timeout)
        usingWithTimeoutOrNull(timeout)
    }

    private fun usingWithTimeout(timeout: Long) {
        viewModelScope.launch { // coroutines started in the viewModelScope run on the main thread
            try {
                val recentAndroidVersions = withTimeout(timeout) {
                    mockApi.getRecentAndroidVersions()
                }
                uiState.value = UiState.Success(recentAndroidVersions)
            } catch (timeoutCancellationException: TimeoutCancellationException) {
                uiState.value = UiState.Error("Network request timed out")
            } catch (exception: Exception) {
                uiState.value = UiState.Error("Network request failed + ${exception.message}")
            }
        }
    }

    private fun usingWithTimeoutOrNull(timeout: Long) {
        viewModelScope.launch { // coroutines started in the viewModelScope run on the main thread
            try {
                val recentAndroidVersions = withTimeoutOrNull(timeout) {
                    mockApi.getRecentAndroidVersions()
                }
                if (recentAndroidVersions != null) {
                    uiState.value = UiState.Success(recentAndroidVersions)
                } else {
                    uiState.value = UiState.Error("Network request timed out")
                }
            } catch (exception: Exception) {
                uiState.value = UiState.Error("Network request failed -> ${exception.message}")
            }
        }
    }

}