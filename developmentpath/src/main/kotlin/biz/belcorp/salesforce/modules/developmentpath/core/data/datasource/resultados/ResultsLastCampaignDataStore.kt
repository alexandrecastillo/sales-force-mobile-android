package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.resultados

import biz.belcorp.salesforce.core.db.objectbox.ObjectBox.boxStore
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.capitalization.CapitalizationEntity
import biz.belcorp.salesforce.core.entities.capitalization.CapitalizationEntity_
import biz.belcorp.salesforce.core.entities.collection.CollectionEntity
import biz.belcorp.salesforce.core.entities.collection.CollectionEntity_
import biz.belcorp.salesforce.core.entities.newcycles.NewCycleEntity
import biz.belcorp.salesforce.core.entities.newcycles.NewCycleEntity_
import biz.belcorp.salesforce.core.entities.saleorders.SaleOrdersEntity
import biz.belcorp.salesforce.core.entities.saleorders.SaleOrdersEntity_
import biz.belcorp.salesforce.core.utils.deleteHyphen
import biz.belcorp.salesforce.core.utils.equal
import biz.belcorp.salesforce.modules.developmentpath.core.data.entities.results.ResultsCampaignData
import io.objectbox.kotlin.boxFor

class ResultsLastCampaignDataStore {

    fun getResults(uaKey: LlaveUA, campaign: String): ResultsCampaignData {
        val saleOrders = getSaleOrders(uaKey, campaign)
        val capitalization = getCapitalization(uaKey, campaign)
        val newCycles = getNewCycles(uaKey, campaign)
        val collection = getCollection(uaKey, campaign)
        return ResultsCampaignData(saleOrders, capitalization, newCycles, collection)
    }

    private fun getSaleOrders(uaKey: LlaveUA, campaign: String): SaleOrdersEntity? {
        return boxStore.boxFor<SaleOrdersEntity>().query()
            .equal(SaleOrdersEntity_.campaign, campaign).and()
            .equal(SaleOrdersEntity_.region, uaKey.codigoRegion.orEmpty().deleteHyphen()).and()
            .equal(SaleOrdersEntity_.zone, uaKey.codigoZona.orEmpty().deleteHyphen()).and()
            .equal(SaleOrdersEntity_.section, uaKey.codigoSeccion.orEmpty().deleteHyphen())
            .build()
            .findFirst()
    }

    private fun getCapitalization(uaKey: LlaveUA, campaign: String): CapitalizationEntity? {
        return boxStore.boxFor<CapitalizationEntity>().query()
            .equal(CapitalizationEntity_.campaign, campaign).and()
            .equal(CapitalizationEntity_.region, uaKey.codigoRegion.orEmpty().deleteHyphen()).and()
            .equal(CapitalizationEntity_.zone, uaKey.codigoZona.orEmpty().deleteHyphen()).and()
            .equal(CapitalizationEntity_.section, uaKey.codigoSeccion.orEmpty().deleteHyphen())
            .build()
            .findFirst()
    }

    private fun getNewCycles(uaKey: LlaveUA, campaign: String): NewCycleEntity? {
        return boxStore.boxFor<NewCycleEntity>().query()
            .equal(NewCycleEntity_.campaign, campaign).and()
            .equal(NewCycleEntity_.region, uaKey.codigoRegion.orEmpty().deleteHyphen()).and()
            .equal(NewCycleEntity_.zone, uaKey.codigoZona.orEmpty().deleteHyphen()).and()
            .equal(NewCycleEntity_.section, uaKey.codigoSeccion.orEmpty().deleteHyphen())
            .build()
            .findFirst()
    }

    private fun getCollection(uaKey: LlaveUA, campaign: String): CollectionEntity? {
        return boxStore.boxFor<CollectionEntity>().query()
            .equal(CollectionEntity_.campaign, campaign).and()
            .equal(CollectionEntity_.region, uaKey.codigoRegion.orEmpty().deleteHyphen()).and()
            .equal(CollectionEntity_.zone, uaKey.codigoZona.orEmpty().deleteHyphen()).and()
            .equal(CollectionEntity_.section, uaKey.codigoSeccion.orEmpty().deleteHyphen())
            .build()
            .findFirst()
    }
}
