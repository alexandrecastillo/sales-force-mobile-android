package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.focos.data.dashboard

import biz.belcorp.salesforce.core.entities.sql.habilidades.HabilidadEntity
import com.raizlabs.android.dbflow.kotlinextensions.from
import com.raizlabs.android.dbflow.kotlinextensions.select

class HabilidadesDbDataStore {
    fun obtenerModelosHabilidad(): MutableList<HabilidadEntity> {
        return (select from HabilidadEntity::class).queryList()
    }
}
