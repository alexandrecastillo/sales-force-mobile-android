package biz.belcorp.salesforce.modules.consultants.core.data.repository.consultants.data

import biz.belcorp.salesforce.core.db.objectbox.ObjectBox.boxStore
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.ConsultantEntity
import biz.belcorp.salesforce.core.entities.ConsultantEntity_
import biz.belcorp.salesforce.core.utils.*
import io.objectbox.kotlin.boxFor

class ConsultantsDataStore {

    fun saveConsultants(items: List<ConsultantEntity>) {
        boxStore
            .boxFor<ConsultantEntity>()
            .deleteAndInsert(items)
    }

    fun getConsultants(uaKey: LlaveUA): List<ConsultantEntity> {
        return boxStore.boxFor<ConsultantEntity>().query()
            .doIf(!uaKey.codigoRegion.isCodeEmptyOrNull()) {
                equal(ConsultantEntity_.region, uaKey.codigoRegion.orEmpty().deleteHyphen())
            }
            .doIf(!uaKey.codigoZona.isCodeEmptyOrNull()) {
                and().equal(ConsultantEntity_.zone, uaKey.codigoZona.orEmpty().deleteHyphen())
            }
            .doIf(!uaKey.codigoSeccion.isCodeEmptyOrNull()) {
                and().equal(ConsultantEntity_.section, uaKey.codigoSeccion.orEmpty().deleteHyphen())
            }
            .order(ConsultantEntity_.constancyNew)
            .order(ConsultantEntity_.name)
            .build()
            .find()
    }

    fun updateConsultants(items: List<ConsultantEntity>) {
        boxStore.runInTx {
            boxStore
                .boxFor<ConsultantEntity>()
                .put(items)
        }
    }

}
