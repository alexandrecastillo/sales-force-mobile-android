package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.concursos

class ConcursoDetalle(
    val tipo: String,
    val nivel: String,
    val descripcionPremio: String,
    val puntosNivel: Long,
    val puntosAcumulados: Long,
    val puntosFaltantes: Long,
    val descripcionProgreso: String,
    val imagenUrl: String
) {

    fun completoPuntaje() = puntosFaltantes == 0L

    fun tieneNivel() = nivel.isNotEmpty()
}
