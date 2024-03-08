package biz.belcorp.salesforce.modules.developmentpath.features.profile.cobranzaestadodecuenta.cobranza

import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.cobranza.ObtenerDatosCobranzaUseCase
import biz.belcorp.salesforce.core.utils.doAsync
import biz.belcorp.salesforce.core.utils.uiThread

class DatosCobranzaPresenter(
    private val view: DatosCobranzaContract.View,
    private val useCase: ObtenerDatosCobranzaUseCase,
    private val mapper: DatosCobranzaMapper
) : DatosCobranzaContract.Presenter {

    override fun obtener(personaId: Long, rol: Rol) {
        useCase.obtener(personaId, rol, DatosCobranzaSubscriber())
    }

    inner class DatosCobranzaSubscriber :
        BaseSingleObserver<ObtenerDatosCobranzaUseCase.DatosCobranza>() {
        override fun onSuccess(t: ObtenerDatosCobranzaUseCase.DatosCobranza) {
            doAsync {
                val datosCobranza = mapper.parse(t)
                val modelo = DatosCobranzaGridModelMapper(datosCobranza).map()
                uiThread {
                    view.pintarContenedorInfo(modelo)
                }
            }
        }
    }
}
