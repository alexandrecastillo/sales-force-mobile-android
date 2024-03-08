package biz.belcorp.salesforce.modules.postulants.core.data.entities.crediticio

import com.google.gson.annotations.SerializedName


class PreCalificacionEntity {

    @SerializedName("IdSujeto")
    var idSujeto: Int = -1

    @SerializedName("Nombres")
    var nombres: String? = null

    @SerializedName("Dui")
    var dui: String? = null

    @SerializedName("Nit")
    var nit: String? = null

    @SerializedName("Iva")
    var iva: String? = null

    @SerializedName("Precalificalificacion")
    var precalificalificacion: String? = null

    @SerializedName("CodigoAprobacion")
    var codigoAprobacion: String? = null

    @SerializedName("CredPeorEstadoDescrip")
    var credPeorEstadoDescrip: String? = null

    @SerializedName("RazonRechazo")
    var razonRechazo: String? = null

    @SerializedName("Acreedores")
    var acreedores: String? = null

    @SerializedName("Mensaje")
    var mensaje: String? = null

}
