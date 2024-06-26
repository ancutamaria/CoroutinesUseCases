package com.am.coroutinesusecases.playground.structuredconcurrency

import kotlinx.coroutines.*
import java.lang.RuntimeException

fun main() {

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Caught exception $throwable")
    }
    // in this case, the scope and the sibling are being cancelled
//    val scope = CoroutineScope(Job() + exceptionHandler)
    // in this case, the scope and the sibling are not being cancelled
    // supervisorJob is used in viewModelScope and lifecycleScope
    val scope = CoroutineScope(SupervisorJob() + exceptionHandler)

    scope.launch {
        println("Coroutine 1 starts")
        delay(50)
        println("Coroutine 1 fails")
        throw RuntimeException()
    }

    scope.launch {
        println("Coroutine 2 starts")
        delay(500)
        println("Coroutine 2 completed")
    }.invokeOnCompletion {
        if (it is CancellationException)
            println("Coroutine 2 was cancelled")
    }

    Thread.sleep(1000)
    // is scope got cancelled, it means we can't start coroutines in it anymore
    println("Scope got cancelled: ${!scope.isActive} ")
}