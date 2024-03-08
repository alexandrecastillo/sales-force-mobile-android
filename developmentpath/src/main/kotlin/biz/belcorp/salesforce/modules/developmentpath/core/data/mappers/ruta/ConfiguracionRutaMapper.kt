package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.ruta

import biz.belcorp.salesforce.core.entities.sql.path.ConfiguracionRDDEntity
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.parametros.ParametrosRdd

class ConfiguracionRutaMapper {

    fun parseToConfiguracion(entity: ConfiguracionRDDEntity?): ParametrosRdd? {

        var parametrosRdd: ParametrosRdd? = null
        if (entity != null) {
            parametrosRdd = ParametrosRdd(
                maxVisitasPorDia = entity.cantidadVisitasDia,
                horaInicio = entity.horaInicio,
                horaFin = entity.horaFin,
                rol = entity.rol,
                parametros = entity.parametros,
                duracionVisitaHoras = entity.duracionVisita,
                distanciaMin = entity.distanciaMin,
                radioBusqueda = entity.radioBusqueda)
        }
        return parametrosRdd
    }
}
