package biz.belcorp.salesforce.modules.developmentpath.features.ingresosextra.more

import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.utils.Logger
import biz.belcorp.salesforce.core.utils.doAsync
import biz.belcorp.salesforce.core.utils.uiThread
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.ingresosextra.OtraMarca
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.profile.ingresosextra.IngresosExtraUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.ingresosextra.section.OtraMarcaModel
import biz.belcorp.salesforce.modules.developmentpath.features.ingresosextra.section.OtraMarcaModelMapper

class IngresosExtraOtrosPresenter(
    private val view: IngresosExtraOtrosContract.View,
    private val useCase: IngresosExtraUseCase,
    private val mapper: OtraMarcaModelMapper
) : IngresosExtraOtrosContract.Presenter {

    override fun obtener(personaId: Long, personaRol: Rol) {
        useCase.obtenerDatosMore(personaId, personaRol, OtrasMarcasSubscriber())
    }

    override fun checkList(data: List<OtraMarcaModel>) {

        useCase.actualizarCheckList(OtraMarcaModelMapper().map(data), CheckModelSubscriber())
    }

    inner class OtrasMarcasSubscriber : BaseSingleObserver<List<OtraMarca>>() {
        override fun onSuccess(t: List<OtraMarca>) {
            doAsync {
                val viewModel = mapper.reverseMap(t)
                uiThread {
                    view.mostrarOtraData(viewModel)
                }
            }
        }

        override fun onError(e: Throwable) {
            e.printStackTrace()
            Logger.loge(TAG, e.localizedMessage, e)
        }
    }

    private inner class CheckModelSubscriber : BaseSingleObserver<Boolean>() {
        override fun onSuccess(t: Boolean) {
            view.checkSuccess()

        }

        override fun onError(e: Throwable) {
            e.printStackTrace()
            view.checkFailure(e.localizedMessage ?: MESSAGE_ERROR)
            Logger.loge(TAG, e.localizedMessage, e)
        }
    }

    companion object {
        private val TAG = IngresosExtraOtrosPresenter::class.java.simpleName
        private const val MESSAGE_ERROR = "Ha ocurrido un error"
    }
}
