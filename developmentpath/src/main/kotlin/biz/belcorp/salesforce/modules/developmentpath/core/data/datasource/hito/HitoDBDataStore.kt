package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.hito

import biz.belcorp.salesforce.core.entities.sql.eventos.CronogramaEventosEntity
import biz.belcorp.salesforce.core.entities.sql.eventos.CronogramaEventosEntity_Table
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.hito.HitoMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos.Hito
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos.HitoEnRegion
import com.raizlabs.android.dbflow.kotlinextensions.*

class HitoDBDataStore(private val hitoMapper: HitoMapper) {
    fun obtenerPorRegion(): List<HitoEnRegion> {
        val where = (select from CronogramaEventosEntity::class
            orderBy (CronogramaEventosEntity_Table.CodigoZona.asc()))

        return hitoMapper.parseEnRegion(where.queryList())
    }

    fun obtenerPorZona(codigoZona: String): List<Hito> {
        val where = (select from CronogramaEventosEntity::class
            where (CronogramaEventosEntity_Table.CodigoZona eq codigoZona))

        return hitoMapper.parse(where.queryList())
    }
}
