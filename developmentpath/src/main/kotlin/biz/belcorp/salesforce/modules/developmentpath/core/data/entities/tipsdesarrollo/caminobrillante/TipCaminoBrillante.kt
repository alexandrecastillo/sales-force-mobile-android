package biz.belcorp.salesforce.modules.developmentpath.core.data.entities.tipsdesarrollo.caminobrillante

import biz.belcorp.salesforce.core.utils.ColorUtils
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.caminobrillante.TipProgresoCaminoBrillante
import com.google.gson.annotations.SerializedName

class TipCaminoBrillante(@SerializedName(TEXTO)
                         val titulo: String,
                         @SerializedName(ICONID)
                         val icono: Int,
                         @SerializedName(COLORES)
                         val colores: List<Int> = emptyList()) {

    val tipoColores get() = colores.map { color -> ColorUtils.crearTipoColor(color) }
    val tipoIcono get() = obtenerTipoIcono(icono)

    private fun obtenerTipoIcono(icono: Int): TipProgresoCaminoBrillante.TipoImagen {
        return when (icono) {
            TipProgresoCaminoBrillante.TipoImagen.HITO.valor -> TipProgresoCaminoBrillante.TipoImagen.HITO
            TipProgresoCaminoBrillante.TipoImagen.META.valor -> TipProgresoCaminoBrillante.TipoImagen.META
            else -> TipProgresoCaminoBrillante.TipoImagen.CONSTANCIA
        }
    }

    companion object {
        private const val TEXTO = "texto"
        private const val ICONID = "iconoId"
        private const val COLORES = "colores"
    }
}
