package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.concursos

import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.concursos.ConcursosUseCaseImpl
import biz.belcorp.salesforce.core.utils.doAsync
import biz.belcorp.salesforce.core.utils.uiThread

class ConcursosFragmentPresenter(
    private val view: ConcursosFragmentContract.View,
    private val useCase: ConcursosUseCaseImpl,
    private val mapper: ConcursoModelMapper
) : ConcursosFragmentContract.Presenter {

    override fun obtenerConcursos(personaId: Long, rol: Rol) {
        useCase.obtenerConcursos(
            ConcursosUseCaseImpl.Request(
                personaId,
                rol,
                subscriber = ObtenerConcursosObserver()
            )
        )
    }

    override fun obtenerConcursosPorTipo(personaId: Long, rol: Rol, tipo: String) {
        useCase.obtenerConcursosPorTipo(
            ConcursosUseCaseImpl.Request(
                personaId,
                rol,
                tipo,
                ObtenerConcursosObserver()
            )
        )
    }

    inner class ObtenerConcursosObserver : BaseSingleObserver<ConcursosUseCaseImpl.Response>() {
        override fun onSuccess(t: ConcursosUseCaseImpl.Response) {
            doAsync {
                val concursos = mapper.parse(t.concursos)
                uiThread {
                    if (concursos.isEmpty()) {
                        view.mostarSinDatos()
                    } else {
                        view.ocultarSinDatos()
                        view.mostarConcursos(concursos)
                    }
                }
            }
        }
    }
}
