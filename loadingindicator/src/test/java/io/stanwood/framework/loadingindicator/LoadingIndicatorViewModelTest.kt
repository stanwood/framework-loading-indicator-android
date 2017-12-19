package io.stanwood.framework.loadingindicator

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class LoadingIndicatorViewModelTest {

    private lateinit var viewModel: LoadingIndicatorViewModel

    @Before
    fun setup() {
        resetViewModel()
    }

    private fun resetViewModel() {
        viewModel = LoadingIndicatorViewModel(null)
    }

    @Test
    fun whenLoadingMessageIsSetThenShowIndicator() {
        viewModel.loadingMessage = "test"
        assert(viewModel.isVisible)
        assert(!viewModel.isError)
    }

    @Test
    fun whenLoadingMessageIsNotSetThenDoNotShowIndicator() {
        viewModel.loadingMessage = "test" // show indicator
        viewModel.loadingMessage = "" // hide indicator
        assert(!viewModel.isVisible)
        assert(!viewModel.isError)

        resetViewModel()
        viewModel.loadingMessage = "test" // show indicator
        viewModel.loadingMessage = null // hide indicator
        assert(!viewModel.isVisible)
        assert(!viewModel.isError)
    }

    @Test
    fun whenErrorMessageIsSetThenShowIndicator() {
        viewModel.errorMessage = "test"
        assert(viewModel.isVisible)
        assert(viewModel.isError)
    }

    @Test
    fun whenErrorMessageIsNotSetThenDoNotShowIndicator() {
        viewModel.errorMessage = "test" // show indicator
        viewModel.errorMessage = "" // hide indicator
        assert(!viewModel.isVisible)
        assert(!viewModel.isError)

        resetViewModel()
        viewModel.errorMessage = "test" // show indicator
        viewModel.errorMessage = null // hide indicator
        assert(!viewModel.isVisible)
        assert(!viewModel.isError)
    }

    @Test
    fun givenLoadingIndicatorIsShownWhenErrorStateIsSetThenShowErrorState() {
        viewModel.loadingMessage = "test"
        viewModel.errorMessage = "test"
        assert(viewModel.isError)
        assert(viewModel.isVisible)
    }

    @Test
    fun giveLoadingIndicatorIsShownWhenErrorStateIsResetThenShowLoadingIndicator() {
        viewModel.loadingMessage = "test"
        viewModel.errorMessage = null
        assert(!viewModel.isError)
        assert(viewModel.isVisible)

        resetViewModel()
        viewModel.loadingMessage = "test"
        viewModel.errorMessage = ""
        assert(!viewModel.isError)
        assert(viewModel.isVisible)
    }

    @Test
    fun givenErrorIndicatorIsShownWhenLoadingMessageIsSetThenShowLoadingIndicator() {
        viewModel.errorMessage = "test"
        viewModel.loadingMessage = "test"
        assert(viewModel.isVisible)
        assert(!viewModel.isError)
        assertEquals(null, viewModel.errorMessage)
    }

    @Test
    fun givenErrorIndicatorIsShownWhenLoadingMessageIsResetThenDoNotShowLoadingIndicator() {
        viewModel.errorMessage = "test"
        viewModel.loadingMessage = null
        assert(!viewModel.isVisible)
        assert(!viewModel.isError)

        resetViewModel()
        viewModel.errorMessage = "test"
        viewModel.loadingMessage = ""
        assert(!viewModel.isVisible)
        assert(!viewModel.isError)
    }

    @Test
    fun givenLoadingIndicatorIsShownThenTheMessageShouldBeTheLoadingMessage() {
        viewModel.loadingMessage = "test"
        assertEquals("test", viewModel.message)
    }

    @Test
    fun givenErrorIndicatorIsShownThenTheMessageShouldBeTheLoadingMessage() {
        viewModel.errorMessage = "test"
        assertEquals("test", viewModel.message)
    }
}