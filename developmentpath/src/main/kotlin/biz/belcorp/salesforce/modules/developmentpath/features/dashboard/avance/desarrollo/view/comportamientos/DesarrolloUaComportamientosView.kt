package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.desarrollo.view.comportamientos

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.desarrollo.view.comportamientos.model.DesarrolloComportamientoModel

interface DesarrolloUaComportamientosView {

    fun pintarCampaniaActual(periodo: Campania.Periodo, nombreCorto: String)

    fun pintarCampaniaAnterior(unidadAdministrativa: String, nombreCorto: String)

    fun pintarComportamientos(comportamientos: List<DesarrolloComportamientoModel>)
}
