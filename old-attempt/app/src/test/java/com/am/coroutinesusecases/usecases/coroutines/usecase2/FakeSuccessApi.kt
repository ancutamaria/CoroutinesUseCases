package com.am.coroutinesusecases.usecases.coroutines.usecase2

import com.am.coroutinesusecases.mock.*
import com.am.coroutinesusecases.utils.EndpointShouldNotBeCalledException
import java.lang.IllegalArgumentException

class FakeSuccessApi: MockApi {

    override suspend fun getRecentAndroidVersions(): List<AndroidVersion> {
        return mockAndroidVersions
    }

    override suspend fun getAndroidVersionFeatures(apiLevel: Int): VersionFeatures {
        return when (apiLevel) {
            27 -> mockVersionFeaturesOreo
            28 -> mockVersionFeaturesPie
            29 -> mockVersionFeaturesAndroid10
            else -> throw IllegalArgumentException("apiLevel not found")
        }
    }

}
