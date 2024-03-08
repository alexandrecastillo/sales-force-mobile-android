package biz.belcorp.salesforce.modules.brightpath.core.domain.usecases.ua

import biz.belcorp.salesforce.base.utils.isDv
import biz.belcorp.salesforce.base.utils.isGr
import biz.belcorp.salesforce.core.domain.entities.ua.Region
import biz.belcorp.salesforce.core.domain.entities.ua.Seccion
import biz.belcorp.salesforce.core.domain.entities.ua.Zona
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.brightpath.core.domain.entities.UASegment
import biz.belcorp.salesforce.modules.brightpath.core.domain.repository.UaSegmentsRepository


class UASegmentUseCase (
    private val repository: UaSegmentsRepository,
    private val sessionRepository: SessionRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    private val role get() = requireNotNull(sessionRepository.getSession()).rol

    fun getSegmentsByRol(observer: BaseSingleObserver<List<UASegment>>) = with(role) {
        val observable = when {
            isDv() ->  getUaRegions()
            isGr() -> getUaZones()
            else -> getUaSections()
        }
        execute(observable, observer)
    }

    private fun getUaRegions() = this.repository.getRegions().map { parseRegions(it) }

    private fun getUaZones() = this.repository.getZones().map { parseZones(it) }

    private fun getUaSections() = this.repository.getSections().map { parseSections(it) }

    private fun parseRegions(list: List<Region>) = list.map { parseRegionToUASeg(it) }

    private fun parseZones(list: List<Zona>) = list.map { parseZoneToUASeg(it) }

    private fun parseSections(list: List<Seccion>) = list.map { parseSectionToUASeg(it) }

    private fun parseRegionToUASeg(i: Region): UASegment {
        return UASegment(
                fullName = i.gerenteRegion.orEmpty(),
                segmentID= i.codigo.orEmpty()
        )
    }

    private fun parseZoneToUASeg(i: Zona): UASegment {
        return UASegment(
                fullName = i.gerenteZona.orEmpty(),
                segmentID= i.codigo.orEmpty()
        )
    }

    private fun parseSectionToUASeg(i: Seccion): UASegment {
        return UASegment(
                fullName = i.sociaEmpresaria.orEmpty(),
                segmentID= i.codigo.orEmpty(),
                level = i.nivel.orEmpty()
        )
    }
}
