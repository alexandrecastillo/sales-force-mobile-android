package biz.belcorp.salesforce.modules.kpis.core.data.repository.common.mappers

import biz.belcorp.salesforce.core.data.dto.kpis.KpiRequestParams
import biz.belcorp.salesforce.modules.kpis.core.data.repository.collection.cloud.dto.CollectionRequestParams
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.KpiQueryParams

class KpiQueryMapper {

    fun mapKpi(queryParams: KpiQueryParams): KpiRequestParams = with(queryParams) {
        return KpiRequestParams(
            country = country,
            campaign = campaign,
            profile = profile,
            region = region,
            zone = zone,
            section = section,
            showRegions = showRegions,
            showZones = showZones,
            showSection = showSection
        )
    }

    fun mapCollection(queryParams: KpiQueryParams): CollectionRequestParams = with(queryParams) {
        return CollectionRequestParams(
            country = country,
            campaign = campaign,
            profile = profile,
            region = region,
            zone = zone,
            section = section,
            showRegions = showRegions,
            showZones = showZones,
            showSection = showSection,
            days = days
        )
    }

}
