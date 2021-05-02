package com.am.coroutinesusecases.playground.exceptionhandling

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun main() {

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Caught $throwable in CoroutineExceptionHandler")
    }

//    versiunea 1 de rulat cu CoroutineExceptionHandler
//    val scope = CoroutineScope(Job() + exceptionHandler)
//
//    scope.launch {
//        throw RuntimeException()
//    }

//    versiunea 2 de rulat cu CoroutineExceptionHandler
        val scope = CoroutineScope(Job())

        scope.launch(exceptionHandler) {
            throw RuntimeException()
        }

//    passing the exceptionHandler to a child coroutine won't have any effect
//    scope.launch {
//        launch(exceptionHandler) {
//            throw RuntimeException()
//        }
//    }
    Thread.sleep(1000)
}