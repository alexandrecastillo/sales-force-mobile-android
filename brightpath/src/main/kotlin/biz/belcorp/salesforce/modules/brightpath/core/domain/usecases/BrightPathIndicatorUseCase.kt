package biz.belcorp.salesforce.modules.brightpath.core.domain.usecases

import biz.belcorp.salesforce.base.utils.isGzOrSe
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.exceptions.UnsupportedRoleException
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.domain.usecases.configuration.ConfigurationUseCase
import biz.belcorp.salesforce.core.utils.orZero
import biz.belcorp.salesforce.modules.brightpath.core.domain.entities.BrightPathArgs
import biz.belcorp.salesforce.modules.brightpath.core.domain.entities.IndicatorArgs
import biz.belcorp.salesforce.modules.brightpath.core.domain.entities.LevelIndicator
import biz.belcorp.salesforce.modules.brightpath.core.domain.repository.BrightPathIndicatorRepository


class BrightPathIndicatorUseCase(
    private val repository: BrightPathIndicatorRepository,
    private val countryConfig: ConfigurationUseCase,
    private val sessionManager: SessionRepository
) {

    private var isBrightPathEnabled: Boolean = false

    private val session get() = requireNotNull(sessionManager.getSession())

    private val role = session.rol

    suspend fun getIndicatorDataAsync(): IndicatorArgs {
        isBrightPathEnabled = countryConfig.getConfiguration().isPdv
        val ua = session.llaveUA

        val entity = if (role.isGzOrSe()) getAsync(ua) else throw getUnsupportedRoleException()

        return implIndicatorBusiness(entity)
    }

    fun implIndicatorBusiness(entity: LevelIndicator): IndicatorArgs =
        if (isBrightPathEnabled) parseIndicatorLevelArgs(entity) else IndicatorArgs()

    private suspend fun getAsync(uaKey: LlaveUA) =
        repository.getIndicatorDataAsync(uaKey)

    private fun getUnsupportedRoleException() = UnsupportedRoleException(role)

    private fun parseIndicatorLevelArgs(entity: LevelIndicator) = with(entity) {
        BrightPathArgs.Se(
            campaniaAnterior = campaniaAnterior.orEmpty(),
            acumuladoPorNivel = acumuladoPorNivel.orEmpty(),
            periodoActual = periodoActual.orEmpty(),
            totalCampaniaAnterior = totalCampaniaAnterior.orZero(),
            acumuladoPeriodo = acumuladoPeriodo.orEmpty()
        )
    }


}
