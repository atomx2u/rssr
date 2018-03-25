package me.atomx2u.rssr.domain.interactor.feed

import io.reactivex.Completable
import io.reactivex.observers.TestObserver
import io.reactivex.subscribers.TestSubscriber
import me.atomx2u.rssr.domain.repository.Repository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AddFeedUseCaseTest {

    @Mock
    private lateinit var repository: Repository
    private lateinit var addFeedUseCase: AddFeedUseCase
    private lateinit var testObserver: TestObserver<Any>

    @Before
    fun setUp() {
        addFeedUseCase = AddFeedUseCase(repository)
        testObserver = TestObserver<Any>()
    }

    @Test
    fun shouldCreateNewFeedByUrl() {
        Mockito.`when`(repository.insertFeed(Mockito.anyString())).thenReturn(Completable.complete())

        addFeedUseCase.execute(AddFeedUseCase.Request(TEST_URL)).subscribe(testObserver)

        Mockito.verify(repository, Mockito.times(1)).insertFeed(TEST_URL)
        Mockito.verifyNoMoreInteractions(repository)

        testObserver.assertComplete()
    }

    companion object {
        private const val TEST_URL = "bilibili.tv"
    }
}