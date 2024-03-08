package biz.belcorp.salesforce.core.base

import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import java.io.Serializable

abstract class FragmentParameters(val rol: Rol) : Serializable {
    companion object {
        val key = FragmentParameters::class.java.simpleName
    }
}
