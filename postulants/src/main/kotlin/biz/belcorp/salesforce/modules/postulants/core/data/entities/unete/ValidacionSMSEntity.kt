package biz.belcorp.salesforce.modules.postulants.core.data.entities.unete

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ValidacionSMSEntity(
    @SerialName("pais")
    val pais: String,
    @SerialName("solicitudPostulanteID")
    val solicitudPostulanteID: Int,
    @SerialName("numeroCelular")
    val numeroCelular: String,
    @SerialName("numeroDocumento")
    val numeroDocumento: String,
    @SerialName("nombreCompleto")
    val nombreCompleto: String
)
