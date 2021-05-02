package com.am.coroutinesusecases.playground.exceptionhandling

import kotlinx.coroutines.*
import java.lang.Exception
import java.lang.RuntimeException

fun main() {

    val scope = CoroutineScope(Job())

//    with CoroutineExceptionHandler
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("Caught exception: $throwable")
    }
    scope.launch(exceptionHandler) {
        launch {
            println("Starting coroutine 1")
            delay(100)
            throw RuntimeException()
        }

        launch {
            println("Starting coroutine 2")
            delay(3000)
            println("Coroutine 2 completed")
        }
    }

//    with try-catch
//    scope.launch {
//        launch {
//            println("Starting coroutine 1")
//            delay(100)
//            try {
//                throw RuntimeException()
//            } catch(e: Exception) {
//                println("Caught exception: $e")
//            }
//        }
//
//        launch {
//            println("Starting coroutine 2")
//            delay(3000)
//            println("Coroutine 2 completed")
//        }
//    }

    Thread.sleep(5000)
}