package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.info.data

import biz.belcorp.salesforce.core.db.objectbox.ObjectBox.boxStore
import biz.belcorp.salesforce.core.entities.directory.ManagerDirectoryEntity
import biz.belcorp.salesforce.core.entities.directory.ManagerDirectoryEntity_
import biz.belcorp.salesforce.core.utils.equal
import io.objectbox.kotlin.boxFor

class DirectoryInfoDataStore {

    fun get(code: String): ManagerDirectoryEntity? {
        return boxStore.boxFor<ManagerDirectoryEntity>().query()
            .equal(ManagerDirectoryEntity_.userName, code)
            .build()
            .findFirst()
    }

}
