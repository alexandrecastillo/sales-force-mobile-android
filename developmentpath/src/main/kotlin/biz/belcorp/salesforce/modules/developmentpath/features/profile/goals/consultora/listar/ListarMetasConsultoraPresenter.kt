package biz.belcorp.salesforce.modules.developmentpath.features.profile.goals.consultora.listar

import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.metas.AdministrarMetaConsultoraUseCase
import biz.belcorp.salesforce.core.utils.doAsync
import biz.belcorp.salesforce.core.utils.uiThread

class ListarMetasConsultoraPresenter(
    private val view: ListarMetasConsultorasView,
    private val useCase: AdministrarMetaConsultoraUseCase,
    private val metaMapper: ListarMetasConsultoraMapper
) {

    var model = ListarMetasConsultoraModel.inicializar()

    fun obtenerMetasConsultora(personaId: Long) {
        useCase.obtener(personaId, AdministrarMetaConsultoraSubscriber())
    }

    private fun pintarMetas() {
        view.pintarMetas(model.metas)
    }

    private fun mostrarAgregarMetasSegunModelo() {
        if (model.sePuedenCrearMetas) {
            view.mostrarBotonAgregarMeta()
        } else {
            view.ocultarBotonAgregarMeta()
        }
    }

    private inner class AdministrarMetaConsultoraSubscriber :
        BaseSingleObserver<AdministrarMetaConsultoraUseCase.ObtenerResponse>() {

        override fun onSuccess(t: AdministrarMetaConsultoraUseCase.ObtenerResponse) {
            doAsync {
                model.metas = metaMapper.parse(t.metas)
                model.sePuedenCrearMetas = t.sePuedeCrear
                uiThread {
                    pintarMetas()
                    mostrarAgregarMetasSegunModelo()
                }
            }
        }
    }
}
