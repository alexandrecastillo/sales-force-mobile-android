package biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.mapper

import biz.belcorp.salesforce.core.utils.Mapper
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.ValidarPin
import biz.belcorp.salesforce.modules.postulants.features.entities.ValidarPinModel

class ValidarPinModelDataMapper constructor() : Mapper<ValidarPin, ValidarPinModel>() {

    override fun map(value: ValidarPin): ValidarPinModel {

        val entity = ValidarPinModel()
        entity.solicitudPostulanteID = value.solicitudPostulanteID
        entity.numeroDocumento = value.numeroDocumento
        entity.pais = value.pais
        entity.PIN = value.pin
        return entity

    }

    override fun reverseMap(value: ValidarPinModel): ValidarPin {

        val entity = ValidarPin()
        entity.solicitudPostulanteID = value.solicitudPostulanteID
        entity.numeroDocumento = value.numeroDocumento
        entity.pais = value.pais
        entity.pin = value.PIN
        return entity
    }

}
