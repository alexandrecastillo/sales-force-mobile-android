package biz.belcorp.salesforce.core.domain.entities.session

import android.annotation.SuppressLint
import biz.belcorp.salesforce.core.constants.Level.DIAMOND
import biz.belcorp.salesforce.core.constants.Level.PRE_BRONZE
import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.core.domain.entities.people.Person
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.aGuionSiEsNullOVacio
import biz.belcorp.salesforce.core.utils.isSE

class Sesion(
    val region: String?,
    val zona: String?,
    val countryIso: String,
    val seccion: String?,
    val codigoRol: String,
    val codigoUsuario: String,
    val username: String,
    val codigoConsultora: String,
    val nivel: String?,
    val cub: String,
    val nivelActual: String?,
    val person: Person,
    val campaign: Campania
) {

    val rol = Rol.Builder.construir(codigoRol)
    val pais = Pais.find(countryIso)

    val zonificacion: String
        get() {
            var zonificacion = ""
            if (!region.isNullOrEmpty()) {
                zonificacion += region
                if (!zona.isNullOrEmpty()) {
                    zonificacion += "/$zona"
                    if (!seccion.isNullOrEmpty()) {
                        zonificacion += "/$seccion"
                    }
                }
            }
            return zonificacion
        }

    val llaveUA: LlaveUA
        get() {
            return LlaveUA(
                codigoRegion = region.aGuionSiEsNullOVacio(),
                codigoZona = zona.aGuionSiEsNullOVacio(),
                codigoSeccion = seccion.aGuionSiEsNullOVacio(),
                consultoraId = -1L
            )
        }

    @Deprecated("Use person instead")
    var personaOld: Any? = null


    val isPreBronce: Boolean
        @SuppressLint("DefaultLocale")
        get() {
            return nivelActual?.toUpperCase().equals(PRE_BRONZE, ignoreCase = true)
        }

    val isDiamond: Boolean
        get() {
            return nivelActual.equals(DIAMOND, ignoreCase = true)
        }

    val codeByRole
        get() = when {
            rol.isSE() -> codigoConsultora
            else -> codigoUsuario
        }

}
