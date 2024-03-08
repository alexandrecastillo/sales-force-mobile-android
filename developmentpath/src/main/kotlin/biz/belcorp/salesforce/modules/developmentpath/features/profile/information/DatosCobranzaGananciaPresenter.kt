package biz.belcorp.salesforce.modules.developmentpath.features.profile.information

import biz.belcorp.salesforce.core.features.handlers.observers.BaseObserver
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.cobranza.CobranzaYGanancia
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PersonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.cobranza.CobranzaYGananciaUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.profile.cobranzaganancia.CobranzaGananciaMapper

class DatosCobranzaGananciaPresenter(
    private val view: DatosCobranzaGananciaView,
    private val useCase: CobranzaYGananciaUseCase,
    private val mapper: CobranzaGananciaMapper
) {

    fun obtener(identificador: PersonaRdd.Identificador) {
        val requestCobranza = CobranzaYGananciaUseCase.RequestCobranzaGanancia(
            identificador,
            ObtenerCobranzaGananciaObserver()
        )
        useCase.obtenerCobranzaGanancia(requestCobranza)
    }

    private inner class ObtenerCobranzaGananciaObserver : BaseObserver<CobranzaYGanancia>() {

        override fun onNext(t: CobranzaYGanancia) {
            super.onNext(t)
            val model = mapper.parseDataToDomain(t)
            view.pintarDatosCobranzaGanancia(model)
        }

        override fun onError(e: Throwable) {
            e.printStackTrace()
            view.cobranzaGananciaError()
        }
    }
}
