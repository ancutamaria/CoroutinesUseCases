package com.am.coroutinesusecases.usecases.coroutines.usecase3

import com.am.coroutinesusecases.mock.VersionFeatures

sealed class UiState {
    object Loading : UiState()
    data class Success(
        val versionFeatures: List<VersionFeatures>
    ) : UiState()

    data class Error(val message: String) : UiState()
}