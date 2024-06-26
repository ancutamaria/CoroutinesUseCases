package com.am.coroutinesusecases.playground.structuredconcurrency

import kotlinx.coroutines.*

fun main() {

    val scope = CoroutineScope(Job())

    scope.launch {

        doSomeTasks()

        val job3 = scope.launch {
            launch {
                println("Starting Task 3")
                delay(100)
                println("Task 3 completed")
            }
        }

//    make job 3 wait for the completion of job 1 and 2

//        variant 1:
//        val job1 = launch {
//            launch {
//                println("Starting Task 1")
//                delay(500)
//                println("Task 1 completed")
//            }
//        }
//        val job2 = launch {
//            launch {
//                println("Starting Task 2")
//                delay(300)
//                println("Task 2 completed")
//            }
//        }
//        job1.join()
//        job2.join()

//        variant 2
//        launch {
//            val job1 = launch {
//                launch {
//                    println("Starting Task 1")
//                    delay(500)
//                    println("Task 1 completed")
//                }
//            }
//            val job2 = launch {
//                launch {
//                    println("Starting Task 2")
//                    delay(300)
//                    println("Task 2 completed")
//                }
//            }
//        }.join()
//        val job3 = scope.launch {
//            launch {
//                println("Starting Task 3")
//                delay(100)
//                println("Task 3 completed")
//            }
//        }
//    }

//        variant 3
//        coroutineScope {
//            val job1 = launch {
//                launch {
//                    println("Starting Task 1")
//                    delay(500)
//                    println("Task 1 completed")
//                }
//            }
//            val job2 = launch {
//                launch {
//                    println("Starting Task 2")
//                    delay(300)
//                    println("Task 2 completed")
//                }
//            }
//        }
//        val job3 = scope.launch {
//            launch {
//                println("Starting Task 3")
//                delay(100)
//                println("Task 3 completed")
//            }
//        }
//    }

//        variant 4
//        failure of a coroutine won't affect it's siblings
//        supervisorScope {
//            val job1 = launch {
//                launch {
//                    println("Starting Task 1")
//                    delay(500)
//                    println("Task 1 completed")
//                }
//            }
//            val job2 = launch {
//                launch {
//                    println("Starting Task 2")
//                    delay(300)
//                    println("Task 2 completed")
//                }
//            }
//        }
//        val job3 = scope.launch {
//            launch {
//                println("Starting Task 3")
//                delay(100)
//                println("Task 3 completed")
//            }
//        }
    }

    Thread.sleep(1000)

}

// do we want to run these 2 coroutines concurrently with the other code that calls this suspend function?
// if yes, make doSomeTasks an extension of CoroutineScope
//fun CoroutineScope.doSomeTasks() {
//    val job1 = launch {
//        launch {
//            println("Starting Task 1")
//            delay(300)
//            println("Task 1 completed")
//        }
//    }
//    val job2 = launch {
//        launch {
//            println("Starting Task 2")
//            delay(500)
//            println("Task 2 completed")
//        }
//    }
//}

// if no, and we want the siblings to be cancelled when a coroutine is cancelled
//suspend fun doSomeTasks() = coroutineScope {
//    val job1 = launch {
//        launch {
//            println("Starting Task 1")
//            delay(500)
//            println("Task 1 completed")
//        }
//    }
//    val job2 = launch {
//        launch {
//            println("Starting Task 2")
//            delay(300)
//            println("Task 2 completed")
//        }
//    }
//}

// if no, and we want the siblings to be unaffected when a coroutine is cancelled
suspend fun doSomeTasks() = coroutineScope {
    val job1 = launch {
        launch {
            println("Starting Task 1")
            delay(500)
            println("Task 1 completed")
        }
    }
    val job2 = launch {
        launch {
            println("Starting Task 2")
            delay(300)
            println("Task 2 completed")
        }
    }
}