package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.ventaganancia

import biz.belcorp.salesforce.core.utils.ColorUtils
import com.google.gson.annotations.SerializedName

data class VentaGananciaEntity(@SerializedName(VENTA_MONTO)
                               val ventaMonto: ValorEntity? = null,
                               @SerializedName(VENTA_DESCRIPCION)
                               val ventaDescripcion: ValorEntity? = null,
                               @SerializedName(GANANCIA_MONTO)
                               val gananciaMonto: ValorEntity? = null,
                               @SerializedName(GANANCIA_DESCRIPCION)
                               val gananciaDescripcion: ValorEntity? = null) {
    data class ValorEntity(@SerializedName(TEXTO)
                           val texto: String? = null,
                           @SerializedName(COLORES)
                           val colores: List<Int> = emptyList()) {

        val tipoColores get() = colores.map { color -> ColorUtils.crearTipoColor(color) }

        companion object {
            private const val TEXTO = "texto"
            private const val COLORES = "colores"
        }
    }

    companion object {
        private const val VENTA_MONTO = "ventaMonto"
        private const val VENTA_DESCRIPCION = "ventaDescripcion"
        private const val GANANCIA_MONTO = "gananciaMonto"
        private const val GANANCIA_DESCRIPCION = "gananciaDescripcion"
    }
}
