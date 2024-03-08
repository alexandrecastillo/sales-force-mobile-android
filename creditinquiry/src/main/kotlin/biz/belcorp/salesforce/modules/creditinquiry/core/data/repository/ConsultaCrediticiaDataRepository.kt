package biz.belcorp.salesforce.modules.creditinquiry.core.data.repository

import biz.belcorp.salesforce.modules.creditinquiry.core.data.mapper.ConsultaCrediticiaEntityDataMapper
import biz.belcorp.salesforce.modules.creditinquiry.core.data.mapper.ValidacionCrediticiaEntityDataMapper
import biz.belcorp.salesforce.modules.creditinquiry.core.domain.entity.ConsultaCrediticia2
import biz.belcorp.salesforce.modules.creditinquiry.core.domain.entity.ValidacionCrediticiaInterna
import biz.belcorp.salesforce.modules.creditinquiry.core.domain.repository.ConsultaCrediticiaRepository
import biz.belcorp.salesforce.modules.creditinquiry.features.model.ConsultaCrediticiaInterna
import io.reactivex.Single


class ConsultaCrediticiaDataRepository constructor(
    private val consultaCrediticiaCloudStore: ConsultaCrediticiaCloudDataStore,
    private val validacionCrediciaCloudStore: ValidacionCrediticiaCloudDataStore,
    private val consultaCrediticiaEntityDataMapper: ConsultaCrediticiaEntityDataMapper,
    private val validacionCrediticiaEntityDataMapper: ValidacionCrediticiaEntityDataMapper
) :
    ConsultaCrediticiaRepository {

    override fun consultaCrediticiaDeudaInterna(
        pais: String,
        documentoIdentidad: String
    ): Single<ConsultaCrediticiaInterna> {
        return validacionCrediciaCloudStore.consultaCrediticiaDeudaInterna(pais, documentoIdentidad)
            .map {
                consultaCrediticiaEntityDataMapper.parseConsultaCrediticia(it)
            }
    }

    override fun consultaCrediticiaDeudaExterna(map: HashMap<String, String>): Single<ConsultaCrediticia2> {
        return validacionCrediciaCloudStore.consultaCrediticiaDeudaExterna(map)
            .map {
                consultaCrediticiaEntityDataMapper.parseConsultaCrediticia(it)
            }
    }

    override fun consultaCrediticiaDeudaExternaBelcorpCO(map: HashMap<String, String>): Single<ConsultaCrediticia2> {
        return validacionCrediciaCloudStore.consultaCrediticiaDeudaExterna(map)
            .map {
                consultaCrediticiaEntityDataMapper.parseConsultaCrediticia(it)
            }
    }

    override fun validarRegionZona(region: String, zona: String): Single<Int> {
        return consultaCrediticiaCloudStore.validarRegionZona(region, zona)
    }

    override fun validacionCrediticiaInterna(
        pais: String,
        documentoIdentidad: String
    ): Single<ValidacionCrediticiaInterna> {
        return validacionCrediciaCloudStore.consultaCrediticiaDeudaInterna(pais, documentoIdentidad)
            .map {
                validacionCrediticiaEntityDataMapper.parseValidacionCrediticiaInterna(it)
            }
    }

}
