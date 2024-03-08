package biz.belcorp.salesforce.modules.developmentpath.features.reconocimientos.superior.model

data class ReconocimientoModel(
    val nombreReconocida: String,
    val valoracion: Int,
    val comentarios: String?,
    val estaPendiente: Boolean,
    val iniciales: String,
    val codRol: String,
    val ua: String
)
