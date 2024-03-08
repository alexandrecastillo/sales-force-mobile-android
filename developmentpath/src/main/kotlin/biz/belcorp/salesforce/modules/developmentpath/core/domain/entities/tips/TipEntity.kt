package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tips

import biz.belcorp.salesforce.core.utils.ColorUtils
import com.google.gson.annotations.SerializedName

data class TipEntity(@SerializedName(ID)
                     val id: Long = 0,
                     @SerializedName(DESCRIPTION)
                     val descripcion: String? = null,
                     @SerializedName(COLORES)
                     val colores: List<Int> = emptyList(),
                     @SerializedName(ORDEN)
                     val orden: Int = -1) {

    val tipoColores get() = colores.map { color -> ColorUtils.crearTipoColor(color) }

    companion object {
        private const val ID = "id"
        private const val DESCRIPTION = "descripcion"
        private const val COLORES = "colores"
        private const val ORDEN = "orden"
    }
}
