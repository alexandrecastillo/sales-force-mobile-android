package biz.belcorp.salesforce.modules.consultants.core.domain.usecases.unified

import biz.belcorp.salesforce.core.constants.CountryISO
import biz.belcorp.salesforce.core.domain.entities.session.Sesion
import biz.belcorp.salesforce.core.domain.repository.configuration.ConfigurationRepository
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.utils.isSE
import biz.belcorp.salesforce.modules.consultants.core.domain.entities.unified.ExtraFilterParams

class GetFilterParamsUseCase(
    private val sesionRepository: SessionRepository,
    private val configurationRepository: ConfigurationRepository
) {

    suspend fun getExtraParams(): ExtraFilterParams {

        val session = requireNotNull(sesionRepository.getSession())
        val config = configurationRepository.getConfiguration()

        return ExtraFilterParams(
            minimalOrderAmount = config.minimalOrderAmount,
            tippingPoint = config.tippingPoint,
            skipBrilliants = allowSkipBrilliants(session)
        )
    }

    private fun allowSkipBrilliants(session: Sesion): Boolean {
        return session.rol.isSE() && session.countryIso == CountryISO.COLOMBIA
    }

}
