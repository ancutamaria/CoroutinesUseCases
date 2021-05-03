package com.am.coroutinesusecases.usecases.coroutines.usecase1

import androidx.lifecycle.viewModelScope
import com.am.coroutinesusecases.base.BaseViewModel
import com.am.coroutinesusecases.mock.MockApi
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception

class PerformSingleNetworkRequestViewModel(
    private val mockApi: MockApi = mockApi()
) : BaseViewModel<UiState>() {

     fun performSingleNetworkRequest() {
        uiState.value = UiState.Loading

        viewModelScope.launch { // coroutines started in the viewModelScope run on the main thread
            try {
                val recentAndroidVersions = mockApi.getRecentAndroidVersions()
                println(recentAndroidVersions)
                uiState.value = UiState.Success(recentAndroidVersions)
            } catch (exception: Exception) {
                Timber.e(exception)
                uiState.value = UiState.Error("Network request failed")
            }
        }
    }
}