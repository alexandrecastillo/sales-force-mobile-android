package biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.sync

data class DatosConsultoraResponse(
    val identifier: Any,
    val fechaRequest: String,
    val httpStatus: Int,
    val resultado: List<Resultado>,
    val fechaResponse: String,
    val version: String
) {
    data class Resultado(
        val codigo: String?,
        val resultadoEmail: Int?,
        val resultadoTelefono: Int?,
        val resultadoTipoTelefonoDefault: Int?
    )

}

