package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model

open class EventoModel(val esFeriado: Boolean,
                       val titulo: String) {

    class Rdd(val id: Long,
              esFeriado: Boolean,
              titulo: String,
              val subtituloHoras: String,
              val mostrarSubtituloHoras: Boolean,
              val mostrarSubtituloTodoElDia: Boolean,
              val registrar: Boolean) : EventoModel(esFeriado, titulo)

}
