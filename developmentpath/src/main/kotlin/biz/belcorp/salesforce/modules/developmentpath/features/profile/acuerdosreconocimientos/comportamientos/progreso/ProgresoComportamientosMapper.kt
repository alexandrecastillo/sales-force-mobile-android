package biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.progreso

import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.comportamientos.ProgresoReconocimientos
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.util.ProveedorIconoComportamiento

class ProgresoComportamientosMapper(private val proveedorIcono: ProveedorIconoComportamiento) {

    fun map(entidad: ProgresoReconocimientos.Avance): ProgresoComportamientoModel {
        return ProgresoComportamientoModel(
            iconoId = proveedorIcono.recuperarIcono(entidad.comportamiento.tipoIcono),
            porcentaje = entidad.porcentaje.toFloat(),
            color = elegirColor(entidad.porcentaje.toFloat())
        )
    }

    fun map(list: List<ProgresoReconocimientos.Avance>): List<ProgresoComportamientoModel> {
        return list.map { map(it) }
    }

    private fun elegirColor(porcentaje: Float): Int {
        return when {
            porcentaje <= LIMITE_BAJO -> R.color.estado_negativo
            porcentaje <= LIMITE_MEDIO -> R.color.estado_intermedio
            else -> R.color.estado_positivo
        }
    }

    companion object {
        const val LIMITE_BAJO = 25
        const val LIMITE_MEDIO = 65
    }
}
