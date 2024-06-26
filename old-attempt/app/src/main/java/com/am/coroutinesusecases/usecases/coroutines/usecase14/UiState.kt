package com.am.coroutinesusecases.usecases.coroutines.usecase14

import com.am.coroutinesusecases.mock.AndroidVersion

sealed class UiState {
    sealed class Loading : UiState() {
        object LoadFromDb : Loading()
        object LoadFromNetwork : Loading()
    }

    data class Success(val dataSource: DataSource, val recentVersions: List<AndroidVersion>) :
        UiState()

    data class Error(val dataSource: DataSource, val message: String) : UiState()
}