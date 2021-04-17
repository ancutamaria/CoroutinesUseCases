package com.am.coroutinesusecases.usecases.coroutines.usecase2.rx

import com.am.coroutinesusecases.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class SequentialNetworkRequestsRxViewModel(
    private val mockApi: RxMockApi = mockApi()
) : BaseViewModel<UiState>() {

    private val disposables = CompositeDisposable()
    fun perform2SequentialNetworkRequest() {
        uiState.value = UiState.Loading

        mockApi.getRecentAndroidVersions()
            .flatMap { androidVersions ->
                val recentVersion = androidVersions.last()
                mockApi.getAndroidVersionFeatures(recentVersion.apiLevel)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy (
                onSuccess = { featuresVersion ->
                    uiState.value = UiState.Success(featuresVersion)
                },
                onError = {
                    uiState.value = UiState.Error("Smth went wrong")
                }
                    )
            .addTo(disposables)
    }

    override fun onCleared() {
        super.onCleared()
        //to avoid memory leaks
        disposables.clear()
    }
}