package chechetkin.yuri.vktestapp.core

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger

private const val SAVE_COUNT = 3

private val handler: Handler = Handler(Looper.getMainLooper())
private val group = ThreadGroup("async")
private val asyncThreadNumber = AtomicInteger(1)
private val cpuCount = Runtime.getRuntime().availableProcessors()
private val threads = cpuCount * 3 + SAVE_COUNT

private val fixedThreadPool = Executors.newFixedThreadPool(threads) {
    Thread(group, it, "async-${asyncThreadNumber.getAndIncrement()}")
}

fun async(block: () -> Unit) {
    fixedThreadPool.execute(block)
}

fun mainThread(block: () -> Unit) {
    if (handler.looper.thread == Thread.currentThread()) {
        block()
    } else {
        handler.post(block)
    }
}