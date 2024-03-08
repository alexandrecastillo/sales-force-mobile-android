package biz.belcorp.salesforce.modules.inspires.features.travel.tabs.advance

import biz.belcorp.salesforce.core.domain.usecases.session.ObtenerSesionUseCase
import biz.belcorp.salesforce.core.features.base.Presenter
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.utils.doAsync
import biz.belcorp.salesforce.core.utils.uiThread
import biz.belcorp.salesforce.modules.inspires.core.domain.entities.InspiraAvances
import biz.belcorp.salesforce.modules.inspires.core.domain.entities.InspiraAvancesPeriodo
import biz.belcorp.salesforce.modules.inspires.core.domain.entities.InspiraIndicador
import biz.belcorp.salesforce.modules.inspires.core.domain.usecase.GetIndicatorUseCase
import biz.belcorp.salesforce.modules.inspires.core.domain.usecase.GetInspiraAvancePeriodoUseCase
import biz.belcorp.salesforce.modules.inspires.core.domain.usecase.GetInspiraAvanceUseCase
import biz.belcorp.salesforce.modules.inspires.mapper.InspireModelDataMapper
import biz.belcorp.salesforce.modules.inspires.model.InspiraAvancePeriodoModel
import biz.belcorp.salesforce.modules.inspires.model.InspireIndicatorModel
import biz.belcorp.salesforce.modules.inspires.util.enmascararACampania
import biz.belcorp.salesforce.modules.inspires.util.isPeru

class InspireAdvancePresenter
constructor(
    private val getInspiraAvance: GetInspiraAvanceUseCase,
    private val getInspiraAvancePeriodo: GetInspiraAvancePeriodoUseCase,
    private val getInspiraIndicatorUseCase: GetIndicatorUseCase,
    private val getSesionUseCase: ObtenerSesionUseCase,
    private val mapper: InspireModelDataMapper
) : Presenter {

    private lateinit var view: InspireAdvanceView
    private val session get() = requireNotNull(getSesionUseCase.obtener())

    fun create(view: InspireAdvanceView) {
        this.view = view
    }

    fun onPrepare() {
        getInspiraIndicatorUseCase.one(InspiraIndicatorSubscriber())
    }

    override fun destroy() {
        getInspiraIndicatorUseCase.dispose()
        getInspiraAvance.dispose()
        getInspiraAvancePeriodo.dispose()
    }

    private fun showViewValues(model: InspireIndicatorModel) {
        view.showHeaderIcon(model.activa)
        view.showHeaderMessage(model.activa, model.nombreSE)
        getInspiraAvancePeriodo.has(HasAvancePeriodoObserver(model.activa))
        if (isLimited().not() && model.activa) {
            getInspiraAvancePeriodo.all(AvancePeriodoObserver())
        }
    }

    private fun isLimited(): Boolean = session.pais.isPeru()

    private inner class InspiraIndicatorSubscriber : BaseSingleObserver<InspiraIndicador>() {
        override fun onError(e: Throwable) = e.printStackTrace()
        override fun onSuccess(t: InspiraIndicador) {
            showViewValues(mapper.transformIndicator(t))
        }
    }

    private inner class AvanceObserver(private val active: Boolean, val hasPeriod: Boolean) : BaseSingleObserver<List<InspiraAvances>>() {
        override fun onError(e: Throwable) {
            view.hideContent()
            e.printStackTrace()
        }

        override fun onSuccess(t: List<InspiraAvances>) {
            doAsync {
                val avancesList = mapper.transformAdvance(t)
                val campaign = avancesList.maxBy { it.campania }?.campania.toString().enmascararACampania()
                val list = avancesList.sortedByDescending { it.campania }
                uiThread {
                    if (list.isNullOrEmpty())
                        view.hideContent()
                    else {
                        view.showTitle(campaign, active)
                        view.showList(list, isLimited(), active, hasPeriod)
                    }
                }
            }
        }
    }

    private inner class HasAvancePeriodoObserver(val activa: Boolean) : BaseSingleObserver<Boolean>() {
        override fun onError(e: Throwable) = e.printStackTrace()
        override fun onSuccess(t: Boolean) {
            getInspiraAvance.all(AvanceObserver(activa, t))
        }
    }

    private inner class AvancePeriodoObserver : BaseSingleObserver<List<InspiraAvancesPeriodo>>() {
        override fun onError(e: Throwable) = e.printStackTrace()
        override fun onSuccess(t: List<InspiraAvancesPeriodo>) {
            doAsync {
                val list: List<InspiraAvancePeriodoModel> = mapper.transformAdvancePeriod(t).map { item ->
                    item.codigoPeriodo.let { codigo ->
                        item.apply {
                            cardinal = when (codigo) {
                                Period.FIRST.code -> Period.FIRST.desc
                                Period.SECOND.code -> Period.SECOND.desc
                                Period.THIRD.code -> Period.THIRD.desc
                                else -> Period.OTHER.desc
                            }
                        }
                    }
                }
                uiThread {
                    view.showPeriodList(list)
                }
            }
        }
    }

    companion object {

        enum class Period(val code: String, val desc: String) {
            FIRST("001", "1ER"),
            SECOND("002", "2DO"),
            THIRD("003", "3ER"),
            OTHER("", "?")
        }

    }
}
