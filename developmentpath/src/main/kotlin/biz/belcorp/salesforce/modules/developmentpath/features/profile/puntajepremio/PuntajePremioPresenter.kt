package biz.belcorp.salesforce.modules.developmentpath.features.profile.puntajepremio

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.puntaje.PuntajePremio
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.puntaje.PuntajePremioUseCase

class PuntajePremioPresenter(private val puntajePremioUseCase: PuntajePremioUseCase) {

    private var view: PuntajePremioView? = null
    var personaId: Long = 0L

    fun establecerVista(view: PuntajePremioView) {
        this.view = view
    }

    fun destruirVista() {
        this.view = null
    }

    fun obtener(personaId: Long) {
        puntajePremioUseCase.obtener(personaId, PuntajePremioSubscriber())
        puntajePremioUseCase.obtenerCamapniaActual(personaId, CampaniaSubscriber())
    }

    private inner class PuntajePremioSubscriber : BaseSingleObserver<PuntajePremio>() {

        override fun onError(e: Throwable) {
            view?.pintarError()
        }

        override fun onSuccess(t: PuntajePremio) {
            if (t.existeMeta) pintarDatos(parse(t)) else view?.pintarError()
        }

        private fun parse(puntaje: PuntajePremio) = PuntajePremioModel(
            nivel = puntaje.nivel,
            puntosTotal = puntaje.puntosMeta,
            puntosObtenidos = puntaje.puntosObtenidos,
            puntosRestantes = puntaje.puntosRestantes,
            premio = puntaje.premio,
            porcentajeAvance = puntaje.porcentaje,
            haAlcanzado = puntaje.haAlcanzadoMeta
        )
    }

    private inner class CampaniaSubscriber : BaseSingleObserver<Campania>() {

        override fun onSuccess(t: Campania) {
            view?.pintarCampania(t.obtenerNombreNumerico())
        }
    }

    private fun pintarDatos(modelo: PuntajePremioModel) =
        if (modelo.haAlcanzado) {
            view?.pintarParaPuntajeAlcanzado(modelo)
        } else {
            view?.pintarParaPuntajeNoAlcanzado(modelo)
        }
}
