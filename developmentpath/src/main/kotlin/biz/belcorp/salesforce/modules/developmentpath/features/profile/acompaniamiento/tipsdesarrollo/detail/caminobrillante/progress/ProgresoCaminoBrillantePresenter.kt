package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.caminobrillante.progress

import biz.belcorp.mobile.components.design.step.model.StepModel
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.caminobrillante.ProgresoCaminoBrillante
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.tipsdesarrollo.caminobrillante.ProgresoCaminoBrillanteUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.caminobrillante.progress.model.ProgresoActualCaminoBrillanteModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.caminobrillante.progress.model.TipProgresoCaminoBrillanteModel
import biz.belcorp.salesforce.core.utils.doAsync
import biz.belcorp.salesforce.core.utils.uiThread

class ProgresoCaminoBrillantePresenter(
    private val view: ProgresoCaminoBrillanteView,
    private val useCase: ProgresoCaminoBrillanteUseCase,
    private val mapper: ProgresoCaminoBrillanteMapper
) {

    fun obtenerProgreso(personaId: Long, rol: Rol) {
        useCase.obtenerProgreso(
            ProgresoCaminoBrillanteUseCase.Params(
                personaId,
                rol,
                ProgresoCaminoBrillanteSubscriber()
            )
        )
    }

    inner class ProgresoCaminoBrillanteSubscriber : BaseSingleObserver<ProgresoCaminoBrillante>() {
        override fun onSuccess(t: ProgresoCaminoBrillante) {
            doAsync {
                val modelo = mapper.parse(t)
                uiThread {
                    decidirMostrarNiveles(modelo.niveles)
                    decidirMostrarProgreso(modelo.progreso)
                    decidirMostrarTipsProgreso(modelo.tipsProgreso)
                }
            }
        }
    }

    private fun decidirMostrarNiveles(niveles: List<StepModel>) {
        if (niveles.isNotEmpty()) view.mostrarNivelesCaminoBrillante(niveles)
        else view.ocultarNivelesCaminoBrillante()
    }

    private fun decidirMostrarProgreso(progreso: ProgresoActualCaminoBrillanteModel) {
        if (progreso.esValido) view.mostrarProgresoCaminoBrillante(progreso)
        else view.ocultarProgresoCaminoBrillante()
    }

    private fun decidirMostrarTipsProgreso(tipsProgreso: List<TipProgresoCaminoBrillanteModel>) {
        if (tipsProgreso.isNotEmpty()) view.mostrarTipsCaminoBrillante(tipsProgreso)
        else view.ocultarTipsCaminoBrillante()
    }
}
