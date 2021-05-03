package com.am.coroutinesusecases.usecases.coroutines.usecase1

import com.am.coroutinesusecases.mock.AndroidVersion
import com.am.coroutinesusecases.mock.MockApi
import com.am.coroutinesusecases.mock.VersionFeatures
import com.am.coroutinesusecases.mock.mockAndroidVersions
import com.am.coroutinesusecases.utils.EndpointShouldNotBeCalledException

class FakeSuccessApi: MockApi {

    override suspend fun getRecentAndroidVersions(): List<AndroidVersion> {
        return mockAndroidVersions
    }

    override suspend fun getAndroidVersionFeatures(apiLevel: Int): VersionFeatures {
        throw EndpointShouldNotBeCalledException()
    }

}
