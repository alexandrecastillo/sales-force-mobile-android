package biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.acuerdos.ultimascampanias.consolidado

import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.acuerdos.ConsolidadoAcuerdosUseCase
import biz.belcorp.salesforce.modules.developmentpath.utils.prefijoConNumeroCampania

class ConsolidadoAcuerdosPresenter(
    private val view: ConsolidadoAcuerdosView,
    private val useCase: ConsolidadoAcuerdosUseCase
) {

    fun obtener(personaId: Long, rol: Rol) {
        val request = ConsolidadoAcuerdosUseCase.ObtenerRequest(
            personaId,
            rol,
            ObtenerSubscriber()
        )
        useCase.obtener(request)
    }

    inner class ObtenerSubscriber : BaseSingleObserver<ConsolidadoAcuerdosUseCase.Response>() {
        override fun onSuccess(t: ConsolidadoAcuerdosUseCase.Response) {
            val modelos = t.cumplimientos.map {
                CumplimientoModel(
                    it.campania.codigo.prefijoConNumeroCampania(),
                    it.estado
                )
            }
            view.pintarCumplimientos(modelos)
        }
    }
}
