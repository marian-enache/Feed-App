package com.example.androidcodingchallenge.data.usecases

import com.example.androidcodingchallenge.data.mappers.PhotoModelDataMapper
import com.example.androidcodingchallenge.data.models.PhotoModel
import com.example.androidcodingchallenge.data.repositories.FeedItemsRepository
import com.example.androidcodingchallenge.domain.models.Photo
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
class GetPhotosPositionsTest {

    private val feedItemsRepository: FeedItemsRepository = mock()
    private val photoMapper = PhotoModelDataMapper()

    private val getPhotosPositionsUseCase: GetPhotosPositions by lazy {
        GetPhotosPositionsImpl(feedItemsRepository, photoMapper)
    }

    private val inputMock = listOf(
        Photo(22, 1, "title1", "url1", "thumbnailUrl1"),
        Photo(23, 2, "title2", "url2", "thumbnailUrl2")
    )
    private val positions = listOf(2, 7)
    private val outputMock = listOf(
        PhotoModel(1, "title1", "url1", 2),
        PhotoModel(2, "title2", "url2", 7)
    )

    @Test
    fun `test getPhotosPositions should return empty list`() =
        runTest(UnconfinedTestDispatcher()) {
            feedItemsRepository.stub {
                onBlocking { getPhotos() } doReturn emptyList()
            }

            val actual = getPhotosPositionsUseCase.call(mock())

            verify(feedItemsRepository).getPhotos()
            Assert.assertEquals(0, actual.size)
    }

    @Test
    fun `test getPhotosPositions should return two items list`() =
        runTest(UnconfinedTestDispatcher()) {
            feedItemsRepository.stub {
                onBlocking { getPhotos() } doReturn inputMock
            }

            val output = getPhotosPositionsUseCase.call(positions)

            verify(feedItemsRepository).getPhotos()
            Assert.assertEquals(2, output.size)
            Assert.assertArrayEquals(output.toTypedArray(), outputMock.toTypedArray())
        }
}