package biz.belcorp.salesforce.core.utils

import android.util.Log
import io.reactivex.*

inline fun <reified T> printEvent(tag: String, success: T?, error: Throwable?) =
    when {
        success == null && error == null -> Log.d(tag, "Complete") /* Only with Maybe */
        success != null -> Log.d(tag, "Success $success")
        error != null -> Log.d(tag, "Error $error")
        else -> -1 /* Cannot happen*/
    }

fun printEvent(tag: String, error: Throwable?) =
    when {
        error != null -> Log.d(tag, "Error $error")
        else -> Log.d(tag, "Complete")
    }

/**
 * Example usage of [log]:
Single.timer(1, TimeUnit.SECONDS)
.log()
.subscribe({ }, { })
 */

fun tag() =
    Thread.currentThread().stackTrace
        .firstOrNull { it?.fileName?.endsWith(".kt") ?: false }
        ?.let { stack -> "${stack.fileName.removeSuffix(".kt")}::${stack.methodName}:${stack.lineNumber}" }
        .orEmpty()

inline fun <reified T> Single<T>.log(): Single<T> {
    val tag = tag()
    return doOnEvent { success, error -> printEvent(tag, success, error) }
        .doOnSubscribe { Log.d(tag, "Subscribe") }
        .doOnDispose { Log.d(tag, "Dispose") }
}

inline fun <reified T> Maybe<T>.log(): Maybe<T> {
    val tag = tag()
    return doOnEvent { success, error -> printEvent(tag, success, error) }
        .doOnSubscribe { Log.d(tag, "Subscribe") }
        .doOnDispose { Log.d(tag, "Dispose") }
}

fun Completable.log(): Completable {
    val tag = tag()
    return doOnEvent { printEvent(tag, it) }
        .doOnSubscribe { Log.d(tag, "Subscribe") }
        .doOnDispose { Log.d(tag, "Dispose") }
}

inline fun <reified T> Observable<T>.log(): Observable<T> {
    val line = tag()
    return doOnEach { Log.d(line, "Each $it") }
        .doOnSubscribe { Log.d(line, "Subscribe") }
        .doOnDispose { Log.d(line, "Dispose") }
}

inline fun <reified T> Flowable<T>.log(): Flowable<T> {
    val line = tag()
    return doOnEach { Log.d(line, "Each $it") }
        .doOnSubscribe { Log.d(line, "Subscribe") }
        .doOnCancel { Log.d(line, "Cancel") }
}
