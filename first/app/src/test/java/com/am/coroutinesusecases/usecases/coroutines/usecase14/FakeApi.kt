package com.am.coroutinesusecases.usecases.coroutines.usecase14

import com.am.coroutinesusecases.mock.AndroidVersion
import com.am.coroutinesusecases.mock.MockApi
import com.am.coroutinesusecases.mock.VersionFeatures
import com.am.coroutinesusecases.mock.mockAndroidVersions
import com.am.coroutinesusecases.utils.EndpointShouldNotBeCalledException
import kotlinx.coroutines.delay

class FakeApi: MockApi {

    override suspend fun getRecentAndroidVersions(): List<AndroidVersion> {
        delay(1)
        return mockAndroidVersions
    }

    override suspend fun getAndroidVersionFeatures(apiLevel: Int): VersionFeatures {
        throw EndpointShouldNotBeCalledException()
    }

}
