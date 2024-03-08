package biz.belcorp.salesforce.modules.consultants.feature.filters

import biz.belcorp.salesforce.core.utils.JsonUtil.JsonEncodedDefault
import biz.belcorp.salesforce.core.utils.readString
import biz.belcorp.salesforce.modules.consultants.core.data.repository.consultants.cloud.dto.ConsultantDto
import biz.belcorp.salesforce.modules.consultants.core.data.repository.consultants.cloud.dto.DigitalDto

object DataCreator {

    fun getConsultantsMockData(): ConsultantDto {
        val jsonString = readString("core/data/consultants.json")
        return JsonEncodedDefault.decodeFromString(ConsultantDto.serializer(), jsonString)
    }

    fun getConsultantsOnlineStoreMockData(): DigitalDto {
        val jsonString = readString("core/data/consultant_online_store.json")
        return JsonEncodedDefault.decodeFromString(DigitalDto.serializer(), jsonString)
    }

}
