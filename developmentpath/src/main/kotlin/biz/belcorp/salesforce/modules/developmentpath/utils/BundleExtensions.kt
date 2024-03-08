package biz.belcorp.salesforce.modules.developmentpath.utils

import android.os.Bundle
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA

object UaParams {
    const val PARAM_CODIGO_REGION = "region"
    const val PARAM_CODIGO_ZONA = "zona"
    const val PARAM_CODIGO_SECCION = "seccion"
    const val PARAM_CONSULTORA_ID = "consultoraId"
    const val CONSULTORA_ID_POR_DEFECTO = 0L
}

fun Bundle.agregarUAComoParametros(llaveUA: LlaveUA): Bundle {
    putString(UaParams.PARAM_CODIGO_REGION, llaveUA.codigoRegion)
    putString(UaParams.PARAM_CODIGO_ZONA, llaveUA.codigoZona)
    putString(UaParams.PARAM_CODIGO_SECCION, llaveUA.codigoSeccion)
    putLong(UaParams.PARAM_CONSULTORA_ID, llaveUA.consultoraId ?: UaParams.CONSULTORA_ID_POR_DEFECTO)

    return this
}

fun Bundle.recuperarUA(): LlaveUA {
    val codigoRegion = getString(UaParams.PARAM_CODIGO_REGION, null)
    val codigoZona = getString(UaParams.PARAM_CODIGO_ZONA, null)
    val codigoSeccion = getString(UaParams.PARAM_CODIGO_SECCION, null)
    val consultoraId = recuperarConsultoraId(this)

    return LlaveUA(codigoRegion,
                   codigoZona,
                   codigoSeccion,
                   consultoraId)
}

private fun recuperarConsultoraId(bundle: Bundle): Long? {
    val consultoraId = bundle.getLong(
        UaParams.PARAM_CONSULTORA_ID,
        UaParams.CONSULTORA_ID_POR_DEFECTO
    )

    return if (consultoraId <= UaParams.CONSULTORA_ID_POR_DEFECTO) {
        null
    } else {
        consultoraId
    }
}
