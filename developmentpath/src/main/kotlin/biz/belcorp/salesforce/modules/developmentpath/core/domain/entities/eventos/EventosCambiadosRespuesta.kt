package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos

sealed class EventosCambiadosRespuesta {

    class Cambiados(
        val nuevos: List<EventoRdd>,
        val editados: List<EventoRdd>,
        val eliminados: List<EventoRdd>
    ) : EventosCambiadosRespuesta() {

        val existenCambios = nuevos.isNotEmpty() ||
            editados.isNotEmpty() ||
            eliminados.isNotEmpty()
    }

    object Ninguno : EventosCambiadosRespuesta()
}
