package com.example.androidcodingchallenge.data.usecases

import com.example.androidcodingchallenge.data.mappers.CommentModelDataMapper
import com.example.androidcodingchallenge.data.models.CommentModel
import com.example.androidcodingchallenge.data.repositories.FeedItemsRepository
import com.example.androidcodingchallenge.domain.models.Comment
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
class GetPostCommentsTest {

    private val feedItemsRepository: FeedItemsRepository = mock()
    private val commentMapper = CommentModelDataMapper()

    private val getPostCommentsUseCase: GetPostComments by lazy {
        GetPostCommentsImpl(feedItemsRepository, commentMapper)
    }

    private val inputMock = listOf(
        Comment(22, 1, "title1", "email@t.com", "body1"),
        Comment(23, 2, "title2", "email@t.com", "body2")
    )
    private val outputMock = listOf(
        CommentModel(22, 1, "title1", "body1", -1),
        CommentModel(23, 2, "title2", "body2", -1),
    )

    @Test
    fun `test getPostComments should return empty list`() =
        runTest(UnconfinedTestDispatcher()) {
            feedItemsRepository.stub {
                onBlocking { getPostComments(any()) } doReturn emptyList()
            }

            val actual = getPostCommentsUseCase.call(mock())

            verify(feedItemsRepository).getPostComments(any())
            Assert.assertEquals(0, actual.size)
        }

    @Test
    fun `test getPostComments should return two items list`() =
        runTest(UnconfinedTestDispatcher()) {
            feedItemsRepository.stub {
                onBlocking { getPostComments(any()) } doReturn inputMock
            }

            val output = getPostCommentsUseCase.call(mock())

            verify(feedItemsRepository).getPostComments(any())
            Assert.assertEquals(2, output.size)
            Assert.assertArrayEquals(output.toTypedArray(), outputMock.toTypedArray())
        }
}