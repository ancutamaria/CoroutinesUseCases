package com.am.coroutinesusecases.usecases.coroutines.usecase1

import com.google.gson.Gson
import com.am.coroutinesusecases.mock.createMockApi
import com.am.coroutinesusecases.mock.mockAndroidVersions
import com.am.coroutinesusecases.utils.MockNetworkInterceptor

fun mockApi() =
    createMockApi(
        MockNetworkInterceptor()
            .mock(
                "http://localhost/recent-android-versions",
                Gson().toJson(mockAndroidVersions),
//                    "smth is wrong on server side",
                200,
                1500
            )
    )