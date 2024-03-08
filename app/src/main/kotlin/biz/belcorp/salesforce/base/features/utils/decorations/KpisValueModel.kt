package biz.belcorp.salesforce.base.features.utils.decorations

import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import java.io.Serializable

class KpisValueModel(var rol: Int = 0,
                     var period: Int = 0,
                     var level: Int = 0,
                     var goal: Int = 0,
                     var isBright: Boolean = true) : Serializable {

    val userRol get() = getRol(rol)

    private fun getRol(rol: Int): Rol {
       return when(rol) {
            0-> Rol.DIRECTOR_VENTAS
            1-> Rol.GERENTE_REGION
            2-> Rol.GERENTE_ZONA
            3-> Rol.SOCIA_EMPRESARIA
            else -> Rol.NINGUNO
        }
    }
}


