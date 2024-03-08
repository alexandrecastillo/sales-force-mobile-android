package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.observaciones

import biz.belcorp.salesforce.core.entities.sql.plan.ObservacionVisitaRDDEntity
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.observaciones.ObservacionVisitaMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.observaciones.ObservacionVisita
import com.raizlabs.android.dbflow.kotlinextensions.from
import com.raizlabs.android.dbflow.kotlinextensions.select

class ObservacionVisitaDBDataStore(private val mapper: ObservacionVisitaMapper) {
    fun obtener(): List<ObservacionVisita> {
        val where = (select from ObservacionVisitaRDDEntity::class)
        return mapper.parse(where.queryList())
    }
}
