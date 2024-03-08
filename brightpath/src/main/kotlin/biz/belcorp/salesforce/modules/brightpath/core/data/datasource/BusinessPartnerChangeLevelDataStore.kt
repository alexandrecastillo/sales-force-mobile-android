package biz.belcorp.salesforce.modules.brightpath.core.data.datasource

import biz.belcorp.salesforce.core.db.objectbox.ObjectBox
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.businesspartner.BusinessPartnerChangeLevelEntity
import biz.belcorp.salesforce.core.entities.businesspartner.BusinessPartnerChangeLevelEntity_
import biz.belcorp.salesforce.core.entities.businesspartner.BusinessPartnerChangeLevelLevelEntity
import biz.belcorp.salesforce.core.entities.businesspartner.BusinessPartnerChangeLevelNextLevelEntity
import biz.belcorp.salesforce.core.entities.businesspartner.BusinessPartnerChangeLevelRequirementEntity
import biz.belcorp.salesforce.core.entities.businesspartner.BusinessPartnerChangeOrdersEntity
import biz.belcorp.salesforce.core.entities.businesspartner.BusinessPartnerChangeSalesEntity
import biz.belcorp.salesforce.core.utils.deleteHyphen
import biz.belcorp.salesforce.core.utils.equal
import biz.belcorp.salesforce.modules.brightpath.core.data.network.dto.BusinessPartnerChangeLevelDto
import io.objectbox.kotlin.boxFor

class BusinessPartnerChangeLevelDataStore {

    fun saveChangeLevel(level: List<BusinessPartnerChangeLevelEntity>) {

        with(ObjectBox.boxStore) {
            runInTx {
                boxFor<BusinessPartnerChangeLevelEntity>().query()
                    .equal(BusinessPartnerChangeLevelEntity_.region, level[0].region).and()
                    .equal(BusinessPartnerChangeLevelEntity_.zone, level[0].zone).and()
                    .equal(BusinessPartnerChangeLevelEntity_.section, level[0].section)
                    .equal(BusinessPartnerChangeLevelEntity_.consultantCode, level[0].consultantCode)
                    .build()
                    .remove()

                boxFor<BusinessPartnerChangeLevelEntity>().put(level)
            }
        }
    }

    fun getChangeLevelMap(uaKey: LlaveUA): MutableList<BusinessPartnerChangeLevelEntity> =
        ObjectBox.boxStore.boxFor<BusinessPartnerChangeLevelEntity>().query()
            .equal(BusinessPartnerChangeLevelEntity_.region, uaKey.codigoRegion.orEmpty().deleteHyphen()).and()
            .equal(BusinessPartnerChangeLevelEntity_.zone, uaKey.codigoZona.orEmpty().deleteHyphen()).and()
            .equal(BusinessPartnerChangeLevelEntity_.section, uaKey.codigoSeccion.orEmpty().deleteHyphen())
            .build()
            .find()


    private fun map(data: BusinessPartnerChangeLevelEntity): BusinessPartnerChangeLevelDto.BusinessPartnerChangeLevel = with(data) {
        val level = level.map(::map)
        val nextLevel = nextLevel.map(::map)

        val entity = BusinessPartnerChangeLevelDto.BusinessPartnerChangeLevel(
            campaign = campaign,
            profile = profile,
            region = region,
            zone = zone,
            section = section,
            consultantCode = consultantCode,
            campaignRequirement = campaignRequirement,
            level = level.last(),
            nextLevel = nextLevel.last(),
            levelRequirement = levelRequirement.map(::map)
        )

        return entity
    }


    private fun map(data: BusinessPartnerChangeLevelLevelEntity): BusinessPartnerChangeLevelDto.BusinessPartnerChangeLevel.Level =
        with(data) {
            return BusinessPartnerChangeLevelDto.BusinessPartnerChangeLevel.Level(
                code = code,
                name = name,
                consecutiveCampaigns = consecutiveCampaigns,
                campaignsNotAccomplished = campaignsNotAccomplished,
            )
        }

    private fun map(data: BusinessPartnerChangeLevelNextLevelEntity): BusinessPartnerChangeLevelDto.BusinessPartnerChangeLevel.NextLevel =
        with(data) {


            val sales = sales.map(::map).last()
            val orders = orders.map(::map).last()

            return BusinessPartnerChangeLevelDto.BusinessPartnerChangeLevel.NextLevel(
                name = name,
                accomplished = accomplished,
                campaigns_accomplished = campaigns_accomplished,
                sales = sales,
                orders = orders
            )
        }

    private fun map(data: BusinessPartnerChangeSalesEntity): BusinessPartnerChangeLevelDto.BusinessPartnerChangeLevel.NextLevel.Sales =
        with(data) {
            return BusinessPartnerChangeLevelDto.BusinessPartnerChangeLevel.NextLevel.Sales(
                requirement = requirement,
                real = real,
                accomplished = accomplished,
            )
        }

    private fun map(data: BusinessPartnerChangeOrdersEntity): BusinessPartnerChangeLevelDto.BusinessPartnerChangeLevel.NextLevel.Orders =
        with(data) {
            return BusinessPartnerChangeLevelDto.BusinessPartnerChangeLevel.NextLevel.Orders(
                requirement = requirement,
                real = real,
                accomplished = accomplished,
            )
        }

    private fun map(data: BusinessPartnerChangeLevelRequirementEntity): BusinessPartnerChangeLevelDto.BusinessPartnerChangeLevel.LevelRequirement =
        with(data) {
            return BusinessPartnerChangeLevelDto.BusinessPartnerChangeLevel.LevelRequirement(
                level = level,
                minimumSales = minimumSales,
                minimumOrders = minimumOrders
            )
        }

}
