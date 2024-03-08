package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.habilidades.propios.presenter

import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.utils.doAsync
import biz.belcorp.salesforce.core.utils.uiThread
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.habilidades.Habilidad
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.habilidades.PermitirAutoasignacionHabilidadesUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.habilidades.RecuperarMisHabilidadesUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.habilidades.propios.model.MiHabilidadViewMapper
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.habilidades.propios.view.MisHabilidadesView

class MisHabilidadesPresenter(
    private val view: MisHabilidadesView,
    private val recuperarMisHabilidadesUseCase: RecuperarMisHabilidadesUseCase,
    private val permitirAutoasignacionHabilidadesUseCase: PermitirAutoasignacionHabilidadesUseCase,
    private val mapper: MiHabilidadViewMapper
) {

    fun establecerVista(view: MisHabilidadesView) {
        validarAutosignacion()
    }

    fun recuperar() = recuperarMisHabilidadesUseCase.recuperar(MisHabilidadesSubscriber())

    private fun validarAutosignacion() =
        permitirAutoasignacionHabilidadesUseCase.permitir(PermisoAutoasignacionSubscriber())

    inner class MisHabilidadesSubscriber : BaseSingleObserver<List<Habilidad>>() {

        override fun onError(e: Throwable) {
            e.printStackTrace()
        }

        override fun onSuccess(t: List<Habilidad>) {
            if (t.isNotEmpty()) {
                doAsync {
                    val modelos = mapper.parse(t)
                    uiThread {
                        view.pintarHabilidades(modelos)
                    }
                }
            }
        }
    }

    inner class PermisoAutoasignacionSubscriber : BaseSingleObserver<Boolean>() {

        override fun onError(e: Throwable) = e.printStackTrace()

        override fun onSuccess(t: Boolean) {
            if (t) view.permitirAsignacion()
        }
    }
}
