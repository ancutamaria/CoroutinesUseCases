package com.am.coroutinesusecases.usecases.coroutines.usecase2.rx

import com.am.coroutinesusecases.mock.VersionFeatures

sealed class UiState {
    object Loading : UiState()
    data class Success(
        val versionFeatures: VersionFeatures
    ) : UiState()

    data class Error(val message: String) : UiState()
}