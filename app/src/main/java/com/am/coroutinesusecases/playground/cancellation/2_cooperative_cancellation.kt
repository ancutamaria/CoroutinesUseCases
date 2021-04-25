package com.am.coroutinesusecases.playground.cancellation

import kotlinx.coroutines.*

fun main() = runBlocking {

    val job = launch(Dispatchers.Default) {
        repeat(10) { index ->
//            make the coroutine cooperative with cancellation. without one of these,
//            the job continues after cancellation... Section 10 -> cooperative cancellation
//            option 1
//            ensureActive() // makes the coroutine cancellable
//            option 2
//            yield() // the purpose of this, is to give other coroutines a chance to run; makes the coroutine cancellable
//            option 3: the benefit of this option is that it doesn't immediately throw a cancellationException so
//            we can perform some cleanup operations before the coroutine finally shuts down
            if (isActive) {
                println("Operation number $index")
                Thread.sleep(100)
            } else {
//                return@launch
//                or
                withContext(NonCancellable) {
                    delay(100)
                    println("Cleaning up operation number $index")
                    throw CancellationException()
                }
            }
        }
    }

    delay(250)
    println("Cancelling coroutine")
    job.cancel()
}