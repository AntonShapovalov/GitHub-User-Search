package bb.testask.githubusersearch

import org.junit.Test
import rx.Observable
import rx.schedulers.Schedulers
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/**
 * Simple test to try something
 */
class Simple {

    private val latch: CountDownLatch = CountDownLatch(1)
    private val dateFormat = SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault())

    @Test
    @Throws(Exception::class)
    fun test() {
        val delay = Observable.timer(2L, TimeUnit.SECONDS)
                .doOnSubscribe { printlnTime("delay start") }
                .map { "delay finish" }
                .doOnNext { printlnTime(it) }
        val work = Observable.just("user.dir")
                .doOnSubscribe { printlnTime("work start") }
                .map { File(System.getProperty(it)) }
                .doOnNext { println("directory name = ${it.name}, files count = ${it.walkTopDown().count()}") }
                .map { "work finish" }
                .doOnNext { printlnTime(it) }
        Observable.zip(delay, work, { _, _ -> "all finished" })
                .subscribeOn(Schedulers.io())
                .doOnUnsubscribe { latch.countDown() }
                .subscribe { println(it) }
        latch.await()
    }

    private fun printlnTime(s: String) {
        println("$s, ${dateFormat.format(System.currentTimeMillis())}")
    }

}