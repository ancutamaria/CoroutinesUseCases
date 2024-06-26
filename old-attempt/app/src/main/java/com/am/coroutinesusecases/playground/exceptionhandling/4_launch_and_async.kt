package com.am.coroutinesusecases.playground.exceptionhandling

import kotlinx.coroutines.*
import java.lang.RuntimeException

fun main() {

//    when we have async in launch: a nested async will propagate the outcome to the launch parent and the error is printed in the logs
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("Caught exception: $throwable")
    }
    val scope = CoroutineScope(Job() + exceptionHandler)
    scope.launch {
        async {
            delay(200)
            throw RuntimeException()
        }
    }

//    when we have async in async: the coroutine is cancelled and nothing is printed in the logs
//    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
//        println("Caught exception: $throwable")
//    }
//    val scope = CoroutineScope(Job() + exceptionHandler)
//    scope.async {
//        async {
//            delay(200)
//            throw RuntimeException()
//        }
//    }

//  with async: if used like this, the coroutine is cancelled and nothing is printed in the logs
//    val scope = CoroutineScope(Job())
//    scope.async {
//        delay(200)
//        throw RuntimeException()
//    }

//    with async, if used like this, the error is printed in the logs
//    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
//        println("Caught exception: $throwable")
//    }
//    val scope = CoroutineScope(Job() + exceptionHandler)
//    val deferred = scope.async {
//        delay(200)
//        throw RuntimeException()
//    }
//    scope.launch {
//        deferred.await()
//    }

//    with launch: Exception in thread "DefaultDispatcher-worker-1" java.lang.RuntimeException
//    val scope = CoroutineScope(Job())
//    scope.launch {
//        delay(200)
//        throw RuntimeException()
//    }

    Thread.sleep(500)
}