package com.am.coroutinesusecases.playground.structuredconcurrency

import kotlinx.coroutines.*

fun main() =  runBlocking {

    val scope = CoroutineScope(Dispatchers.Default)

    scope.coroutineContext[Job]!!.invokeOnCompletion {
        if (it is CancellationException) {
            println("Coroutine parent was cancelled")
        }
    }

    val job2 = scope.launch {
        delay(1000)
        println("Coroutine 1 has completed")
    }
    job2.invokeOnCompletion {
        if (it is CancellationException) {
            println("Coroutine 1 was cancelled")

        }
    }
    val job3 = scope. launch {
        delay(3000)
        println("Coroutine 2 has completed")
    }.invokeOnCompletion {
        if (it is CancellationException) {
            println("Coroutine 2 was cancelled")

        }
    }

    //calling cancel here does not wait until all coroutines got cancelled, so if no thread sleep exists, nothing will be printed
//    scope.cancel()
// fixes the above comment
//    scope.coroutineContext[Job]!!.join()
    // or
//    scope.coroutineContext[Job]!!.cancelAndJoin()

    delay(200)
    job2.cancelAndJoin()
}