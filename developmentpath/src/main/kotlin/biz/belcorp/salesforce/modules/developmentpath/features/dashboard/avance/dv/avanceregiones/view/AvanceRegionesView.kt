package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.dv.avanceregiones.view

import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.dv.avanceregiones.model.RegionModel

interface AvanceRegionesView {

    fun pintarRegiones(regiones: List<RegionModel>)
}
