package biz.belcorp.salesforce.modules.consultants.core.data.repository.filtros

import biz.belcorp.salesforce.core.entities.sql.filtros.TipoSaldoEntity
import com.raizlabs.android.dbflow.kotlinextensions.from
import com.raizlabs.android.dbflow.kotlinextensions.select
import io.reactivex.Observable

class TipoSaldoDBDataStore {

    val all: Observable<List<TipoSaldoEntity>>
        get() = Observable.create { subscriber ->

            try {
                val list = (select from TipoSaldoEntity::class).queryList()

                subscriber.onNext(list)
                subscriber.onComplete()

            } catch (e: Exception) {
                subscriber.onError(Exception(e.cause ?: Throwable(e.message ?: "")))
            }

        }
}
