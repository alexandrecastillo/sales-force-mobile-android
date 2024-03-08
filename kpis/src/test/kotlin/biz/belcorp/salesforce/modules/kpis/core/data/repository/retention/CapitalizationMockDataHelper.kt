package biz.belcorp.salesforce.modules.kpis.core.data.repository.retention

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.kpis.core.data.repository.capitalization.cloud.dto.CapitalizationDto
import biz.belcorp.salesforce.modules.kpis.core.data.repository.capitalization.mapper.CapitalizationMapper
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.capitalization.CapitalizationIndicator
import biz.belcorp.salesforce.modules.kpis.features.utils.readString
import biz.belcorp.salesforce.core.utils.JsonUtil.JsonEncodedDefault

object CapitalizationMockDataHelper {

    fun getCapitalizationQueryData(): CapitalizationDto {
        val jsonString = readString("core/data/capitalization.json")
        return JsonEncodedDefault.decodeFromString(CapitalizationDto.serializer(), jsonString)
    }

    fun getCapitalizationEntities(
        ua: LlaveUA,
        campaign: String,
        mapper: CapitalizationMapper
    ): List<CapitalizationIndicator> {
        val capitalizationDto = getCapitalizationQueryData()
        val entity = mapper.map(capitalizationDto)
            .filter {
                it.campaign == campaign
                    && it.region == ua.codigoRegion
                    && it.zone == ua.codigoZona
                    && it.section == ua.codigoSeccion
            }.map {
                mapper.map(it)
            }
        return entity
    }
}
