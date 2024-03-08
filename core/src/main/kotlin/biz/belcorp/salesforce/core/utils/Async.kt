package biz.belcorp.salesforce.core.utils

import android.os.Handler
import android.os.Looper
import java.lang.ref.WeakReference
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

fun <T> T.doAsync(
    exceptionHandler: ((Throwable) -> Unit)? = crashLogger,
    task: AsyncContext<T>.() -> Unit
): Future<Unit> {
    val context =
        AsyncContext(WeakReference(this))
    return BackgroundExecutor.submit {
        return@submit try {
            context.task()
        } catch (t: Throwable) {
            val result = exceptionHandler?.invoke(t)
            if (result != null) {
                result
            } else {
                Unit
            }
        }
    }
}

fun <T> AsyncContext<T>.uiThread(f: (T) -> Unit): Boolean {
    val ref = weakRef.get() ?: return false
    if (ContextHelper.mainThread == Thread.currentThread()) {
        f(ref)
    } else {
        ContextHelper.handler.post { f(ref) }
    }
    return true
}

private val crashLogger = { throwable: Throwable -> throwable.printStackTrace() }

class AsyncContext<T>(val weakRef: WeakReference<T>)

private object BackgroundExecutor {
    var executor: ExecutorService = Executors.newScheduledThreadPool(
        2 * Runtime.getRuntime().availableProcessors()
    )

    fun <T> submit(task: () -> T): Future<T> = executor.submit(task)
}

private object ContextHelper {
    val handler = Handler(Looper.getMainLooper())
    val mainThread: Thread = Looper.getMainLooper().thread
}
