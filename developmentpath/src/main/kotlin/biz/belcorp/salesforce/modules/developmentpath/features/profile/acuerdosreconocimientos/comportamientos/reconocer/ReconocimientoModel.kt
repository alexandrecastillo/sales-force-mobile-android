package biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.reconocer

data class ReconocimientoModel(
    val id: Long,
    val iconoId: Int,
    val descripcion: String,
    val seleccionado: Boolean
)
