package com.am.coroutinesusecases.playground.structuredconcurrency

import kotlinx.coroutines.*

val scope = CoroutineScope(Dispatchers.Default)

fun main() = runBlocking {
    val job = scope.launch {
        delay(100)
        println("Coroutine completed")
    }
    job.invokeOnCompletion { throwable ->
        if (throwable is CancellationException) {
            println("Coroutine was cancelled!")
        }
    }

    delay(50)
    onDestroy()
}

fun onDestroy() {
    println("lifetime of the scope ends")
    scope.cancel()
}