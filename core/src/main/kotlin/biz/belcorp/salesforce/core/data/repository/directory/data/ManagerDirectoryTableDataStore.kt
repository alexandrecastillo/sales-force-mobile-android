package biz.belcorp.salesforce.core.data.repository.directory.data

import biz.belcorp.salesforce.core.entities.sql.directorio.DirectorioVentaUsuarioEntity
import biz.belcorp.salesforce.core.utils.deleteAndInsert

class ManagerDirectoryTableDataStore {

    fun saveManagers(managers: List<DirectorioVentaUsuarioEntity>) {
        managers.deleteAndInsert()
    }

}
