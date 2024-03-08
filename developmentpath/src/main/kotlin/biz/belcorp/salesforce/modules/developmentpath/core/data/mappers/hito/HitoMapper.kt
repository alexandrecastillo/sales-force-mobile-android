package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.hito

import biz.belcorp.salesforce.core.entities.sql.eventos.CronogramaEventosEntity
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.toCalendar
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos.Hito
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos.HitoEnRegion

class HitoMapper{

    fun parse(modelo: CronogramaEventosEntity): Hito {
        val calendar = modelo.fechaInicio.toCalendar()
            ?: throw Exception("No se puede parsear la fecha del evento")

        return Hito(titulo = modelo.nombreActividad,
            fecha = calendar)
    }

    private fun parseEnRegion(modelo: CronogramaEventosEntity): HitoEnRegion {
        val calendar = modelo.fechaInicio.toCalendar()
            ?: throw Exception("No se puede parsear la fecha del evento")

        return HitoEnRegion(titulo = modelo.nombreActividad,
            fecha = calendar,
            codigoZona = modelo.codigoZona)
    }

    fun parse(modelos: List<CronogramaEventosEntity>): List<Hito> {
        return modelos.map { parse(it) }
    }

    fun parseEnRegion(modelos: List<CronogramaEventosEntity>): List<HitoEnRegion> {
        return modelos.map { parseEnRegion(it) }
    }
}
