package com.am.coroutinesusecases.playground.exceptionhandling

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.Exception
import java.lang.RuntimeException

fun main() = runBlocking<Unit> {

    try {
        doSmth_suspendStyle()
    } catch (e: Exception) {
        println("Caught $e")
    }

}

private suspend fun doSmth_suspendStyle() {
    coroutineScope {
        launch {
            throw RuntimeException()
        }
    }
}