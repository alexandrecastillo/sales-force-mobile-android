package biz.belcorp.salesforce.modules.creditinquiry.core.data.mapper

import biz.belcorp.salesforce.modules.creditinquiry.core.data.entity.ConsultaCrediticiaInternaEntity
import biz.belcorp.salesforce.modules.creditinquiry.core.domain.entity.ValidacionCrediticiaInterna

class ValidacionCrediticiaEntityDataMapper {

    fun parseValidacionCrediticiaInterna(entity: ConsultaCrediticiaInternaEntity): ValidacionCrediticiaInterna {

        val consultaCrediticiaInterna = ValidacionCrediticiaInterna()

        consultaCrediticiaInterna.bloqueosInternos = entity.tieneBloqueo
        consultaCrediticiaInterna.bloqueos = BloqueoEntityDataMapper().parseBloqueo(entity.bloqueos)

        return consultaCrediticiaInterna
    }

}
