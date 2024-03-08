package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.concursos

data class ConcursoViewModel(
    val tipo: String,
    val iconoProgreso: Int,
    val nivel: String,
    val descripcionPremio: String,
    val puntosNivel: Long,
    val puntosAcumulados: Long,
    val puntosFaltantes: Long,
    val descripcionProgreso: String,
    val imagenUrl: String
) {
    fun nivelAlcanzado() = puntosFaltantes == 0L
    fun perteneceCaminoBrillante() = nivel.isNotEmpty()
}
