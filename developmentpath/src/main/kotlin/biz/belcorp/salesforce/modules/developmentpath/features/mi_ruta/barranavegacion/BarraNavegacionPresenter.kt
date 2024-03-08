package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.barranavegacion

import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.utils.doAsync
import biz.belcorp.salesforce.core.utils.uiThread
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.plan.InfoPlanRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.navegacion.BarraNavegacionUseCase

class BarraNavegacionPresenter(
    private val view: BarraNavegacionView,
    private val barraNavegacionUseCase: BarraNavegacionUseCase,
    private val barraNavegacionMapper: BarraNavegacionMapper
) {

    lateinit var model: BarraNavegacionModel
    private var infoPlan: InfoPlanRdd? = null

    fun configurarBarra(planId: Long) {
        barraNavegacionUseCase.obtenerBarra(planId, Subscriber())
        infoPlan = barraNavegacionUseCase.obetenerinfoPlan(planId)
    }

    fun toggle() {
        val nivelPropio = model.niveles.last() as NivelModel.UaExpandible
        Constant.EVENTO_VER_MAS = nivelPropio.expandido
        nivelPropio.expandido = !nivelPropio.expandido
        model.niveles.take(model.niveles.size - 1).forEach {
            it.visible = !it.visible
        }

        cargarBarra()
    }

    private fun cargarBarra() {
        view.cargarBarra(model)
        infoPlan?.let { view.traerRol(it) }
    }

    inner class Subscriber : BaseSingleObserver<BarraNavegacionUseCase.Response>() {
        override fun onSuccess(t: BarraNavegacionUseCase.Response) {
            doAsync {
                model = barraNavegacionMapper.parse(t.barra)
                uiThread {
                    cargarBarra()
                }
            }
        }
    }

}
