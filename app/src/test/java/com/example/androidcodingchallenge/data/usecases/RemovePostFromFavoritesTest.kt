package com.example.androidcodingchallenge.data.usecases

import com.example.androidcodingchallenge.data.FavoritePostsDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.*

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class RemovePostFromFavoritesTest {

    private val dataSource: FavoritePostsDataSource = mock()

    private val removePostFromFavorites: RemovePostFromFavorites by lazy {
        RemovePostFromFavoritesImpl(dataSource)
    }

    @Test
    fun `test removePostFromFavorites did not remove`() =
        runTest(UnconfinedTestDispatcher()) {
            dataSource.stub {
                onBlocking { remove(any()) } doReturn 0
            }

            val actual = removePostFromFavorites.call(mock())

            verify(dataSource).remove(any())
            Assert.assertFalse(actual)
        }

    @Test
    fun `test removePostFromFavorites successful remove`() =
        runTest(UnconfinedTestDispatcher()) {
            dataSource.stub {
                onBlocking { remove(any()) } doReturn 1
            }

            val actual = removePostFromFavorites.call(mock())

            verify(dataSource).remove(any())
            Assert.assertTrue(actual)
        }
}