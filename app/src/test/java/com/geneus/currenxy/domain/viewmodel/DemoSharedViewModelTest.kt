package com.geneus.currenxy.domain.viewmodel

import app.cash.turbine.test
import com.geneus.currenxy.ui.DemoSharedViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class DemoSharedViewModelTest {

    @Test
    fun `initial search query is empty`() = runTest {
        val viewModel = DemoSharedViewModel()

        // Verify initial state of searchQuery
        viewModel.searchQuery.test {
            assertEquals("", awaitItem()) // Initial value should be an empty string
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `setSearchQuery updates the search query state`() = runTest {
        val viewModel = DemoSharedViewModel()

        // Update search query
        viewModel.setSearchQuery("new query")

        // Verify the updated state of searchQuery
        viewModel.searchQuery.test {
            assertEquals("new query", awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}
