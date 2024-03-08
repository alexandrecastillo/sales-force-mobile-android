package biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.concursos

import com.google.gson.annotations.SerializedName

class ConcursoData(
    @SerializedName(TIPO)
    val tipo: String,
    @SerializedName(NIVEL)
    val nivel: String,
    @SerializedName(DESCRIPCION_PREMIO)
    val descripcionPremio: String,
    @SerializedName(PUNTOS_NIVEL)
    val puntosNivel: Long,
    @SerializedName(PUNTOS_ACUMULADOS)
    val puntosAcumulados: Long,
    @SerializedName(PUNTOS_FALTANTES)
    val puntosFaltantes: Long,
    @SerializedName(DESCRIPCION_PROGRESO)
    val descripcionProgreso: String,
    @SerializedName(IMAGEN_URL)
    val imagenUrl: String) {

    companion object {
        private const val TIPO = "tipo"
        private const val NIVEL = "nivel"
        private const val DESCRIPCION_PREMIO = "descripcionPremio"
        private const val PUNTOS_NIVEL = "puntosNivel"
        private const val PUNTOS_ACUMULADOS = "puntosAcumulados"
        private const val PUNTOS_FALTANTES = "puntosFaltantes"
        private const val DESCRIPCION_PROGRESO = "descripcionProgreso"
        private const val IMAGEN_URL = "imageUrl"
    }
}
