package com.am.coroutinesusecases.playground.coroutinebuilders

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val job = launch(start = CoroutineStart.LAZY) {
        networkRequest()
        println("result received")
    }
    delay(200)
    job.start()
//    job.join() // used to suspend the runBlocking coroutine and wait for the job to complete
    println("end of runBlocking")
}

suspend fun networkRequest(): String {
    delay(500)
    return "result"
}