package biz.belcorp.salesforce.core.domain.entities.zonificacion

import android.os.Parcel
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.constants.Constant.HYPHEN
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.isNotNull
import biz.belcorp.salesforce.core.utils.notEquals
import java.io.Serializable

class LlaveUA(
    val codigoRegion: String? = null,
    val codigoZona: String? = null,
    val codigoSeccion: String? = null,
    val consultoraId: Long? = null
) : Serializable {

    val roleAssociated: Rol
        get() = when {
            isDv() -> Rol.DIRECTOR_VENTAS
            isGr() -> Rol.GERENTE_REGION
            isGz() -> Rol.GERENTE_ZONA
            isSe() && !isCo() -> Rol.SOCIA_EMPRESARIA
            else -> Rol.CONSULTORA
        }

    val rolLiderAsociado: Rol
        get() = roleAssociated

    val rolHijoAsociado: Rol
        get() = when (rolLiderAsociado) {
            Rol.DIRECTOR_VENTAS -> Rol.GERENTE_REGION
            Rol.GERENTE_REGION -> Rol.GERENTE_ZONA
            Rol.GERENTE_ZONA -> Rol.SOCIA_EMPRESARIA
            else -> Rol.CONSULTORA
        }

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Long::class.java.classLoader) as? Long
    )

    private fun isDv() = codigoRegion.isNullOrBlank() || codigoRegion == HYPHEN
    private fun isGr() = !isDv() && codigoZona.isNullOrBlank() || codigoZona.equals(HYPHEN)
    private fun isGz() = !isGr() && codigoSeccion.isNullOrBlank() || codigoSeccion.equals(HYPHEN)
    private fun isSe() = !isGz() && !codigoSeccion.isNullOrBlank() || codigoSeccion.notEquals(HYPHEN)
    private fun isCo() = isSe() && consultoraId?.takeIf { it != Constant.ONE_NEGATIVE.toLong() }.isNotNull()
}
