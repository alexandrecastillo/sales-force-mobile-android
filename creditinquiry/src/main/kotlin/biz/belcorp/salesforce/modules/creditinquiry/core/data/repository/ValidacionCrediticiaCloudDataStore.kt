package biz.belcorp.salesforce.modules.creditinquiry.core.data.repository

import biz.belcorp.salesforce.modules.creditinquiry.core.data.entity.ConsultaCrediticia2Entity
import biz.belcorp.salesforce.modules.creditinquiry.core.data.entity.ConsultaCrediticiaInternaEntity
import biz.belcorp.salesforce.modules.creditinquiry.core.data.network.ValidacionCrediticiaRestApi
import io.reactivex.Single


class ValidacionCrediticiaCloudDataStore constructor(
    private val validacionCrediticiaApi: ValidacionCrediticiaRestApi
) {

    fun consultaCrediticiaDeudaInterna(
        pais: String,
        documentoIdentidad: String
    ): Single<ConsultaCrediticiaInternaEntity> {
        return validacionCrediticiaApi.getConsultaCrediticiaDeudaInterna(pais, documentoIdentidad)
    }

    fun consultaCrediticiaDeudaExterna(map: HashMap<String, String>): Single<ConsultaCrediticia2Entity> {
        return validacionCrediticiaApi.getConsultaCrediticiaDeudaExterna(map)
    }

}
