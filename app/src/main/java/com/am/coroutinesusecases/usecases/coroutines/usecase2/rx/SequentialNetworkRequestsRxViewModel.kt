package com.am.coroutinesusecases.usecases.coroutines.usecase2.rx

import com.am.coroutinesusecases.base.BaseViewModel

class SequentialNetworkRequestsRxViewModel(
    private val mockApi: RxMockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun perform2SequentialNetworkRequest() {

    }
}