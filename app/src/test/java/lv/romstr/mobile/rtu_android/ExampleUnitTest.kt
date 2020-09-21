package lv.romstr.mobile.rtu_android

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import kotlin.concurrent.thread


class ExampleUnitTest {

    @Test
    fun coroutines() {
        runBlocking {
            repeat(100_000) {
                launch {
                    delay(1000L)
                    print(".")
                }
            }
        }
    }

    @Test
    fun threads() {
        runBlocking {
            repeat(100_000) {
                thread {
                    Thread.sleep(1000L)
                    print(".")
                }
            }
        }
    }
}