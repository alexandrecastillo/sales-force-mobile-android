package biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.detail.multiprofile.items.uadetail.mapper

import biz.belcorp.mobile.components.design.selector.bar.model.SelectorModel
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.features.uainfo.UaInfoModel

class GainUaMapper {

    fun parse(ua: UaInfoModel, rol: Rol): SelectorModel {
        return SelectorModel(getUaId(ua, rol), getUaId(ua, rol))
    }

    fun getUaId(ua: UaInfoModel, rol: Rol): String {
        return when (rol) {
            Rol.DIRECTOR_VENTAS -> ua.uaKey.codigoRegion.orEmpty()
            Rol.GERENTE_REGION -> ua.uaKey.codigoZona.orEmpty()
            Rol.GERENTE_ZONA -> ua.uaKey.codigoSeccion.orEmpty()
            else -> Constant.EMPTY_STRING
        }

    }

}
