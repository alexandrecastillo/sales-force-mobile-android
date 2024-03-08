package biz.belcorp.salesforce.core.utils

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA

fun LlaveUA.isEqual(ua: LlaveUA): Boolean {
    return removeHyphens().run {
        val otherUa = ua.removeHyphens()
        codigoRegion.equals(otherUa.codigoRegion, false)
            .and(codigoZona.equals(otherUa.codigoZona, false))
            .and(codigoSeccion.equals(otherUa.codigoSeccion, false))
    }
}

fun LlaveUA.formatHyphenIfNull(): LlaveUA {
    return LlaveUA(
        codigoRegion = codigoRegion.aGuionSiEsNullOVacio(),
        codigoZona = codigoZona.aGuionSiEsNullOVacio(),
        codigoSeccion = codigoSeccion.aGuionSiEsNullOVacio(),
        consultoraId = consultoraId
    )
}

fun LlaveUA.removeHyphens(): LlaveUA {
    return LlaveUA(
        codigoRegion = codigoRegion.orEmpty().deleteHyphen(),
        codigoZona = codigoZona.orEmpty().deleteHyphen(),
        codigoSeccion = codigoSeccion.orEmpty().deleteHyphen(),
        consultoraId = consultoraId
    )
}
