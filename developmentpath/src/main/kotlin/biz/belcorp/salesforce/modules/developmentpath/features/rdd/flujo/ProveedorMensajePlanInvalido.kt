package biz.belcorp.salesforce.modules.developmentpath.features.rdd.flujo

import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.R

class ProveedorMensajePlanInvalido {

    fun recuperarRecurso(rolAsociado: Rol): Int {
        return when (rolAsociado) {
            Rol.GERENTE_REGION -> R.string.rdd_plan_region_error
            Rol.GERENTE_ZONA -> R.string.rdd_plan_zona_error
            Rol.SOCIA_EMPRESARIA -> R.string.rdd_plan_seccion_error
            else -> R.string.rdd_plan_error_general
        }
    }
}
