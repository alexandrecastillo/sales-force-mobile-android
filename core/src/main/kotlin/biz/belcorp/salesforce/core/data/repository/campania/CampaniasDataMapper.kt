package biz.belcorp.salesforce.core.data.repository.campania

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.entities.sql.campania.CampaniaUaEntity
import biz.belcorp.salesforce.core.utils.toFullDate
import java.util.*


class CampaniasDataMapper {

    fun parse(entidad: CampaniaUaEntity): Campania {
        return Campania(
            codigo = entidad.codigo,
            nombreCorto = entidad.nombreCorto,
            inicio = entidad.fechaInicio.toFullDate() ?: Date(),
            fin = entidad.fechaFin.toFullDate() ?: Date(),
            inicioFacturacion = entidad.fechaInicioFacturacion.toFullDate() ?: Date(),
            orden = entidad.orden,
            periodo = Campania.construirPeriodo(entidad.periodo),
            esPrimerDiaFacturacion = entidad.primerDiaFacturacion
        )
    }

    fun parse(entidades: List<CampaniaUaEntity>): List<Campania> {
        return entidades.map { parse(it) }
    }

}
