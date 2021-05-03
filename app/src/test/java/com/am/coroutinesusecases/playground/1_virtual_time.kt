package com.am.coroutinesusecases.playground

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test

class SystemUnderTest {

    suspend fun functionWithDelay(): Int {
        delay(1000)
        return 42
    }
}

private fun CoroutineScope.functionThatStartsNewCoroutine() {
    launch {
        delay(1000)
        println("Coroutine completed!")
    }
}
class TestClass {

    @Test
    fun `functionWithDelay() should return 42`() = runBlockingTest {

        val realTimeStart = System.currentTimeMillis()
        val virtualTimeStart = currentTime

//        val actual = SystemUnderTest().functionWithDelay()
//        assertEquals(42, actual)

        functionThatStartsNewCoroutine()
        advanceTimeBy(1000)

        println("Test took ${System.currentTimeMillis() - realTimeStart} real ms")
        println("Test took ${currentTime - virtualTimeStart} virtual ms")
    }

}