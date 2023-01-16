package com.example.androidcodingchallenge.data.usecases

import com.example.androidcodingchallenge.data.mappers.PostModelDataMapper
import com.example.androidcodingchallenge.data.models.PostModel
import com.example.androidcodingchallenge.data.repositories.FeedItemsRepository
import com.example.androidcodingchallenge.domain.models.Post
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
class GetPostsTest {

    private val feedItemsRepository: FeedItemsRepository = mock()
    private val postMapper = PostModelDataMapper()

    private val getPostsUseCase: GetPosts by lazy {
        GetPostsImpl(feedItemsRepository, postMapper)
    }

    private val inputMock = listOf(
        Post(22, 1, "title1", "body1"),
        Post(23, 2, "title2", "body2")
    )
    private val outputMock = listOf(
        PostModel(1, "title1", "body1"),
        PostModel(2, "title2", "body2")
    )

    @Test
    fun `test getPosts should return empty list`() =
        runTest(UnconfinedTestDispatcher()) {
            feedItemsRepository.stub {
                onBlocking { getPosts() } doReturn emptyList()
            }

            val actual = getPostsUseCase.call()

            verify(feedItemsRepository).getPosts()
            Assert.assertEquals(0, actual.size)
        }

    @Test
    fun `test getPosts should return two item list`() =
        runTest(UnconfinedTestDispatcher()) {
            feedItemsRepository.stub {
                onBlocking { getPosts() } doReturn inputMock
            }

            val output = getPostsUseCase.call()

            verify(feedItemsRepository).getPosts()
            Assert.assertEquals(2, output.size)
            Assert.assertArrayEquals(output.toTypedArray(), outputMock.toTypedArray())
        }
}