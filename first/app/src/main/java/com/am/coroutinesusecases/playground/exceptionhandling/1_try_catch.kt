package com.am.coroutinesusecases.playground.exceptionhandling

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception
import java.lang.RuntimeException

fun main() {

    val scope = CoroutineScope(Job())

    scope.launch {
        try {
            launch {
                functionThatThrowsException()
            }
            functionThatThrowsException()
        } catch (e: Exception) {
            println("Caught $e")
        }
    }

    Thread.sleep(100)

//    functionThatThrowsException()

//    try {
//        functionThatThrowsException()
//    } catch (e: Exception) {
//        println("Exception caught $e")
//    }
}

private fun functionThatThrowsException() {
    throw RuntimeException()
}