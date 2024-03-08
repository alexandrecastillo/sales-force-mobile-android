package biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.detail.multiprofile.mapper

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.collection.CollectionIndicator

class GainUaMapper {

    fun getUaId(indicator: CollectionIndicator, rol: Rol): String {
        return when (rol) {
            Rol.DIRECTOR_VENTAS -> indicator.region
            Rol.GERENTE_REGION -> indicator.zone
            Rol.GERENTE_ZONA -> indicator.section
            else -> Constant.EMPTY_STRING
        }
    }

}
