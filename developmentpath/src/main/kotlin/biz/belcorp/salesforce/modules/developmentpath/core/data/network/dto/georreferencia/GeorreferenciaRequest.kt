package biz.belcorp.ffvv.data.net.dto.georreferencia

import com.google.gson.annotations.SerializedName


class GeorreferenciaRequest {

    @SerializedName("Codigo")
    var codigo: String? = null

    @SerializedName("Latitud")
    var latitud: String? = null

    @SerializedName("Longitud")
    var longitud: String? = null

}