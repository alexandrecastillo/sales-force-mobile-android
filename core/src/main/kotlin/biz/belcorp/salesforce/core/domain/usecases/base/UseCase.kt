package biz.belcorp.salesforce.core.domain.usecases.base

import androidx.core.util.Preconditions
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.utils.log
import io.reactivex.*
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

abstract class UseCase(
    val threadExecutor: ThreadExecutor,
    val postExecutionThread: PostExecutionThread
) {

    private val disposables: CompositeDisposable = CompositeDisposable()

    inline fun <reified T> execute(observable: Observable<T>, observer: DisposableObserver<T>) {
        Preconditions.checkNotNull(observer)
        val disposable = observable
            .log()
            .subscribeOn(Schedulers.from(threadExecutor))
            .observeOn(postExecutionThread.scheduler)
            .subscribeWith(observer)
        addDisposable(disposable)
    }

    inline fun <reified T> execute(single: Single<T>, observer: SingleObserver<T>) {
        Preconditions.checkNotNull(observer)
        single
            .log()
            .subscribeOn(threadExecutor.scheduler)
            .observeOn(postExecutionThread.scheduler)
            .subscribeWith(observer)
    }

    fun execute(completable: Completable, observer: CompletableObserver) {
        Preconditions.checkNotNull(observer)
        completable
            .log()
            .subscribeOn(Schedulers.from(threadExecutor))
            .observeOn(postExecutionThread.scheduler)
            .subscribeWith(observer)
    }

    fun dispose() {
        if (!disposables.isDisposed)
            disposables.dispose()
    }

    fun addDisposable(disposable: Disposable) {
        Preconditions.checkNotNull(disposable)
        Preconditions.checkNotNull(disposables)
        disposables.add(disposable)
    }

}
