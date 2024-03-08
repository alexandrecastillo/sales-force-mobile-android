package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.graficos.capitalization.data

import biz.belcorp.salesforce.core.db.objectbox.ObjectBox
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.capitalization.CapitalizationEntity
import biz.belcorp.salesforce.core.entities.capitalization.CapitalizationEntity_
import biz.belcorp.salesforce.core.utils.deleteHyphen
import biz.belcorp.salesforce.core.utils.equal
import biz.belcorp.salesforce.core.utils.inValues
import io.objectbox.kotlin.boxFor

class ProfileCapitalizationDataStore {

    fun save(items: List<CapitalizationEntity>) {
        ObjectBox.boxStore.boxFor<CapitalizationEntity>().put(items)
    }

    fun getStoredDataByUa(uaKey: LlaveUA): List<CapitalizationEntity> =
        ObjectBox.boxStore.boxFor<CapitalizationEntity>().query()
            .equal(CapitalizationEntity_.region, uaKey.codigoRegion.orEmpty().deleteHyphen())
            .and()
            .equal(CapitalizationEntity_.zone, uaKey.codigoZona.orEmpty().deleteHyphen())
            .and()
            .equal(CapitalizationEntity_.section, uaKey.codigoSeccion.orEmpty().deleteHyphen())
            .build()
            .find()

    fun getCapitalizationByUaAndCampaign(
        uaKey: LlaveUA,
        campaigns: List<String>
    ): List<CapitalizationEntity> =
        ObjectBox.boxStore.boxFor<CapitalizationEntity>().query()
            .equal(CapitalizationEntity_.region, uaKey.codigoRegion.orEmpty().deleteHyphen())
            .and()
            .equal(CapitalizationEntity_.zone, uaKey.codigoZona.orEmpty().deleteHyphen())
            .and()
            .equal(CapitalizationEntity_.section, uaKey.codigoSeccion.orEmpty().deleteHyphen())
            .and()
            .inValues(CapitalizationEntity_.campaign, campaigns.toTypedArray())
            .build()
            .find()

}
