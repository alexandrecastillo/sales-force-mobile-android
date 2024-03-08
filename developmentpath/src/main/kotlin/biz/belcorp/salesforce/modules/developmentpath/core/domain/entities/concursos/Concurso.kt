package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.concursos

class Concurso(
    val personaId: Long,
    val region: String,
    val zona: String,
    val seccion: String,
    val data: List<ConcursoDetalle>
) {

    companion object {
        const val TITULO = "Concursos %s"
        const val DESCRIPCION_ES_BRILLANTE = "Ya pertenece al Programa brillante"
        const val DESCRIPCION_COMPLETO = "|Logró el puntaje para la bonificación|"
        const val DESCRIPCION_REGALO = "|%s pts| para llevarse el regalo"
        const val DESCRIPCION_BRILLANTE = "|%s pts| para pertenecer al Programa Brillante"
        const val DEFAULT_ID = 1
        const val DEFAULT_ORDEN = 3
    }
}
