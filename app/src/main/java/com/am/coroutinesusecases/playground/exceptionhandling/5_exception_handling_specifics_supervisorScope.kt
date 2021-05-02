package com.am.coroutinesusecases.playground.exceptionhandling

import kotlinx.coroutines.*
import java.lang.Exception
import java.lang.RuntimeException

// without runBlocking
fun main() {

    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("Caught exception: $throwable")
    }

    val scope = CoroutineScope(Job() + exceptionHandler)

    scope.launch {
        try {
            supervisorScope {
                launch {
                    println("CEH: ${coroutineContext[CoroutineExceptionHandler]}")
                    throw RuntimeException()
                }
            }
        } catch (e: Exception) {
            println("Caught $e")
        }
    }

    Thread.sleep(100)
}


// with runBlocking
//fun main() = runBlocking {
//
//    try {
//        doSmth_suspendStyle()
//    } catch (e: Exception) {
//        println("Caught $e")
//    }
//
//}

private suspend fun doSmth_suspendStyle() {

//    catches the exception
//    supervisorScope {
//        throw RuntimeException()
//    }

//    does not catch the exception
//    supervisorScope {
//        launch {
//            throw RuntimeException()
//        }
//    }

//    nothing is printed
//    supervisorScope {
//        async {
//            throw RuntimeException()
//        }
//    }

//    catches the exception
//    supervisorScope {
//        async {
//            throw RuntimeException()
//        }.await()
//    }

//    the exception will be propagated to the job hierarchy and it will crash
    supervisorScope {
        val deferred = async {
            throw RuntimeException()
        }
        launch {
            deferred.await()
        }
    }
}