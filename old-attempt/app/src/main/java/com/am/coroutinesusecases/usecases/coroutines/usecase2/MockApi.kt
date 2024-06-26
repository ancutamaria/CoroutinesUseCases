package com.am.coroutinesusecases.usecases.coroutines.usecase2

import com.google.gson.Gson
import com.am.coroutinesusecases.mock.createMockApi
import com.am.coroutinesusecases.mock.mockAndroidVersions
import com.am.coroutinesusecases.mock.mockVersionFeaturesAndroid10
import com.am.coroutinesusecases.utils.MockNetworkInterceptor

fun mockApi() = createMockApi(
    MockNetworkInterceptor()
        .mock(
            "http://localhost/recent-android-versions",
            Gson().toJson(mockAndroidVersions),
            200,
            1500
        )
        .mock(
            "http://localhost/android-version-features/29",
            Gson().toJson(mockVersionFeaturesAndroid10),
            200,
            1500
        )
)