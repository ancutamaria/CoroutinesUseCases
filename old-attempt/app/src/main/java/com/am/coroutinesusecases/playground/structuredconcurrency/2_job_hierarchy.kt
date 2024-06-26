package com.am.coroutinesusecases.playground.structuredconcurrency

import kotlinx.coroutines.*

fun main() {

    val scopeJob = Job()
    val scope = CoroutineScope(Dispatchers.Default + scopeJob)

    var childCoroutineJob: Job? = null
    val passedJob = Job()
    // passing passedJob makes coroutineJob not be a child of scopeJob anymore - we create a new job hierarchy
    // this is why it is not recommended passing jobs in the context parameter to coroutine builders
    val coroutineJob = scope.launch(passedJob) {
        childCoroutineJob = launch {
            println("Starting child coroutine")
            delay(1000)
        }
        println("the coroutine was started")
        delay(1000)
    }

    println("is childCoroutineJob a child of coroutineJob? => ${coroutineJob.children.contains(childCoroutineJob)}")

    println("is passedJob a child of coroutineJob? => ${coroutineJob.children.contains(passedJob)}")

    println("passedJob and coroutineJob are refecences to the same job object: ${passedJob == coroutineJob}")

    println("is coroutineJob a child of scopeJob? => ${scopeJob.children.contains(coroutineJob)}")

    println("is childCoroutineJob a child of scopeJob? => ${scopeJob.children.contains(childCoroutineJob)}")

    println("is scopeJob a child of coroutineJob? => ${coroutineJob.children.contains(scopeJob)}")

    Thread.sleep(1100)


}