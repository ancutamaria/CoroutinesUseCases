package com.am.coroutinesusecases.usecases.coroutines.usecase3

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.am.coroutinesusecases.mock.mockVersionFeaturesAndroid10
import com.am.coroutinesusecases.mock.mockVersionFeaturesOreo
import com.am.coroutinesusecases.mock.mockVersionFeaturesPie
import com.am.coroutinesusecases.utils.MainCoroutineScopeRule
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class PerformNetworkRequestsConcurrentlyViewModelTest {

    @get:Rule
    val mainCoroutineScopeRule = MainCoroutineScopeRule()

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private val receivedUiStates = mutableListOf<UiState>()

    @Test
    fun `performNetworkRequestsSequentially() should load data sequentially`() =
        mainCoroutineScopeRule.runBlockingTest {
            val responseDelay = 1000L
            val fakeApi = FakeSuccessApi(responseDelay)
            val viewModel = PerformNetworkRequestsConcurrentlyViewModel(fakeApi)

            observeViewModel(viewModel)

            viewModel.performNetworkRequestsSequentially()
            val forwardedTime = advanceUntilIdle()

            assertEquals(
                listOf(
                    UiState.Loading,
                    UiState.Success(
                        listOf(
                            mockVersionFeaturesOreo,
                            mockVersionFeaturesPie,
                            mockVersionFeaturesAndroid10
                        )
                    )
                ), receivedUiStates
            )
            assertEquals(3000, forwardedTime)
    }

    @Test
    fun `performNetworkRequestsConcurrently() should load data concurrently`()
        = mainCoroutineScopeRule.runBlockingTest{
            val responseDelay = 1000L
            val fakeApi = FakeSuccessApi(responseDelay)
            val viewModel = PerformNetworkRequestsConcurrentlyViewModel(fakeApi)

            observeViewModel(viewModel)

            viewModel.performNetworkRequestsConcurrently()
            val forwardedTime = advanceUntilIdle()

            assertEquals(
                listOf(
                    UiState.Loading,
                    UiState.Success(
                        listOf(
                            mockVersionFeaturesOreo,
                            mockVersionFeaturesPie,
                            mockVersionFeaturesAndroid10
                        )
                    )
                ), receivedUiStates
            )
            assertEquals(1000, forwardedTime)
    }

    private fun observeViewModel(viewModel: PerformNetworkRequestsConcurrentlyViewModel) {
        viewModel.uiState().observeForever { uiState ->
            uiState?.let {
                receivedUiStates.add(uiState)
            }
        }
    }
}