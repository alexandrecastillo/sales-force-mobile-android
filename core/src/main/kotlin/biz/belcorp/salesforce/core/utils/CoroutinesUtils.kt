package biz.belcorp.salesforce.core.utils

import io.reactivex.Completable
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

fun delay(timeInMillis: Long, block: suspend () -> Unit) {
    GlobalScope.launch(Dispatchers.Main) {
        delay(timeInMillis)
        block.invoke()
    }
}

fun <T> CoroutineScope.lazyDeferred(
    coroutineContext: CoroutineContext = Dispatchers.IO,
    block: suspend CoroutineScope.() -> T
): Lazy<Deferred<T>> {
    return lazy {
        async(context = coroutineContext, start = CoroutineStart.LAZY) {
            block.invoke(this)
        }
    }
}

suspend fun CoroutineScope.io(f: suspend () -> Unit) {
    withContext(Dispatchers.IO) { f.invoke() }
}

suspend fun CoroutineScope.ui(f: suspend () -> Unit) {
    withContext(Dispatchers.Main) { f.invoke() }
}

fun Any.ui(f: suspend () -> Unit) {
    GlobalScope.launch(Dispatchers.Main) {
        f.invoke()
    }
}

fun Any.io(f: suspend () -> Unit) {
    GlobalScope.launch(Dispatchers.IO) {
        f.invoke()
    }
}

fun execCompletableCoroutine(f: suspend () -> Unit): Completable {
    return Completable.create { emitter ->
        GlobalScope.launch {
            try {
                f.invoke()
                emitter.onComplete()
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }
    }
}
