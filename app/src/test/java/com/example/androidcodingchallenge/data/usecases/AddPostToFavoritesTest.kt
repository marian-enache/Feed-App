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
class AddPostToFavoritesTest {

    private val dataSource: FavoritePostsDataSource = mock()

    private val addPostToFavorites: AddPostToFavorites by lazy {
        AddPostToFavoritesImpl(dataSource)
    }

    @Test
    fun `test addPostToFavorites did not add`() =
        runTest(UnconfinedTestDispatcher()) {
            dataSource.stub {
                onBlocking { add(any()) } doReturn 0
            }

            val actual = addPostToFavorites.call(mock())

            verify(dataSource).add(any())
            Assert.assertFalse(actual)
        }

    @Test
    fun `test addPostToFavorites successful add`() =
        runTest(UnconfinedTestDispatcher()) {
            dataSource.stub {
                onBlocking { add(any()) } doReturn 1
            }

            val actual = addPostToFavorites.call(mock())

            verify(dataSource).add(any())
            Assert.assertTrue(actual)
        }
}