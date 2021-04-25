package com.am.coroutinesusecases.usecases.coroutines.usecase10

import androidx.lifecycle.viewModelScope
import com.am.coroutinesusecases.base.BaseViewModel
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.math.BigInteger
import kotlin.system.measureTimeMillis

class CalculationInBackgroundViewModel : BaseViewModel<UiState>() {

    fun performCalculation(factorialOf: Int) {
        uiState.value = UiState.Loading

        viewModelScope.launch {

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
    }

    private suspend fun calculateFactorialOf(number: Int) = withContext(Dispatchers.Default) {
        var factorial = BigInteger.ONE
        for (i in 1 .. number) {
            factorial = factorial.multiply(BigInteger.valueOf(i.toLong()))
        }
        factorial
    }

}