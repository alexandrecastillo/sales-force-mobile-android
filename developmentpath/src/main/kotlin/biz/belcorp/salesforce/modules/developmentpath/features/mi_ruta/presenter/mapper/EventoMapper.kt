package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.presenter.mapper

import biz.belcorp.salesforce.core.utils.parseToHoursDescription
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos.*
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model.EventoModel

class EventoMapper {

    fun map(eventos: List<Evento>) = eventos.map { map(it) }

    fun map(evento: Evento): EventoModel {
        return when (evento) {
            is Feriado ->
                EventoModel(esFeriado =  true,
                            titulo = "")
            is HitoEnRegion ->
                EventoModel(esFeriado = false,
                            titulo = "${evento.titulo} - ${evento.codigoZona}")
            is Hito ->
                EventoModel(esFeriado = false,
                            titulo = evento.titulo ?: "")
            is EventoRdd ->
                EventoModel.Rdd(id = evento.eventoRddXUa.id,
                                esFeriado = false,
                                titulo = evento.descripcion ?: "",
                                subtituloHoras = convertirIntervaloAString(evento),
                                mostrarSubtituloHoras = !evento.esTodoElDia,
                                mostrarSubtituloTodoElDia = evento.esTodoElDia,
                                registrar = evento.registrar)
            else ->
                EventoModel(esFeriado = false,
                            titulo =  "")
        }
    }

    private fun convertirIntervaloAString(evento: EventoRdd) =
            "${evento.fechaInicio.time.parseToHoursDescription()} " +
                    "- ${evento.fechaFin?.time?.parseToHoursDescription() ?: ""}"
}
