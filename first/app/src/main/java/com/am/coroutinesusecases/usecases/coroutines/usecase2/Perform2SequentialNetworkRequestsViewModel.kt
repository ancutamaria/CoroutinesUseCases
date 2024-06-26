package com.am.coroutinesusecases.usecases.coroutines.usecase2

import androidx.lifecycle.viewModelScope
import com.am.coroutinesusecases.base.BaseViewModel
import com.am.coroutinesusecases.mock.MockApi
import kotlinx.coroutines.launch
import java.lang.Exception

class Perform2SequentialNetworkRequestsViewModel(
    private val mockApi: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun perform2SequentialNetworkRequest() {
        uiState.value = UiState.Loading

        viewModelScope.launch {
            try {
                val recentVersions = mockApi.getRecentAndroidVersions()
                val mostRecentVersion = recentVersions.last()
                val featuresOfMostRecentVersion =
                    mockApi.getAndroidVersionFeatures(mostRecentVersion.apiLevel)
                uiState.value = UiState.Success(featuresOfMostRecentVersion)
            } catch (exception: Exception) {
                uiState.value = UiState.Error("Network request failed")
            }
        }
    }

    //the coroutine get cancelled automatically so we don't need to worry about memory leaks
}