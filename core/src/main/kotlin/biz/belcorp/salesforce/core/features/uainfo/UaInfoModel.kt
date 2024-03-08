package biz.belcorp.salesforce.core.features.uainfo

import biz.belcorp.mobile.components.core.R
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.people.Person
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.features.utils.CoreTextResolver
import biz.belcorp.salesforce.core.utils.capitalizeAll
import biz.belcorp.salesforce.core.utils.deleteHyphen
import biz.belcorp.salesforce.core.utils.inject
import org.koin.core.KoinComponent
import java.util.*

data class UaInfoModel(
    val code: Long,
    val person: Person?,
    val uaKey: LlaveUA,
    val role: Rol,
    val campaign: Campania?,
    val isCovered: Boolean,
    val isThirdPerson: Boolean
) : KoinComponent {

    private val textResolver by inject<CoreTextResolver>()

    val userInformation
        get() = if (isCovered) "${fullName.capitalizeAll()} ${Constant.HYPHEN} ${role.comoTexto()}" else NOT_COVERED

    val userKpiInformation
        get() = if (isCovered) fullName.capitalizeAll() else NOT_COVERED

    val userInformationColor
        get() = if (isCovered) R.color.captionBlack else R.color.magenta

    val periodInformation: String
        get() {
            return if (campaign == null) Constant.EMPTY_STRING
            else "${campaign.periodo?.nombre().orEmpty()} ${campaign.nombreCorto.deleteHyphen()}"
        }

    val periodColor: Int
        get() {
            if (campaign?.periodo == null) return R.color.captionBlack
            return when (campaign.periodo) {
                Campania.Periodo.FACTURACION -> R.color.magenta
                else -> R.color.captionBlack
            }
        }

    val fullName
        get() = "${person?.firstName.orEmpty().toLowerCase(Locale.getDefault()).capitalize()} " +
            person?.firstSurname.orEmpty().toLowerCase(Locale.getDefault()).capitalize()

    val label
        get() = when (role) {
            Rol.GERENTE_REGION -> uaKey.codigoRegion.orEmpty()
            Rol.GERENTE_ZONA -> uaKey.codigoZona.orEmpty()
            Rol.SOCIA_EMPRESARIA -> uaKey.codigoSeccion.orEmpty()
            else -> Constant.EMPTY_STRING
        }

    val labelText
        get() = if (isThirdPerson) label else labelByRole()

    private fun labelByRole(): String {
        return when (role) {
            Rol.DIRECTOR_VENTAS -> textResolver.getTextUaCountry()
            Rol.GERENTE_REGION -> textResolver.getTextUaRegion()
            Rol.GERENTE_ZONA -> textResolver.getTextUaZone()
            else -> Constant.EMPTY_STRING
        }
    }

    companion object {
        private const val NOT_COVERED = "Descoberturada"
    }
}
