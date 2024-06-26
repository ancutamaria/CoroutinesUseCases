package com.am.coroutinesusecases.usecases.coroutines.usecase10

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.am.coroutinesusecases.utils.MainCoroutineScopeRule
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class CalculationInBackgroundViewModelTest {

    @get:Rule
    val mainCoroutineScopeRule = MainCoroutineScopeRule()

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private val receivedUiStates = mutableListOf<UiState>()

    @Test
    fun `performCalculation() should perform correct calculations`() =
        mainCoroutineScopeRule.runBlockingTest {
            val viewModel =
                CalculationInBackgroundViewModel(mainCoroutineScopeRule.testDispacher)
                    .apply {
//                        observe is not reconized, have no idea why.
//                        will come pack to this at a later time
//                        observe()
                    }

            assertTrue(receivedUiStates.isEmpty())

            viewModel.performCalculation(1)

            assertEquals(
                UiState.Loading,
                receivedUiStates.first()
            )

            assertEquals(
                "1",
                (receivedUiStates[1] as UiState.Success).result
            )

        }
}