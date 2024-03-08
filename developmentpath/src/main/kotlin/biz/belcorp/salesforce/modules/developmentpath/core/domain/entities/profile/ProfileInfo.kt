package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.profile

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.utils.WordUtils
import biz.belcorp.salesforce.core.utils.guionSiNull
import biz.belcorp.salesforce.core.utils.isEmptyOrDash
import biz.belcorp.salesforce.modules.developmentpath.utils.obtenerPrimerLetra
import java.util.*


open class ProfileInfo(
    val contact: Contact,
    val llaveUA: LlaveUA? = null
) {

    class DatoPersonaPc(
        contact: Contact
    ) : ProfileInfo(contact)

    class DatoPersonaCo(
        contact: Contact,
        val codigo: String,
        val digitoVerificador: String,
        val saldoPendiente: Double,
        val codigoCampaniaIngreso: String,
        val codigoCampaniaUltimaFacturacion: String,
        val cantidadReingresos: Int,
        val celular: String,
        val autorizada: Boolean,
        val segmentoInterno: String,
        val hasCashPayment: Boolean,
        val digitalSegment: String? = null,
        val segmentDescription: String? = null
    ) : ProfileInfo(contact)

    class DatoPersonaSe(
        contact: Contact,
        val nivel: String,
        val campaniaIngreso: String,
        val codigo: String,
        val saldoPendiente: Double,
        val codigoSeccion: String,
        val campaniaUltimaFacturacion: String,
        val exitosa: Boolean,
        val productividad: String,
        val digitalSegment: String? = null
    ) : ProfileInfo(contact)

    class DatoPersonaGz(
        contact: Contact,
        llaveUA: LlaveUA,
        var estado: String,
        val esNueva: Boolean = false,
        val nroCampaniasComoNueva: Int,
        var prevCampaign: String = Constant.EMPTY_STRING,
        val digitalSegment: String? = null
    ) : ProfileInfo(contact, llaveUA)

    class DatoPersonaGr(
        contact: Contact,
        llaveUA: LlaveUA,
        var estado: String,
        var prevCampaign: String = Constant.EMPTY_STRING,
        val digitalSegment: String? = null
    ) : ProfileInfo(contact, llaveUA)

    class Contact(
        val primerNombre: String,
        val primerApellido: String,
        val segundoApellido: String,
        val birthdate: Date? = null,
        val phoneNumber: String,
        val homeNumber: String = Constant.HYPHEN,
        var prefix: String = Constant.EMPTY_STRING,
        val email: String,
        val document: String,
        val address: String,
        val addressReference: String = Constant.EMPTY_STRING,
        val anniversaryDate: Date? = null,
        val multiBrand: Boolean? = false,
        var digitalSegment: String? = null
    ) {

        val phoneNumberWithPrefix
            get() = "$prefix $phoneNumber".takeIf { !phoneNumber.isEmptyOrDash() }.guionSiNull()

        val homeNumberWithPrefix
            get() = "$prefix $homeNumber".takeIf { !homeNumber.isEmptyOrDash() }.guionSiNull()

        val fullname
            get() = WordUtils.capitalizeFully("$primerNombre $primerApellido $segundoApellido")

        val shortname
            get() = WordUtils.capitalizeFully("$primerNombre $primerApellido")

        val iniciales
            get() = "${primerNombre.obtenerPrimerLetra()}${primerApellido.obtenerPrimerLetra()}"

    }

}
