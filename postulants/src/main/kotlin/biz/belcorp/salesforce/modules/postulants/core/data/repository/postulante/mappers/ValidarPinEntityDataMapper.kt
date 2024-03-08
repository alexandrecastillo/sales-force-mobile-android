package biz.belcorp.salesforce.modules.postulants.core.data.repository.postulante.mappers

import biz.belcorp.salesforce.core.utils.Mapper
import biz.belcorp.salesforce.modules.postulants.core.data.entities.unete.ValidarPinEntity
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.ValidarPin

class ValidarPinEntityDataMapper : Mapper<ValidarPin, ValidarPinEntity>() {

    override fun reverseMap(value: ValidarPinEntity): ValidarPin {
        val entity = ValidarPin()
        entity.pais = value.pais
        entity.solicitudPostulanteID = value.solicitudPostulanteID
        entity.numeroDocumento = value.numeroDocumento
        entity.pin = value.PIN
        return entity
    }

    override fun map(value: ValidarPin): ValidarPinEntity {
        val entity = ValidarPinEntity()
        entity.pais = value.pais
        entity.solicitudPostulanteID = value.solicitudPostulanteID
        entity.numeroDocumento = value.numeroDocumento
        entity.PIN = value.pin
        return entity
    }

}





