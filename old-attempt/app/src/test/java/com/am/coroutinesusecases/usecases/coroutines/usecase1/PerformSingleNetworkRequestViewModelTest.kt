package com.am.coroutinesusecases.usecases.coroutines.usecase1

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.am.coroutinesusecases.mock.mockAndroidVersions
import com.am.coroutinesusecases.utils.MainCoroutineScopeRule
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class PerformSingleNetworkRequestViewModelTest {

    @get:Rule
    val mainCoroutineScopeRule = MainCoroutineScopeRule()

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()
    private val receivedUiStates = mutableListOf<UiState>()

    @Test
    fun `should return Success when network request is successful`() {

        // arrange
        val fakeApi = FakeSuccessApi()
        val viewModel = PerformSingleNetworkRequestViewModel(fakeApi)

        observeViewModel(viewModel)

        // act
        viewModel.performSingleNetworkRequest()

        // assert
        assertEquals(
                listOf(
                        UiState.Loading,
                        UiState.Success(mockAndroidVersions)
                ), receivedUiStates
        )
    }

    @Test
    fun `should return Error when network request fails`() {
        val fakeApi = FakeErrorApi()
        val viewModel = PerformSingleNetworkRequestViewModel(fakeApi)

        observeViewModel(viewModel)

        viewModel.performSingleNetworkRequest()

        assertEquals(
                listOf(
                        UiState.Loading,
                        UiState.Error("Network request failed")
                ), receivedUiStates
        )

    }


    private fun observeViewModel(viewModel: PerformSingleNetworkRequestViewModel) {
        viewModel.uiState().observeForever { uiState ->
            uiState?.let {
                receivedUiStates.add(uiState)
            }
        }
    }
}