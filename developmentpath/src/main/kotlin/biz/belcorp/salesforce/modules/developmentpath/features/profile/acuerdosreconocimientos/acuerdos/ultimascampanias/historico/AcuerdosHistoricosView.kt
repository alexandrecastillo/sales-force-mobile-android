package biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.acuerdos.ultimascampanias.historico

import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.acuerdos.AcuerdosHistoricosUseCase

interface AcuerdosHistoricosView {
    fun pintarAcuerdosNoCumplidos(modelos: List<AcuerdosPorCampaniaModel>)
    fun pintarAcuerdosCumplidos(modelos: List<AcuerdosPorCampaniaModel>)
    fun mostrarSinAcuerdosCumplidos()
    fun ocultarSinAcuerdosCumplidos()
    fun mostrarSinAcuerdosNoCumplidos()
    fun ocultarSinAcuerdosNoCumplidos()
    fun mostrarSinAcuerdos()
    fun ocultarSinAcuerdos()
    fun mostrarTituloAcuerdosNoCumplidos()
    fun mostrarTituloAcuerdosCumplidos()
    fun ocultarTituloAcuerdosCumplidos()
    fun notificarCambioEnAcuerdos()
    fun eventoaRegistarAnalytics(evento: AcuerdosHistoricosUseCase.EventoAnalyticsResponse?)
}
