package com.am.coroutinesusecases.playground.structuredconcurrency

import kotlinx.coroutines.*

fun main() = runBlocking<Unit> {
    val scope = CoroutineScope(Dispatchers.Default)

    val parentCoroutineJob = scope.launch {
        launch {
            delay(1000)
            println("Child coroutine 1 has completed")
        }
        launch {
            delay(3000)
            println("Child coroutine 2 has completed")
        }
    }

    parentCoroutineJob.join()
    println("Parent coroutine has completed")
}