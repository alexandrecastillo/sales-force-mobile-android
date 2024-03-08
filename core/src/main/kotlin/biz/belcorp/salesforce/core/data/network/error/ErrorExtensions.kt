package biz.belcorp.salesforce.core.data.network.error


import io.reactivex.Completable
import io.reactivex.Single


fun <T> Single<T>.capturarError(): Single<T> {
    return onErrorResumeNext { Single.error(it.mapearError()) }
}

fun Completable.capturarError(): Completable {
    return onErrorResumeNext { Completable.error(it.mapearError()) }
}

fun Throwable.mapearError(): Throwable {
    val errorFactory = ErrorRetrofitFactory(this)
    return errorFactory.crearError()
}
