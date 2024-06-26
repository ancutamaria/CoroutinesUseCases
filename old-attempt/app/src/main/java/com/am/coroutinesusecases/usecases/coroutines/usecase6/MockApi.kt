package com.am.coroutinesusecases.usecases.coroutines.usecase6

import com.google.gson.Gson
import com.am.coroutinesusecases.mock.createMockApi
import com.am.coroutinesusecases.mock.mockAndroidVersions
import com.am.coroutinesusecases.utils.MockNetworkInterceptor

fun mockApi() = createMockApi(
    MockNetworkInterceptor()
        .mock(
            "http://localhost/recent-android-versions",
            "something went wrong on server side",
            500,
            1000,
            persist = false
        ).mock(
            "http://localhost/recent-android-versions",
            "something went wrong on server side",
            500,
            1000,
            persist = false
        ).mock(
            "http://localhost/recent-android-versions",
            Gson().toJson(mockAndroidVersions),
            200,
            1000
        )
)