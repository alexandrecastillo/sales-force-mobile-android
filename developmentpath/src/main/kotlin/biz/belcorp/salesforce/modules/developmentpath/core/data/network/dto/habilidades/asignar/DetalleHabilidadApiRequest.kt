package biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.habilidades.asignar

import com.google.gson.annotations.SerializedName

class DetalleHabilidadApiRequest(
    @SerializedName("Campania")
    val campania: String,
    @SerializedName("Habilidades")
    val habilidades: String?,
    @SerializedName("Region")
    val region: String,
    @SerializedName("Zona")
    val zona: String?,
    @SerializedName("Seccion")
    val seccion: String?,
    @SerializedName("Usuario")
    val usuario: String = "001075381")
