package biz.belcorp.salesforce.modules.calculator.util

import io.reactivex.Completable

internal fun Completable.doAsync() {
    this.subscribe({},{})
}
