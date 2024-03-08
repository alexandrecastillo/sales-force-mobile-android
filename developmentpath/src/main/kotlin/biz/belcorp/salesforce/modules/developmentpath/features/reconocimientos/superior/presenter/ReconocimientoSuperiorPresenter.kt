package biz.belcorp.salesforce.modules.developmentpath.features.reconocimientos.superior.presenter

import biz.belcorp.salesforce.core.features.handlers.observers.BaseCompletableObserver
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.reconocimiento.ReconocimientoASuperior
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.reconocimiento.ReconocimientoConMadre
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.reconocimiento.ManageReconcocimientosUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.reconocimientos.superior.model.ReconocimientoDetalleMapper
import biz.belcorp.salesforce.modules.developmentpath.features.reconocimientos.superior.view.ReconocimientoASuperiorDetalleView

class ReconocimientoASuperiorDetallePresenter(
    private var view: ReconocimientoASuperiorDetalleView? = null,
    private val useCase: ManageReconcocimientosUseCase,
    private val mapper: ReconocimientoDetalleMapper
) {

    private var idReconocimiento: Long = 0

    private var reconocimiento: ReconocimientoASuperior? = null

    fun recuperar(idReconocimiento: Long) {
        this.idReconocimiento = idReconocimiento
        recuperarDatos()
    }

    fun establecerVista(view: ReconocimientoASuperiorDetalleView) {
        this.view = view
    }

    private fun recuperarDatos() {
        return useCase.obtenerReconocimiento(idReconocimiento, ReconocimientoSubscriber())
    }

    private inner class ReconocimientoSubscriber : BaseSingleObserver<ReconocimientoConMadre>() {

        override fun onSuccess(t: ReconocimientoConMadre) {
            reconocimiento = t.reconocimiento
            if ((reconocimiento as ReconocimientoASuperior).pendienteReconocimiento)
                view?.pintarNoReconocida(mapper.parse(t))
            else
                view?.pintarReconocida(mapper.parse(t))
        }
    }

    fun grabar(idReconocimiento: Long, valoracion: Int, comentarios: String) {
        val reconocimientoAGrabar = mapper.parse(idReconocimiento, valoracion, comentarios)
        useCase.reconocer(reconocimientoAGrabar, CalificarVisitaSubscriber())
    }

    private inner class CalificarVisitaSubscriber : BaseCompletableObserver() {

        override fun onComplete() {
            view?.alGrabarseReconocimiento(reconocimiento?.nombreReconocida ?: "")
        }
    }
}
