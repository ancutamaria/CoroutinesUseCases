package com.am.coroutinesusecases.playground.cancellation

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {

    val job = launch {
        repeat(10) { index ->
            println("Operation number $index")
//            delay(100)
            try {
                delay(100)
            } catch (e: CancellationException) {
                println("Operation number $index is cancelled")
                throw CancellationException()
            }
        }
    }

    delay(250)
    println("Cancelling coroutine")
    job.cancel()

}