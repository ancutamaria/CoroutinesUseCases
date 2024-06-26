package com.am.coroutinesusecases.usecases.coroutines.usecase14

import com.am.coroutinesusecases.utils.MainCoroutineScopeRule
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule

class AndroidVersionRepositoryTest {

    @get:Rule
    val mainCoroutineScopeRule = MainCoroutineScopeRule()

    @Test
    fun `loadRecentAndroidVersions() should continue to load and store android versions when cakku=ing scope gets cancelled`() {

        mainCoroutineScopeRule.runBlockingTest {

            val fakeDatabase = FakeDatabase()
            val fakeApi = FakeApi()
            val repository = AndroidVersionRepository(
                fakeDatabase,
                mainCoroutineScopeRule,
                fakeApi
            )

            val testViewModelScope = TestCoroutineScope((SupervisorJob()))
            testViewModelScope.launch {
                repository.loadAndStoreRemoteAndroidVersions()
                fail("Scope should be cancelled before versions are loaded")
            }
            testViewModelScope.cancel()
            advanceUntilIdle()

            assertTrue(fakeDatabase.insertedIntoDb)

        }
    }
}