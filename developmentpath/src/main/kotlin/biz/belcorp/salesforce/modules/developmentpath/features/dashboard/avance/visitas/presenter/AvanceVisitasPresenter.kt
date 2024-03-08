package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.visitas.presenter

import biz.belcorp.salesforce.core.features.handlers.observers.BaseObserver
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.utils.doAsync
import biz.belcorp.salesforce.core.utils.getSafePercentage
import biz.belcorp.salesforce.core.utils.isNotNull
import biz.belcorp.salesforce.core.utils.uiThread
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.ConsultantSectionDevelopmentPathInfo
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas.AvanceVisitas
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.dashboard.AvanceSeccionesUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.ruta.GetConsultantsUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.visitas.RecuperarAvanceVisitasUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.AvanceModelMapper
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.gz.model.SeccionAvanceModel
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.visitas.model.AvanceMapper
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.visitas.model.ConsultantInfoModel
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.visitas.view.AvanceVisitasView
import biz.belcorp.salesforce.modules.developmentpath.utils.IsConsultantNewCode.Companion.isNewConsultant

class AvanceVisitasPresenter(
    private val view: AvanceVisitasView,
    private val useCase: RecuperarAvanceVisitasUseCase,
    private val getConsultantUseCase: GetConsultantsUseCase,
    private val mapper: AvanceMapper,
    private val avanceUseCase: AvanceSeccionesUseCase,
    private val avanceModelMapper: AvanceModelMapper,
) {

    private var newConsultantVisited: Int = 0
    private var totalNewConsultants: Int = 0
    private var consultantVisited: Int = 0
    private var totalConsultants: Int = 0
    private var bpTotal: Int = 0
    private var bpMakeVisitsQuantity: Int = 0
    private var sectionsAmount: Int = 0
    private var bpWithMoreThan8daysVisit: Int = 0
    private var semaphore: Boolean = true
    fun calcularAvance(planId: Long) {
        useCase.ejecutar(planId, CalculoAvanceObserver())
    }

    fun getGzProgressSections(planId: Long) {
        // We fetch BP sections to get Consultants information
        avanceUseCase.ejecutar(planId, BpSectionsProgressSubscriber())
    }

    fun getBpProgressSections(planId: Long) {
        // we fetch only the bp section
        getConsultantUseCase.getConsultants(planId, ConsultantsInOneSectionSubscriber())
    }

    private fun getSections(sections: List<SeccionAvanceModel>) {
        sectionsAmount = sections.size
        bpTotal = sections.size
        for (section in sections) {
            if (section.visitedDays > 8) {
                bpWithMoreThan8daysVisit++
            }
            if (section.visitadas.toInt() > 0) {
                bpMakeVisitsQuantity++
            }
            consultantVisited += section.visitadas.toInt()
            totalConsultants += section.total.toInt()
            if (section.planId.isNotNull()) {
                section.planId?.let {
                    getConsultantUseCase.getConsultants(it, AllSectionsConsultantsSubscriber())
                }
            }
        }
    }

    private fun getAllSectionsConsultants(consultantsInfo: ConsultantSectionDevelopmentPathInfo) {
        //This controls that all sections are treated
        if (sectionsAmount > 0) {
            sectionsAmount--
            for (consultant in consultantsInfo.consultants) {
                if (isNewConsultant(consultant.tipsId)) {
                    totalNewConsultants++
                }
                if (consultant.visitDate.isNotNull() && isNewConsultant(consultant.tipsId)) {
                    newConsultantVisited++
                }
            }
        }
        if (sectionsAmount == 0) {
            view.showProgressCampaignInfo(
                ConsultantInfoModel(
                    newConsultantVisited,
                    totalNewConsultants,
                    progressNewConsultants = newConsultantVisited.toFloat()
                        .getSafePercentage(totalNewConsultants.toFloat()),
                    consultantVisited,
                    totalConsultants,
                    progressConsultants = consultantVisited.toFloat()
                        .getSafePercentage(totalConsultants.toFloat()),
                    bpMakeVisitsQuantity,
                    bpTotal,
                    progressBpVisits = (bpMakeVisitsQuantity.toFloat()
                        .getSafePercentage(bpTotal.toFloat())),
                    bpWithMore8DaysVisitPercentage = bpWithMoreThan8daysVisit.toFloat()
                        .getSafePercentage(bpTotal.toFloat())
                )
            )
            newConsultantVisited = 0
            totalNewConsultants = 0
            consultantVisited = 0
            totalConsultants = 0
            bpMakeVisitsQuantity = 0
            bpTotal = 0
            bpWithMoreThan8daysVisit = 0
            semaphore = true
        }
    }

    private fun getSectionConsultants(consultantsInfo: ConsultantSectionDevelopmentPathInfo) {
        for (consultant in consultantsInfo.consultants) {
            if (isNewConsultant(consultant.tipsId)) {
                totalNewConsultants++
            }
            if (consultant.visitDate.isNotNull() && isNewConsultant(consultant.tipsId)) {
                newConsultantVisited++
            }
        }

        view.showProgressCampaignInfo(
            ConsultantInfoModel(
                newConsultantVisited = newConsultantVisited,
                totalNewConsultants = totalNewConsultants,
                progressNewConsultants = newConsultantVisited.toFloat()
                    .getSafePercentage(totalNewConsultants.toFloat()),
                bpVisitsAtLeastOneDay = consultantsInfo.visitedDays
            )
        )

        totalNewConsultants = 0
        newConsultantVisited = 0
    }

    private inner class AllSectionsConsultantsSubscriber :
        BaseSingleObserver<ConsultantSectionDevelopmentPathInfo>() {

        override fun onError(exception: Throwable) {
            if (sectionsAmount > 0) {
                sectionsAmount--
            }
            exception.printStackTrace()
        }

        override fun onSuccess(t: ConsultantSectionDevelopmentPathInfo) {
            doAsync {
                uiThread {
                    getAllSectionsConsultants(t)
                }
            }
        }
    }

    private inner class ConsultantsInOneSectionSubscriber :
        BaseSingleObserver<ConsultantSectionDevelopmentPathInfo>() {

        override fun onError(exception: Throwable) {
            exception.printStackTrace()
        }

        override fun onSuccess(t: ConsultantSectionDevelopmentPathInfo) {
            doAsync {
                uiThread {
                    getSectionConsultants(t)
                }
            }
        }
    }

    private inner class BpSectionsProgressSubscriber :
        BaseObserver<AvanceSeccionesUseCase.Response>() {

        override fun onError(exception: Throwable) {
            exception.printStackTrace()
        }

        override fun onNext(t: AvanceSeccionesUseCase.Response) {
            doAsync {
                uiThread {
                    // To avoid call this method two times we add a semaphore
                    if (semaphore) {
                        semaphore = false
                        getSections(avanceModelMapper.map(t).secciones)
                    }
                }
            }
        }
    }


    inner private class CalculoAvanceObserver : BaseSingleObserver<AvanceVisitas>() {

        override fun onSuccess(t: AvanceVisitas) {
            doAsync {
                val modelo = mapper.map(t)
                uiThread {
                    view?.pintarAvance(modelo)
                }
            }
        }

        override fun onError(e: Throwable) {
            e.printStackTrace()
        }
    }
}
