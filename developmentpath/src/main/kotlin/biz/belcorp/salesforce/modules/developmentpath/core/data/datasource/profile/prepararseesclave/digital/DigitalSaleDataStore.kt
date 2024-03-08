package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.prepararseesclave.digital

import biz.belcorp.salesforce.core.db.objectbox.ObjectBox.boxStore
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.digitalsale.DigitalSaleEntity
import biz.belcorp.salesforce.core.entities.digitalsale.DigitalSaleEntity_
import biz.belcorp.salesforce.core.entities.digitalsale.DigitalSaleSeEntity
import biz.belcorp.salesforce.core.entities.digitalsale.DigitalSaleSeEntity_
import biz.belcorp.salesforce.core.utils.deleteHyphen
import biz.belcorp.salesforce.core.utils.equal
import biz.belcorp.salesforce.core.utils.inValues
import io.objectbox.kotlin.boxFor

class DigitalSaleDataStore {

    fun deleteDigitalSaleConsultantData(consultantCode: String) {
        boxStore.boxFor<DigitalSaleEntity>().query()
            .equal(DigitalSaleEntity_.consultantCode, consultantCode)
            .build()
            .remove()
    }

    fun deleteDigitalSaleBusinessPartnerData(uaKey: LlaveUA) {
        boxStore.boxFor<DigitalSaleSeEntity>().query()
            .equal(DigitalSaleSeEntity_.region, uaKey.codigoRegion.orEmpty().deleteHyphen())
            .and()
            .equal(DigitalSaleSeEntity_.zone, uaKey.codigoZona.orEmpty().deleteHyphen())
            .and()
            .equal(DigitalSaleSeEntity_.section, uaKey.codigoSeccion.orEmpty().deleteHyphen())
            .build()
            .remove()
    }

    fun saveDigitalSaleConsultant(options: List<DigitalSaleEntity>) {
        boxStore.boxFor<DigitalSaleEntity>().put(options)
    }

    fun saveDigitalSaleBusinessPartner(options: List<DigitalSaleSeEntity>) {
        boxStore.boxFor<DigitalSaleSeEntity>().put(options)
    }

    fun getDigitalSaleSeList(uaKey: LlaveUA, campaigns: List<String>): List<DigitalSaleSeEntity> {
        return boxStore.boxFor<DigitalSaleSeEntity>().query()
            .equal(DigitalSaleSeEntity_.region, uaKey.codigoRegion.orEmpty().deleteHyphen())
            .and()
            .equal(DigitalSaleSeEntity_.zone, uaKey.codigoZona.orEmpty().deleteHyphen())
            .and()
            .equal(DigitalSaleSeEntity_.section, uaKey.codigoSeccion.orEmpty().deleteHyphen())
            .and()
            .inValues(DigitalSaleSeEntity_.campaign, campaigns.toTypedArray())
            .build()
            .find()
    }

    fun getDigitalSaleConsultantList(
        consultantCode: String,
        campaigns: List<String>
    ): List<DigitalSaleEntity> {
        return boxStore.boxFor<DigitalSaleEntity>().query()
            .equal(DigitalSaleEntity_.consultantCode, consultantCode)
            .and()
            .inValues(DigitalSaleEntity_.campaign, campaigns.toTypedArray())
            .build()
            .find()
    }

    fun hasDigitalSalesConsultant(consultantCode: String, campaigns: List<String>): Boolean {
        return getDigitalSaleConsultantList(consultantCode, campaigns).size == campaigns.size
    }

    fun hasDigitalSaleSe(uaKey: LlaveUA, campaigns: List<String>): Boolean {
        return getDigitalSaleSeList(uaKey, campaigns).size == campaigns.size
    }
}
