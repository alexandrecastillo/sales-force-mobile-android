package biz.belcorp.salesforce.core.utils

import io.reactivex.Completable
import io.reactivex.Single

fun doOnCompletable(function: () -> Unit): Completable {
    return Completable.create {
        function.invoke()
        it.onComplete()
    }
}

fun <T> doOnSingle(function: () -> T): Single<T> {
    return Single.create {
        val value = function.invoke()
        it.onSuccess(value)
    }
}
