package biz.belcorp.salesforce.modules.postulants.core.data.repository.postulante.mappers

import biz.belcorp.salesforce.modules.postulants.core.data.network.dto.unete.PostulanteAptaApiResponse
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.PostulanteAptaResponse

class PostulanteAptaDataMapper {

    fun parse(response: PostulanteAptaApiResponse.PostulanteApta): PostulanteAptaResponse {
        return PostulanteAptaResponse(
            response.esApta,
            response.idEstadoActividad,
            response.mensajeError,
            response.nombreCompleto,
            response.tipoError,
            response.tipoSolicitud
        )
    }
}
