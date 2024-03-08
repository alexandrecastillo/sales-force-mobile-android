package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas

class GrupoTipsVisita(
    val tituloGrupo: String?,
    val tituloVideo: String?,
    val urlVideo: String?
) {
    lateinit var tips: List<TipVisita>
}

class TipVisita(
    val id: Long,
    val descripcion: String?
) {
    lateinit var grupo: GrupoTipsVisita
}
