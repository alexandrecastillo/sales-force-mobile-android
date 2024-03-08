package biz.belcorp.salesforce.modules.developmentpath.features.ingresosextra.section

import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.features.handlers.observers.BaseCompletableObserver
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.utils.Logger
import biz.belcorp.salesforce.core.utils.doAsync
import biz.belcorp.salesforce.core.utils.uiThread
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.ingresosextra.OtraMarca
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.profile.ingresosextra.IngresosExtraUseCase

class IngresosExtraPresenter(
    private val view: IngresosExtraContract.View,
    private val useCase: IngresosExtraUseCase,
    private val mapper: OtraMarcaModelMapper,
) : IngresosExtraContract.Presenter {

    override fun obtener(personaId: Long, personaRol: Rol) {
            useCase.obtenerDatosFront(personaId, personaRol, OtrasMarcasSubscriber())
    }

    override fun obtenerMarcasNofront(personaId: Long, personaRol: Rol) {
        useCase.obtenerDatosMoreChecked(personaId, personaRol, OtrasMarcasNoFrontSubscriber())
    }

    override fun check(item: OtraMarcaModel) {
        useCase.actualizarCheck(mapper.map(item), CheckModelSubscriber())
    }

    inner class OtrasMarcasSubscriber : BaseSingleObserver<List<OtraMarca>>() {
        override fun onSuccess(t: List<OtraMarca>) {
            doAsync {
                val viewModel = mapper.reverseMap(t)
                uiThread {
                    view.mostrarData(viewModel)
                }
            }
        }

        override fun onError(e: Throwable) {
            e.printStackTrace()
            Logger.loge(TAG, e.localizedMessage, e)
        }
    }

    inner class OtrasMarcasNoFrontSubscriber : BaseSingleObserver<List<OtraMarca>>() {
        override fun onSuccess(t: List<OtraMarca>) {
            doAsync {
                val viewModel = mapper.reverseMap(t)
                uiThread {
                    view.mostrarDataNoFront(viewModel)
                }
            }
        }

        override fun onError(e: Throwable) {
            e.printStackTrace()
            Logger.loge(TAG, e.localizedMessage, e)
        }
    }


    private inner class CheckModelSubscriber : BaseCompletableObserver() {
        override fun onError(e: Throwable) {
            e.printStackTrace()
            Logger.loge(TAG, e.localizedMessage, e)
        }
    }

    companion object {
        private val TAG = IngresosExtraPresenter::class.java.simpleName
    }
}
