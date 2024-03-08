package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.desarrollo.model

import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.comportamientos.ComportamientoPorcentaje
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.desarrollo.view.comportamientos.model.DesarrolloComportamientoModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.util.ProveedorIconoComportamiento

class DesarrolloComportamientoMapper(private val proveedorIcono: ProveedorIconoComportamiento) {

    fun map(comportamientos: List<ComportamientoPorcentaje>): List<DesarrolloComportamientoModel> {
        return comportamientos.mapNotNull { map(it) }
    }

    private fun map(comportamiento: ComportamientoPorcentaje): DesarrolloComportamientoModel? {
        return DesarrolloComportamientoModel(
            texto = comportamiento.descripcion,
            iconoId = proveedorIcono.recuperarIcono(comportamiento.iconoID),
            porcentaje = comportamiento.porcentaje,
            color = elegirColor(comportamiento.cumplimiento)
        )
    }

    private fun elegirColor(cumplimiento: Boolean): Int {
        return if (cumplimiento) {
            R.color.estado_positivo
        } else {
            R.color.estado_negativo
        }
    }

}
