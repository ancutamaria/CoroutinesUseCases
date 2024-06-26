package com.am.coroutinesusecases.usecases.coroutines.usecase11

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import timber.log.Timber
import java.math.BigInteger
import kotlin.system.measureTimeMillis

class CooperativeCancellationViewModel(
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) : ViewModel() {

    private var calculationJob: Job? = null

    fun performCalculation(factorialOf: Int) {
        uiState.value = UiState.Loading

        calculationJob = viewModelScope.launch {

            Timber.d("Coroutine context: $coroutineContext")

            var result: BigInteger
            val computationDuration = measureTimeMillis {
                result = calculateFactorialOf(factorialOf)
            }
            var resultString: String
            val stringConversionDuration = measureTimeMillis {
                resultString = withContext(Dispatchers.Default + CoroutineName("String conversion coroutine")) {
                    result.toString()
                }
            }
            uiState.value = UiState.Success(resultString, computationDuration, stringConversionDuration)
        }

        calculationJob?.invokeOnCompletion {
            if (it is CancellationException) {
                Timber.d("Calculation was cancelled!")
            }
        }
    }

    private suspend fun calculateFactorialOf(number: Int) = withContext(defaultDispatcher) {
        var factorial = BigInteger.ONE
        for (i in 1 .. number) {
            yield()
            factorial = factorial.multiply(BigInteger.valueOf(i.toLong()))
        }
        Timber.d("Calculating factorial of $number")
        factorial
    }

    fun cancelCalculation() {
        calculationJob?.cancel()
    }

    fun uiState(): LiveData<UiState> = uiState

    private val uiState: MutableLiveData<UiState> = MutableLiveData()
}