package biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.habilidades.reconocer

import com.google.gson.annotations.SerializedName

class HabilidadReconocidaApiRequest(
    @SerializedName("Zona")
    val codigoZona: String?,
    @SerializedName("Habilidades")
    val habilidades: String?,
    @SerializedName("Campania")
    val campania: String,
    @SerializedName("Region")
    val codigoRegion: String,
    @SerializedName("Seccion")
    val codigoSeccion: String?,
    @SerializedName("UsuarioReconoce")
    val usuarioReconoce: String?,
    @SerializedName("UsuarioReconocida")
    val usuarioReconocida: String?)
