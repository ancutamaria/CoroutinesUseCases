package com.am.coroutinesusecases.usecases.coroutines.usecase2

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.am.coroutinesusecases.mock.mockVersionFeaturesAndroid10
import com.am.coroutinesusecases.utils.MainCoroutineScopeRule
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.rules.TestRule

class Perform2SequentialNetworkRequestsViewModelTest {

    @get:Rule
    val mainCoroutineScopeRule = MainCoroutineScopeRule()

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private val receivedUiStates = mutableListOf<UiState>()

    @Test
    fun `should return Success when both network requests are successful`() {

        val fakeApi = FakeSuccessApi()
        val viewModel = Perform2SequentialNetworkRequestsViewModel(fakeApi)

        observeViewModel(viewModel)

        viewModel.perform2SequentialNetworkRequest()

        assertEquals(
            listOf(
                UiState.Loading,
                UiState.Success(mockVersionFeaturesAndroid10)
            ), receivedUiStates
        )
    }

    @Test
    fun `should return Error when the first network request fails`() {

        val fakeApi = FakeVersionsErrorApi()
        val viewModel = Perform2SequentialNetworkRequestsViewModel(fakeApi)

        observeViewModel(viewModel)

        viewModel.perform2SequentialNetworkRequest()

        assertEquals(
            listOf(
                UiState.Loading,
                UiState.Error("Network request failed")
            ), receivedUiStates
        )
    }

    @Test
    fun `should return Error when the second network request fails`() {

        val fakeApi = FakeFeaturesErrorApi()
        val viewModel = Perform2SequentialNetworkRequestsViewModel(fakeApi)

        observeViewModel(viewModel)

        viewModel.perform2SequentialNetworkRequest()

        assertEquals(
            listOf(
                UiState.Loading,
                UiState.Error("Network request failed")
            ), receivedUiStates
        )
    }


    private fun observeViewModel(viewModel: Perform2SequentialNetworkRequestsViewModel) {
        viewModel.uiState().observeForever { uiState ->
            uiState?.let {
                receivedUiStates.add(uiState)
            }
        }
    }
}