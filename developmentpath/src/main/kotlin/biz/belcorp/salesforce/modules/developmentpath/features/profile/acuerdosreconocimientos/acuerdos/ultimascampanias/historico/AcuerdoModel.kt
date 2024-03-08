package biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.acuerdos.ultimascampanias.historico

data class AcuerdoModel(
    val id: Long,
    val contenido: String,
    val fecha: String,
    val cumplido: Boolean
)
