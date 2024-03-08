package biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.focos

import com.google.gson.annotations.SerializedName

data class DetalleFocoSEApiModel(
    @SerializedName("ConsultoraID") val consultoraID: Long,
    @SerializedName("Campania") val campania: String,
    @SerializedName("Focos") val focos: String?
)
