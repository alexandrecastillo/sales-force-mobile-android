package biz.belcorp.salesforce.modules.postulants.core.data.repository.postulante.mappers

import biz.belcorp.salesforce.modules.postulants.core.data.entities.unete.ValidacionResponseEntity
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.ValidacionBuroResponse

class ValidacionBuroResponseEntitiyDataMapper {

    fun reverseMap(value: ValidacionResponseEntity?): ValidacionBuroResponse? {
        if (value != null) {

            val entity = ValidacionBuroResponse()

            entity.enumEstadoCrediticio = value.enumEstadoCrediticio
            entity.requiereAprobacionSAC = value.requiereAprobacionSAC
            entity.mensaje = value.mensaje.orEmpty()
            entity.respuestaServicio = value.respuestaServicio

            return entity
        }
        return null
    }

}
