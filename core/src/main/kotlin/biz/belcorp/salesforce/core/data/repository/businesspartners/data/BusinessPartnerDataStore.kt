package biz.belcorp.salesforce.core.data.repository.businesspartners.data

import biz.belcorp.salesforce.core.data.exceptions.ErrorException
import biz.belcorp.salesforce.core.db.objectbox.ObjectBox.boxStore
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.exceptions.NetworkConnectionException
import biz.belcorp.salesforce.core.entities.SuccessfuHistoricEntity
import biz.belcorp.salesforce.core.entities.businesspartner.BusinessPartnerEntity
import biz.belcorp.salesforce.core.entities.businesspartner.BusinessPartnerEntity_
import biz.belcorp.salesforce.core.utils.deleteAndInsert
import biz.belcorp.salesforce.core.utils.deleteHyphen
import biz.belcorp.salesforce.core.utils.equal
import io.objectbox.kotlin.boxFor
import io.reactivex.Single

class BusinessPartnerDataStore {

    fun saveBusinessPartners(partners: List<BusinessPartnerEntity>) {
        cleanSuccessfulHistoric()
        boxStore.boxFor<BusinessPartnerEntity>()
            .deleteAndInsert(partners)
    }

    fun getBusinessPartnersSingle(): Single<List<BusinessPartnerEntity>> {
        return Single.create { s ->
            try {
                val list = getBusinessPartners()

                if (list.isNotEmpty()) {
                    s.onSuccess(list)
                } else {
                    s.onError(ErrorException())
                }

            } catch (e: Exception) {
                s.onError(NetworkConnectionException())
            }
        }
    }

    fun getBusinessPartners(): List<BusinessPartnerEntity> {
        return boxStore.boxFor<BusinessPartnerEntity>()
            .query()
            .build()
            .find()
    }

    fun getBusinessPartner(uaKey: LlaveUA): BusinessPartnerEntity? {
        return boxStore.boxFor<BusinessPartnerEntity>()
            .query()
            .equal(BusinessPartnerEntity_.region, uaKey.codigoRegion.orEmpty().deleteHyphen())
            .and()
            .equal(BusinessPartnerEntity_.zone, uaKey.codigoZona.orEmpty().deleteHyphen())
            .and()
            .equal(BusinessPartnerEntity_.section, uaKey.codigoSeccion.orEmpty().deleteHyphen())
            .build()
            .findFirst()
    }

    fun getBusinessPartner(consultantCode: String): BusinessPartnerEntity? {
        return boxStore.boxFor<BusinessPartnerEntity>()
            .query()
            .equal(BusinessPartnerEntity_.partnerCode, consultantCode)
            .build()
            .findFirst()
    }

    private fun cleanSuccessfulHistoric() {
        boxStore.boxFor<SuccessfuHistoricEntity>().removeAll()
    }

}
