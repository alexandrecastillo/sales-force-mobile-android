package biz.belcorp.salesforce.modules.brightpath.core.data.repository

import biz.belcorp.salesforce.core.data.repository.businesspartners.data.BusinessPartnerDataStore
import biz.belcorp.salesforce.core.domain.entities.ua.Region
import biz.belcorp.salesforce.core.domain.entities.ua.Seccion
import biz.belcorp.salesforce.core.domain.entities.ua.Zona
import biz.belcorp.salesforce.modules.brightpath.core.data.mapper.ua.RegionEntityDataMapper
import biz.belcorp.salesforce.modules.brightpath.core.data.mapper.ua.SectionEntityDataMapper
import biz.belcorp.salesforce.modules.brightpath.core.data.mapper.ua.ZoneEntityDataMapper
import biz.belcorp.salesforce.modules.brightpath.core.data.repository.data.ua.UaSegmentsDBDataStore
import biz.belcorp.salesforce.modules.brightpath.core.domain.repository.UaSegmentsRepository
import io.reactivex.Single

class UaSegmentsDataRepository(
    private val dbDataStore: UaSegmentsDBDataStore,
    private val businessPartnerDataStore: BusinessPartnerDataStore,
    private val sectionEntityDataMapper: SectionEntityDataMapper,
    private val zoneEntityDataMapper: ZoneEntityDataMapper,
    private val regionEntityDataMapper: RegionEntityDataMapper
) : UaSegmentsRepository {

    override fun getSections(): Single<List<Seccion>> {

        return businessPartnerDataStore
            .getBusinessPartnersSingle()
            .map { this.sectionEntityDataMapper.map(it) }
    }

    override fun getZones(): Single<List<Zona>> {
        return dbDataStore
            .getZones()
            .map { this.zoneEntityDataMapper.parse(it) }
    }

    override fun getRegions(): Single<List<Region>> {
        return dbDataStore
            .getRegions()
            .map { this.regionEntityDataMapper.parse(it) }
    }
}
