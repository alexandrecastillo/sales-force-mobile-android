package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.notas

data class Nota(
    var consultoraId: Int? = null,
    var notaId: Long? = null,
    var fechaComentario: String? = null,
    var region: String? = null,
    var zona: String? = null,
    var seccion: String? = null,
    var comentario: String? = null,
    var id: Int? = null
)
