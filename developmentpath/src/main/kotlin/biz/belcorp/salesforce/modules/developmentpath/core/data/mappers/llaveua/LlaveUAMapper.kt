package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.llaveua

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA

class LlaveUAMapper {

    fun obtenerUAComoString(llaveUA: LlaveUA): String {
        var zonificacion = ""
        if (!llaveUA.codigoRegion.isNullOrEmpty()) {
            zonificacion += llaveUA.codigoRegion
            if (!llaveUA.codigoZona.isNullOrEmpty()) {
                zonificacion += "/${llaveUA.codigoZona}"
                if (!llaveUA.codigoSeccion.isNullOrEmpty()) {
                    zonificacion += "/${llaveUA.codigoSeccion}"
                }
            }
        }
        return zonificacion
    }

}
