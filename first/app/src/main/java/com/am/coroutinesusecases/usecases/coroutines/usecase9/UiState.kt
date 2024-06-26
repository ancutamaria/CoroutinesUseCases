package com.am.coroutinesusecases.usecases.coroutines.usecase9

import com.am.coroutinesusecases.mock.AndroidVersion

sealed class UiState {
    object Loading : UiState()
    data class Success(val recentVersions: List<AndroidVersion>) : UiState()
    data class Error(val message: String) : UiState()
}