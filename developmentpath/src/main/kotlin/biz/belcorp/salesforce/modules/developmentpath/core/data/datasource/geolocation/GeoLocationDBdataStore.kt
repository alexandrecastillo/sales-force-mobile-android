package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.geolocation

import biz.belcorp.salesforce.core.entities.sql.consultora.ConsultoraEntity
import biz.belcorp.salesforce.core.entities.sql.consultora.ConsultoraEntity_Table
import com.raizlabs.android.dbflow.sql.language.Select
import io.reactivex.Completable

class GeoLocationDBdataStore {

    fun saveGeolocation(codigo: String, latitud: String, longitud: String): Completable {
        return Completable.create { subscriber ->

            val entity = Select().from(ConsultoraEntity::class.java)
                    .where(ConsultoraEntity_Table.Codigo.like("%$codigo%"))
                    .querySingle()

            entity?.longitud = longitud
            entity?.latitud = latitud

            entity?.update()

            subscriber.onComplete()
        }
    }

}
