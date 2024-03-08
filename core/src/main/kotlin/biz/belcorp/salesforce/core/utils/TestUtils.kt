package biz.belcorp.salesforce.core.utils

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import biz.belcorp.salesforce.core.constants.Constant
import java.io.IOException
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

private const val DEFAULT_TIME = Constant.NUMBER_THREE.toLong()
private const val DEFAULT_COUNT = Constant.NUMERO_UNO

@Throws(IOException::class)
fun Any.readString(path: String): String {
    val stream = this::class.java.classLoader?.getResourceAsStream(path)
    val s = stream?.let { Scanner(it).useDelimiter("\\A") } ?: return Constant.EMPTY_STRING
    val result = if (s.hasNext()) s.next() else Constant.EMPTY_STRING
    stream.close()
    return result
}

@Suppress("UNCHECKED_CAST")
@VisibleForTesting
fun <T> LiveData<T>.getOrAwaitValue(
    time: Long = DEFAULT_TIME,
    timeUnit: TimeUnit = TimeUnit.SECONDS
): T {
    var data: T? = null
    val latch = CountDownLatch(DEFAULT_COUNT)
    val observer = object : Observer<T> {
        override fun onChanged(o: T?) {
            data = o
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
        }
    }
    this.observeForever(observer)

    if (!latch.await(time, timeUnit)) {
        throw TimeoutException("Value was never set")
    }
    return data as T
}
