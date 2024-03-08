package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.horariovisita.data

import biz.belcorp.salesforce.core.entities.sql.horariovisita.HorarioVisitaEntity
import biz.belcorp.salesforce.core.entities.sql.horariovisita.HorarioVisitaEntity_Table
import com.raizlabs.android.dbflow.kotlinextensions.*

class HorarioVisitaLocalDataStore {
    fun obtenerHorarioVisita(): List<HorarioVisitaEntity> {
        val query = (select from HorarioVisitaEntity::class
            orderBy (HorarioVisitaEntity_Table.orden.asc()))
        return query.queryList()
    }
}
