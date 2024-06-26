package com.am.coroutinesusecases.usecases.coroutines.usecase1

import com.am.coroutinesusecases.mock.AndroidVersion
import com.am.coroutinesusecases.mock.MockApi
import com.am.coroutinesusecases.mock.VersionFeatures
import com.am.coroutinesusecases.utils.EndpointShouldNotBeCalledException
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response

class FakeErrorApi: MockApi {

    override suspend fun getRecentAndroidVersions(): List<AndroidVersion> {
        throw HttpException(
            Response.error<List<AndroidVersion>>(
                500,
                ResponseBody.create(MediaType.parse("application/json"), "")
            )
        )
    }

    override suspend fun getAndroidVersionFeatures(apiLevel: Int): VersionFeatures {
        throw EndpointShouldNotBeCalledException()
    }

}
