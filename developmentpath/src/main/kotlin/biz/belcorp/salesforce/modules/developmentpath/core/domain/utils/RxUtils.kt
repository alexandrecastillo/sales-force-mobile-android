package biz.belcorp.salesforce.modules.developmentpath.core.domain.utils

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.functions.BiFunction

internal fun <K, J, L, I> Single<K>.doInParallelWithResult(
    function1: (K) -> Single<J>,
    function2: (K) -> Single<L>,
    transform: (J, L) -> Single<I>
): Single<I> {
    return flatMap { function1(it).zipWith(function2(it), BiFunction { j: J, l: L -> Pair(j, l) }) }
        .flatMap { transform(it.first, it.second) }
}

internal fun <K, J> Single<K>.doInParallel(single: Single<J>): Single<Pair<K, J>> {
    return this.zipWith(single, BiFunction { t1: K, t2: J -> Pair(t1, t2) })
}

internal fun <T> Single<T>.flatMapOnError(single: () -> Single<T>): Single<T> {
    return onErrorResumeNext { single.invoke() }
}

internal fun Completable.doAsync() = this.subscribe({}, {})

internal fun <T> Single<T>.doAsync() = this.subscribe({}, {})

