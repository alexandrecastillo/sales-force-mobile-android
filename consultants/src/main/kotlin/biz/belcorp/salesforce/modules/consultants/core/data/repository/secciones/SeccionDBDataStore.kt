package biz.belcorp.salesforce.modules.consultants.core.data.repository.secciones

import biz.belcorp.salesforce.core.entities.sql.ua.SeccionEntity
import biz.belcorp.salesforce.core.entities.sql.ua.SeccionEntity_Table
import com.raizlabs.android.dbflow.kotlinextensions.from
import com.raizlabs.android.dbflow.kotlinextensions.select
import io.reactivex.Observable

class SeccionDBDataStore {

    val all: Observable<List<SeccionEntity>>
        get() = Observable.create { subscriber ->

            val list = (select from SeccionEntity::class).orderBy(SeccionEntity_Table.Codigo, true).queryList()

            subscriber.onNext(list)
            subscriber.onComplete()

        }

}
