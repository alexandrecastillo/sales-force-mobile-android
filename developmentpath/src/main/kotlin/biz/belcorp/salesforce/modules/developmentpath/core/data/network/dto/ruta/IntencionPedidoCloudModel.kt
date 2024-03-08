package biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.ruta

import com.google.gson.annotations.SerializedName

class IntencionPedidoCloudModel(
    @SerializedName("campania")
    val campania: String,

    @SerializedName("region")
    val region: String,

    @SerializedName("zona")
    val zona: String,

    @SerializedName("seccion")
    var seccion: String,

    @SerializedName("consultoraID")
    var consultoraId: Long,

    @SerializedName("pasaPedido")
    var pasaPedido: Boolean,

    @SerializedName("usuarioCreacion")
    var usuario: String)
