package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsoferta

import com.google.gson.annotations.SerializedName

data class OfertaEntity(@SerializedName(CUV)
                        val cuv: String? = null,
                        @SerializedName(TIPO)
                        val tipo: String? = null,
                        @SerializedName(MARCA)
                        val marca: String? = null,
                        @SerializedName(PRODUCTO_NOMBRE)
                        val productoNombre: String? = null,
                        @SerializedName(PRODUCTO_PRECIO)
                        val productoPrecio: String? = null,
                        @SerializedName(PRODUCTO_IMAGEN)
                        val productoImagen: String? = null,
                        @SerializedName(PRODUCTO_URL)
                        val productoUrl: String? = null) {
    companion object {
        private const val CUV = "cuv"
        private const val TIPO = "tipo"
        private const val MARCA = "marca"
        private const val PRODUCTO_NOMBRE = "producto_nombre"
        private const val PRODUCTO_PRECIO = "producto_precio"
        private const val PRODUCTO_IMAGEN = "producto_imagen"
        private const val PRODUCTO_URL = "producto_url"
    }
}
