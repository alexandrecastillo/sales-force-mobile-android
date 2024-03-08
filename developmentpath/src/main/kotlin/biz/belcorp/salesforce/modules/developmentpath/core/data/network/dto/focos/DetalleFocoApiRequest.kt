package biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.focos

import com.google.gson.annotations.SerializedName

data class DetalleFocoApiRequest(
    @SerializedName("Campania")
    val campania: String?,
    @SerializedName("Region")
    val region: String?,
    @SerializedName("Zona")
    val zona: String?,
    @SerializedName("Seccion")
    val seccion: String?,
    @SerializedName("Usuario")
    val usuario: String?,
    @SerializedName("Focos")
    val focos: String?
)
