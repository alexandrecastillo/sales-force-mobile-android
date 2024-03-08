package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.ruta

import biz.belcorp.salesforce.core.entities.sql.plan.FechaNoHabilFacturacionEntity
import biz.belcorp.salesforce.core.utils.esFinDeSemana
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.toCalendar
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos.Feriado

class FechaNoHabilMapper {

    fun parse(modelo: FechaNoHabilFacturacionEntity): Feriado {
        val calendar = modelo.fecha.toCalendar()
            ?: throw Exception("No se puede parsear fecha no hábil")

        return Feriado(calendar)
    }

    fun parse(modelos: List<FechaNoHabilFacturacionEntity>): List<Feriado> {
        // Quitar sábados y domingos de 'fechas no hábiles'
        return modelos.map { parse(it) }.filter { !it.fecha.esFinDeSemana() }
    }
}
