package biz.belcorp.salesforce.base.features.home.user

import biz.belcorp.salesforce.base.R
import biz.belcorp.salesforce.base.features.utils.AppTextResolver
import biz.belcorp.salesforce.core.base.BaseMapper
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.constants.Emoji
import biz.belcorp.salesforce.core.constants.Level
import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.core.domain.entities.session.Sesion
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.createEmoji
import java.util.*

class UserInfoMapper(private val textResolver: AppTextResolver) : BaseMapper<Sesion, UserModel> {

    override fun map(model: Sesion): UserModel {
        return UserModel(
            name = createName(model.person.firstName),
            description = createUserDescription(model),
            level = createLevel(model),
            colorSegment = createColorForLevel(model.nivelActual.orEmpty())
        )
    }

    private fun createName(name: String) = name.toLowerCase(Locale.getDefault()).capitalize()

    private fun createUserDescription(model: Sesion): String {
        return when (model.rol) {
            Rol.DIRECTOR_VENTAS -> createDescriptionDV(model.pais)
            Rol.GERENTE_REGION -> createDescriptionGR(model.llaveUA, model.pais)
            Rol.GERENTE_ZONA -> createDescriptionGZ(model.llaveUA, model.pais)
            Rol.SOCIA_EMPRESARIA -> createDescriptionSE(model.llaveUA, model.pais)
            else -> Constant.EMPTY_STRING
        }
    }

    private fun createDescriptionDV(country: Pais?): String {
        val args = arrayOf(
            Rol.DIRECTOR_VENTAS.comoTexto(),
            country?.asText().orEmpty(),
            createFlagEmoji(country?.codigoIso.orEmpty())
        )
        return textResolver.formatUserDescriptionMultiProfile(*args)
    }

    private fun createDescriptionGR(ua: LlaveUA, country: Pais?): String {
        val args = arrayOf(
            Rol.GERENTE_REGION.comoTexto(),
            ua.codigoRegion,
            createFlagEmoji(country?.codigoIso.orEmpty())
        )
        return textResolver.formatUserDescriptionMultiProfile(*args)
    }

    private fun createDescriptionGZ(ua: LlaveUA, country: Pais?): String {
        val args = arrayOf(
            Rol.GERENTE_ZONA.comoTexto(),
            ua.codigoZona,
            createFlagEmoji(country?.codigoIso.orEmpty())
        )
        return textResolver.formatUserDescriptionMultiProfile(*args)
    }

    private fun createDescriptionSE(ua: LlaveUA, country: Pais?): String {
        val args = arrayOf(
            Rol.SOCIA_EMPRESARIA.comoTexto(),
            ua.codigoSeccion,
            ua.codigoZona,
            createFlagEmoji(country?.codigoIso.orEmpty())
        )
        return textResolver.formatUserDescriptionSE(*args)
    }

    private fun createColorForLevel(level: String): Int {
        return when {
            level.startsWith(Level.PRE_BRONZE, true) ||
                level.startsWith(Level.BRONZE, true) -> R.color.bronce
            level.startsWith(Level.GOLDEN, true) -> R.color.oro
            level.startsWith(Level.SILVER, true) -> R.color.plata
            level.startsWith(Level.PLATINUM, true) -> R.color.platinum
            level.startsWith(Level.DIAMOND, true) -> R.color.diamante
            else -> R.color.transparent
        }
    }

    private fun createFlagEmoji(countryISO: String): String {
        return createEmoji(Emoji.Country.mapToCountryEmoji(countryISO))
    }

    private fun createLevel(session: Sesion): String {
        if (session.rol == Rol.SOCIA_EMPRESARIA)
            return textResolver.formatUserLevel(session.nivelActual)
        return Constant.EMPTY_STRING
    }
}
