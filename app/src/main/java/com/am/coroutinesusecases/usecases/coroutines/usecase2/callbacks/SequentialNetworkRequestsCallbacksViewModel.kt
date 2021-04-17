package com.am.coroutinesusecases.usecases.coroutines.usecase2.callbacks

import com.am.coroutinesusecases.base.BaseViewModel
import com.am.coroutinesusecases.mock.AndroidVersion
import com.am.coroutinesusecases.mock.VersionFeatures
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SequentialNetworkRequestsCallbacksViewModel(
    private val mockApi: CallbackMockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun perform2SequentialNetworkRequest() {
        uiState.value = UiState.Loading

        val getAndroidVersionCall = mockApi.getRecentAndroidVersions()
        getAndroidVersionCall.enqueue(object: Callback<List<AndroidVersion>>{
            override fun onResponse(
                call: Call<List<AndroidVersion>>,
                response: Response<List<AndroidVersion>>
            ) {
                if (response.isSuccessful){
                    val mostRecentVersion = response.body()!!.last()
                    val getAndroidFeaturesCall = mockApi.getAndroidVersionFeatures(mostRecentVersion.apiLevel)
                    getAndroidFeaturesCall.enqueue(object: Callback<VersionFeatures>{
                        override fun onResponse(
                            call: Call<VersionFeatures>,
                            response: Response<VersionFeatures>
                        ) {
                            if (response.isSuccessful){
                                val featuresOfMostRecentVersion = response.body()!!
                                uiState.value = UiState.Success(featuresOfMostRecentVersion)
                            } else {
                                uiState.value = UiState.Error("Network request failed")
                            }
                        }

                        override fun onFailure(call: Call<VersionFeatures>, t: Throwable) {
                            uiState.value = UiState.Error("Something unexpected happened!")
                        }

                    })
                } else {
                    uiState.value = UiState.Error("Network request failed")
                }
            }

            override fun onFailure(call: Call<List<AndroidVersion>>, t: Throwable) {
                uiState.value = UiState.Error("Something unexpected happened!")
            }

        })
    }
}