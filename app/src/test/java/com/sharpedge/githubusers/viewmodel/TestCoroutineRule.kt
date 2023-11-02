package com.sharpedge.githubusers.viewmodel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class TestCoroutineRule : TestWatcher() {
    @OptIn(ExperimentalCoroutinesApi::class)
    val testCoroutineDispatcher = TestCoroutineDispatcher()
    @OptIn(ExperimentalCoroutinesApi::class)
    val testCoroutineScope = TestCoroutineScope(testCoroutineDispatcher)

    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(testCoroutineDispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
        testCoroutineDispatcher.cleanupTestCoroutines()
    }
}
