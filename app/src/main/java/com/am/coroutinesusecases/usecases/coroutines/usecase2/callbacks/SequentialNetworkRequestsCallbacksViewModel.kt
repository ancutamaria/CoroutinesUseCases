package com.am.coroutinesusecases.usecases.coroutines.usecase2.callbacks

import com.am.coroutinesusecases.base.BaseViewModel

class SequentialNetworkRequestsCallbacksViewModel(
    private val mockApi: CallbackMockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun perform2SequentialNetworkRequest() {

    }
}