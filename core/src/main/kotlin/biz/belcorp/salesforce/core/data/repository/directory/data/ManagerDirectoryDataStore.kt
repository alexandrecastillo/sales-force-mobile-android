package biz.belcorp.salesforce.core.data.repository.directory.data

import biz.belcorp.salesforce.core.db.objectbox.ObjectBox.boxStore
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.directory.ManagerDirectoryEntity
import biz.belcorp.salesforce.core.entities.directory.ManagerDirectoryEntity_
import biz.belcorp.salesforce.core.utils.deleteAndInsert
import biz.belcorp.salesforce.core.utils.deleteHyphen
import biz.belcorp.salesforce.core.utils.equal
import io.objectbox.kotlin.boxFor

class ManagerDirectoryDataStore {

    fun saveManagerDirectory(entities: List<ManagerDirectoryEntity>) {
        boxStore.boxFor<ManagerDirectoryEntity>()
            .deleteAndInsert(entities)
    }

    fun getManagers(): List<ManagerDirectoryEntity> {
        return boxStore.boxFor<ManagerDirectoryEntity>().query().build().find()
    }

    fun getManager(uaKey: LlaveUA): ManagerDirectoryEntity? {
        return boxStore.boxFor<ManagerDirectoryEntity>().query()
            .equal(ManagerDirectoryEntity_.region, uaKey.codigoRegion.orEmpty().deleteHyphen())
            .and()
            .equal(ManagerDirectoryEntity_.zone, uaKey.codigoZona.orEmpty().deleteHyphen())
            .build()
            .findFirst()
    }

    fun getManager(consultantId: Int): ManagerDirectoryEntity {
        val manager = boxStore.boxFor<ManagerDirectoryEntity>().query()
            .equal(ManagerDirectoryEntity_.consultantId, consultantId.toLong())
            .build()
            .findFirst()
        return requireNotNull(manager)
    }
}
