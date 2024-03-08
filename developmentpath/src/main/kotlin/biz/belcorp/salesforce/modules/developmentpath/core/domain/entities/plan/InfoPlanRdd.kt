package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.plan

import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.zonificacion.Rol

class InfoPlanRdd(
    val planId: Long,
    val campania: String,
    val codigoPais: String,
    val codigoRegion: String?,
    val codigoZona: String?,
    val codigoSeccion: String?,
    val rol: Rol,
    val usuario: String?,
    val visitadas: Int,
    val planificadas: Int,
    val visitedDays: Int = NUMBER_ZERO,
) {
    val llaveUA: LlaveUA
        get() {
            return LlaveUA(
                codigoRegion = codigoRegion,
                codigoZona = codigoZona,
                codigoSeccion = codigoSeccion,
                consultoraId = null
            )
        }
}
