package com.am.coroutinesusecases.usecases.coroutines.usecase3

import com.google.gson.Gson
import com.am.coroutinesusecases.mock.createMockApi
import com.am.coroutinesusecases.mock.mockVersionFeaturesAndroid10
import com.am.coroutinesusecases.mock.mockVersionFeaturesOreo
import com.am.coroutinesusecases.mock.mockVersionFeaturesPie
import com.am.coroutinesusecases.utils.MockNetworkInterceptor

fun mockApi() = createMockApi(
    MockNetworkInterceptor()
        .mock(
            "http://localhost/android-version-features/27",
            Gson().toJson(mockVersionFeaturesOreo),
            200,
            1000
        )
        .mock(
            "http://localhost/android-version-features/28",
            Gson().toJson(mockVersionFeaturesPie),
            200,
            1000
        )
        .mock(
            "http://localhost/android-version-features/29",
            Gson().toJson(mockVersionFeaturesAndroid10),
            200,
            1000
        )
)