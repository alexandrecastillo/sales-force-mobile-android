package biz.belcorp.salesforce.modules.postulants.features.indicador.components.listado.dialog

import biz.belcorp.salesforce.core.utils.Logger
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.ParametroUnete
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.UneteTipoParametro
import biz.belcorp.salesforce.modules.postulants.core.domain.usecases.ParametroUneteUseCase
import biz.belcorp.salesforce.modules.postulants.core.domain.usecases.PostulantesUseCase
import biz.belcorp.salesforce.modules.postulants.features.base.Presenter
import biz.belcorp.salesforce.modules.postulants.features.mapper.ParametroUneteModelDataMapper


class RechazoPostulantePresenter
constructor(
    private val useCase: PostulantesUseCase,
    private val parametroUneteUseCase: ParametroUneteUseCase,
    private val mapParametroUnete: ParametroUneteModelDataMapper
) : Presenter {

    var mView: RechazoPostulanteView? = null

    fun setView(view: RechazoPostulanteView) {
        mView = view
    }


    private inner class UpdateEstadoRechazadaSubscriber : BaseSingleObserver<Boolean>() {

        override fun onError(e: Throwable) {
            mView?.hideLoading()
            Logger.loge(javaClass.simpleName, e.message)
        }

        override fun onSuccess(t: Boolean) {
            mView?.hideLoading()
            if (t) {
                mView?.showPostulanteRechaza()
            }
        }
    }

    private inner class MotivoRechazoSubscriber : BaseSingleObserver<List<ParametroUnete>>() {

        override fun onError(e: Throwable) {
            Logger.loge(javaClass.simpleName, e.message)
        }

        override fun onSuccess(t: List<ParametroUnete>) {
            val y = mapParametroUnete.map(t)
            mView?.showMotivoRechazo(y)
        }
    }

    override fun destroy() {
        useCase.dispose()
        mView = null
    }

    fun getMotivoRechazos() {
        parametroUneteUseCase.list(
            MotivoRechazoSubscriber(),
            UneteTipoParametro.MOTIVORECHAZOSE.tipo
        )
    }

    fun getMotivoRechazoProactivos() {
        parametroUneteUseCase.list(
            MotivoRechazoSubscriber(),
            UneteTipoParametro.RECHAZOPROACTIVAS.tipo
        )
    }

    fun updateEstadoRechazada(
        pais: String, solicitudPostulanteID: Int, estadoPostulante: Int,
        subEstadoPostulante: Int?, tipoRechazo: String, motivoRechazo: String
    ) {
        mView?.showLoading()
        this.useCase.updateEstado(
            UpdateEstadoRechazadaSubscriber(),
            pais,
            solicitudPostulanteID,
            estadoPostulante,
            subEstadoPostulante,
            tipoRechazo,
            motivoRechazo
        )
    }

    fun updateEstadoPreRechazada(pais: String, solicitudPrePostulanteID: Int, tipoRechazo: String,
                                 motivoRechazo: String) {
        mView?.showLoading()
        this.useCase.updateEstadoPre(
            UpdateEstadoRechazadaSubscriber(),
            pais,
            solicitudPrePostulanteID,
            tipoRechazo,
            motivoRechazo
        )
    }

}
