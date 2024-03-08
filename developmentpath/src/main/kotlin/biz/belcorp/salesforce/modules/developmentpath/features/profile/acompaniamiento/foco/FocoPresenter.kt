package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.foco

import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.features.handlers.observers.BaseObserver
import biz.belcorp.salesforce.modules.developmentpath.common.view.FocoView
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.focos.CabeceraFoco
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.focos.FocoUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.tips.ObtenerTipsVisitaUseCase

class FocoPresenter(
    private val useCase: FocoUseCase,
    private val useCaseTips: ObtenerTipsVisitaUseCase,
    private val focoMapper: FocoModelMapper
) {

    private var view: FocoView? = null
    private var focoPersonaView: FocoPersonaView? = null

    fun setView(vw: FocoView) {
        this.view = vw
    }

    fun establecerView(view: FocoPersonaView) {
        focoPersonaView = view
    }

    fun destruirFocoView() {
        focoPersonaView = null
    }

    fun tieneFoco(personaId: Long, rol: Rol) {
        val request = ObtenerTipsVisitaUseCase.RequestTip(
            personaId = personaId,
            rol = rol,
            subscriber = RecuperarTipIdSubscriber()
        )

        useCaseTips.recuperarTipId(request)
    }

    fun obtenerFocos(segmentoId: Int) {
        useCase.obtenerFocos(GetFocosSubscriber(), segmentoId)
    }

    private inner class GetFocosSubscriber : BaseObserver<List<CabeceraFoco>>() {

        override fun onNext(t: List<CabeceraFoco>) {
            view?.mostrarFocos(focoMapper.parse(t))
        }
    }

    private inner class TieneFocosSubscriber : BaseObserver<Boolean>() {

        override fun onNext(t: Boolean) {
            if (t) {
                focoPersonaView?.mostrarBanner()
            } else {
                focoPersonaView?.ocultarBanner()
            }
        }
    }

    private inner class RecuperarTipIdSubscriber : BaseObserver<Long>() {

        override fun onNext(t: Long) {
            if (t != -1L) {
                focoPersonaView?.recuperarTipId(t)
                useCase.tieneFocos(TieneFocosSubscriber(), t.toInt())
            } else {
                focoPersonaView?.ocultarBanner()
            }
        }
    }
}
