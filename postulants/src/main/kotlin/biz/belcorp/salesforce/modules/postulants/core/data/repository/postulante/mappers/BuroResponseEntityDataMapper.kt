package biz.belcorp.salesforce.modules.postulants.core.data.repository.postulante.mappers

import biz.belcorp.salesforce.modules.postulants.core.data.entities.unete.BuroResponseEntity
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.BuroResponse

class BuroResponseEntityDataMapper {

    fun reverseMap(value: BuroResponseEntity?): BuroResponse? {

        val entity = BuroResponse()

        entity.estadoCrediticio = value?.estadoCrediticio
        entity.enumEstadoCrediticioID = value?.enumEstadoCrediticioID
        entity.bloqueosInternos = value?.bloqueosInternos
        entity.mensajeError = value?.mensajeError.orEmpty()
        entity.tipoSolicitud = value?.tipoSolicitud
        entity.buroResponse = value?.buroResponse
        entity.requiereAprobacionSAC = value?.requiereAprobacionSAC
        entity.esConsultora = value?.existePostulante?.esConsultora
        entity.nombreCompleto = value?.existePostulante?.nombreCompleto
        entity.esPostulante = value?.existePostulante?.esPostulante
        entity.estadoActividad = value?.existePostulante?.estadoActividad
        entity.esPotencial = value?.existePostulante?.esPotencial

        return entity
    }

}
