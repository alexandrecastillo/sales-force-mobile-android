package biz.belcorp.salesforce.modules.developmentpath.features.profile.cobranzaganancia

import biz.belcorp.salesforce.core.features.handlers.observers.BaseObserver
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.cobranza.CobranzaYGanancia
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PersonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.cobranza.CobranzaYGananciaUseCase

class CobranzaGananciaPresenter(
    private val view: DatosCobranzaGananciaContract.View,
    private val useCase: CobranzaYGananciaUseCase,
    private val mapper: CobranzaGananciaMapper
) {

    fun obtener(identificador: PersonaRdd.Identificador) {
        view.mostrarProgreso()
        val request = CobranzaYGananciaUseCase.RequestCobranzaGanancia(
            identificador,
            ObtenerCobranzaGananciaObserver()
        )
        useCase.obtenerCobranzaGanancia(request)
    }

    private inner class ObtenerCobranzaGananciaObserver : BaseObserver<CobranzaYGanancia>() {
        override fun onNext(t: CobranzaYGanancia) {
            super.onNext(t)
            val model = mapper.parseDataToDomain(t)
            view.pintarContenedorInfo(DatosCobranzaGananciaGridModelMapper(model).map(), model)
            view.ocultarProgreso(model)
        }

        override fun onError(exception: Throwable) {
            exception.printStackTrace()
            view.cobranzaGananciaError()
        }
    }
}
