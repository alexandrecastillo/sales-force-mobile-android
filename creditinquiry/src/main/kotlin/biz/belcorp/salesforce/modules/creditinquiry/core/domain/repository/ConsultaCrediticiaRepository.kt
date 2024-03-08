package biz.belcorp.salesforce.modules.creditinquiry.core.domain.repository

import biz.belcorp.salesforce.modules.creditinquiry.core.domain.entity.ConsultaCrediticia2
import biz.belcorp.salesforce.modules.creditinquiry.core.domain.entity.ValidacionCrediticiaInterna
import biz.belcorp.salesforce.modules.creditinquiry.features.model.ConsultaCrediticiaInterna
import io.reactivex.Single

interface ConsultaCrediticiaRepository {

    fun consultaCrediticiaDeudaInterna(
        pais: String,
        documentoIdentidad: String
    ): Single<ConsultaCrediticiaInterna>

    fun validarRegionZona(region: String, zona: String): Single<Int>

    fun consultaCrediticiaDeudaExterna(map: HashMap<String, String>): Single<ConsultaCrediticia2>

    fun consultaCrediticiaDeudaExternaBelcorpCO(map: HashMap<String, String>): Single<ConsultaCrediticia2>

    fun validacionCrediticiaInterna(
        pais: String,
        documentoIdentidad: String
    ): Single<ValidacionCrediticiaInterna>

}
