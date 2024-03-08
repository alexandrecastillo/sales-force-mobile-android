package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.barranavegacion

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.plan.InfoPlanRdd

interface BarraNavegacionView {
    fun cargarBarra(modelo: BarraNavegacionModel)
    fun traerRol(rol: InfoPlanRdd)
}
