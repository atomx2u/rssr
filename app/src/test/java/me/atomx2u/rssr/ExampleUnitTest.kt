package me.atomx2u.rssr

import io.reactivex.Observable
import org.junit.Test

import org.junit.Assert.*



/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun rxJava() {
        Observable.just(1, 2, 3)
            .takeUntil { integer -> integer < 3 }
            .test()
            .assertResult(1, 2)
    }
}
