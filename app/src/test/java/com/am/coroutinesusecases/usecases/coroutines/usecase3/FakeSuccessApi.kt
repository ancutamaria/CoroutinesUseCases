package com.am.coroutinesusecases.usecases.coroutines.usecase3

import com.am.coroutinesusecases.mock.*
import com.am.coroutinesusecases.utils.EndpointShouldNotBeCalledException
import kotlinx.coroutines.delay
import java.lang.IllegalArgumentException

class FakeSuccessApi(private val responseDelay: Long = 0L): MockApi {

    override suspend fun getRecentAndroidVersions(): List<AndroidVersion> {
        throw EndpointShouldNotBeCalledException()
    }

    override suspend fun getAndroidVersionFeatures(apiLevel: Int): VersionFeatures {
        delay(responseDelay)
        return when (apiLevel) {
            27 -> mockVersionFeaturesOreo
            28 -> mockVersionFeaturesPie
            29 -> mockVersionFeaturesAndroid10
            else -> throw IllegalArgumentException("apiLevel not found")
        }
    }

}
