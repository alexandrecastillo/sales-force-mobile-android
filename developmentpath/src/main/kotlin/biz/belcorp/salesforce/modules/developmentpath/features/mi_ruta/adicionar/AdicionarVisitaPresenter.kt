package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.adicionar

import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.features.handlers.observers.BaseObserver
import biz.belcorp.salesforce.core.utils.parseToHoursDescription
import biz.belcorp.salesforce.core.utils.parseToShortYearString
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PersonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.visitas.AdicionarVisitaUseCase
import java.util.*

class AdicionarVisitaPresenter(
    private val view: AdicionarVisitaView,
    private val adicionarVisitaUseCase: AdicionarVisitaUseCase
) {

    fun adicionar(personaId: Long, rol: Rol, fecha: Date) {
        val request = AdicionarVisitaUseCase.Request(
            PersonaRdd.Identificador(personaId, rol),
            fecha,
            AdicionarSubscriber()
        )
        adicionarVisitaUseCase.adicionarVisita(request)
    }

    private inner class AdicionarSubscriber : BaseObserver<Date>() {
        override fun onNext(t: Date) {
            view.mostrarExito(
                t.parseToShortYearString(),
                t.parseToHoursDescription().toLowerCase(Locale.getDefault())
            )
            view.notificarCambioPlan()
        }

        override fun onError(exception: Throwable) {
            view.mostrarError(exception.localizedMessage.orEmpty())
        }
    }
}
