package biz.belcorp.salesforce.modules.consultants.core.data.repository.location.data

import biz.belcorp.salesforce.core.entities.sql.consultora.ConsultoraEntity
import biz.belcorp.salesforce.core.entities.sql.consultora.ConsultoraEntity_Table
import com.raizlabs.android.dbflow.sql.language.Select

class GeoLocationDataStore {

    fun saveGeoLocation(consultantCode: String, latitude: String, longitude: String) {
        Select().from(ConsultoraEntity::class.java)
            .where(ConsultoraEntity_Table.Codigo.like("%$consultantCode%"))
            .querySingle()
            ?.apply {
                longitud = longitude
                latitud = latitude
                update()
            }
    }
}
