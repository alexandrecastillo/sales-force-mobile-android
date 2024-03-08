package biz.belcorp.salesforce.modules.developmentpath.features.habilidades.avance_u6c.view

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.habilidades.AvanceHabilidad
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.habilidades.DetalleHabilidadesAcompaniamiento
import biz.belcorp.salesforce.modules.developmentpath.features.habilidades.avance_u6c.model.AvanceHabilidadModel
import biz.belcorp.salesforce.modules.developmentpath.features.habilidades.view.ProveedorEstiloHabilidad

class AvanceHabilidadMapper {

    private fun parse(modelo: AvanceHabilidad, idAsignadas: List<Long>): AvanceHabilidadModel {

        return AvanceHabilidadModel(
            idIcono = ProveedorEstiloHabilidad().obtenerIcono(modelo.iconoId),
            titulo = modelo.titulo,
            porcentaje = calcularPorcentaje(modelo),
            estaAsignada = idAsignadas.contains(modelo.habilidadId),
            cumplimientoCampaniaX = modelo.cumplimientoCampaniaX,
            cumplimientoCampaniaXMenos1 = modelo.cumplimientoCampaniaXMenos1,
            cumplimientoCampaniaXMenos2 = modelo.cumplimientoCampaniaXMenos2,
            cumplimientoCampaniaXMenos3 = modelo.cumplimientoCampaniaXMenos3,
            cumplimientoCampaniaXMenos4 = modelo.cumplimientoCampaniaXMenos4,
            cumplimientoCampaniaXMenos5 = modelo.cumplimientoCampaniaXMenos5
        )
    }

    private fun calcularPorcentaje(modelo: AvanceHabilidad): Int {
        var cantidad = 0
        if (modelo.cumplimientoCampaniaX)
            cantidad++
        if (modelo.cumplimientoCampaniaXMenos1)
            cantidad++
        if (modelo.cumplimientoCampaniaXMenos2)
            cantidad++
        if (modelo.cumplimientoCampaniaXMenos3)
            cantidad++
        if (modelo.cumplimientoCampaniaXMenos4)
            cantidad++
        if (modelo.cumplimientoCampaniaXMenos5)
            cantidad++

        return (cantidad.div(6.0) * 100).toInt()
    }

    fun parse(avances: List<AvanceHabilidad>, asignadas: List<DetalleHabilidadesAcompaniamiento>):
        List<AvanceHabilidadModel> {

        val habilidadesAsignadas = asignadas.map { it.habilidad.id }
        return avances.map { parse(it, habilidadesAsignadas) }
    }
}
