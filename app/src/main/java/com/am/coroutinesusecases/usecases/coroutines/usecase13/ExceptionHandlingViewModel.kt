package com.am.coroutinesusecases.usecases.coroutines.usecase13

import androidx.lifecycle.viewModelScope
import com.am.coroutinesusecases.base.BaseViewModel
import com.am.coroutinesusecases.mock.MockApi
import kotlinx.coroutines.*
import retrofit2.HttpException
import timber.log.Timber

class ExceptionHandlingViewModel(
    private val api: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun handleExceptionWithTryCatch() {
        uiState.value = UiState.Loading

        viewModelScope.launch {
            try {
                api.getAndroidVersionFeatures(27)
            } catch (e: Exception) {
                if (e is HttpException) {
                    if (e.code() == 500) {
                        // msg 1
                    } else {
                        // msg 2
                    }
                }
                uiState.value = UiState.Error("Network request failed: $e")
            }
        }
    }

    fun handleWithCoroutineExceptionHandler() {
        uiState.value = UiState.Loading

        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            uiState.value = UiState.Error("Network request failed: $throwable")
        }
        viewModelScope.launch(exceptionHandler) {
            api.getAndroidVersionFeatures(27)
        }
    }

    fun showResultsEvenIfChildCoroutineFails() {
        uiState.value = UiState.Loading

        viewModelScope.launch {

            supervisorScope {
                val oreoFeaturesDeferred = async {
                    api.getAndroidVersionFeatures(27)
                }
                val pieFeaturesDeferred = async {
                    api.getAndroidVersionFeatures(28)
                }
                val android10FeaturesDeferred = async {
                    api.getAndroidVersionFeatures(29)
                }

//                replaced all this with the below
//                val oreoFeatures = try {
//                    oreoFeaturesDeferred.await()
//                } catch (e: Exception) {
//                    Timber.e("Error loading oreo features")
//                    null
//                }
//                val pieFeatures = try {
//                    pieFeaturesDeferred.await()
//                } catch (e: Exception) {
//                    Timber.e("Error loading pie features")
//                    null
//                }
//                val android10Features = try {
//                    android10FeaturesDeferred.await()
//                } catch (e: Exception) {
//                    Timber.e("Error loading android10 features")
//                    null
//                }
//                val versionFeatures = listOfNotNull(oreoFeatures, pieFeatures, android10Features)

                val versionFeatures = listOf(
                        oreoFeaturesDeferred,
                        pieFeaturesDeferred,
                        android10FeaturesDeferred
                ).mapNotNull {
                            try {
                                Timber.e("Success loading features data")
                                it.await()
                            } catch (e: Exception) {
                                if (e is CancellationException) {
                                    throw e
                                }
                                Timber.e("Error loading features data")
                                null
                            }
                        }
                uiState.value = UiState.Success(versionFeatures)
            }
        }
    }
}