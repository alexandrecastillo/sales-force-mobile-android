package biz.belcorp.salesforce.modules.developmentpath.core.data.entities.acuerdos.consolidado

import com.google.gson.annotations.SerializedName

data class CumplimientoModel(
    @SerializedName("campania")
    val campania: String,
    @SerializedName("cumplimiento")
    val cumplimiento: Boolean
)
