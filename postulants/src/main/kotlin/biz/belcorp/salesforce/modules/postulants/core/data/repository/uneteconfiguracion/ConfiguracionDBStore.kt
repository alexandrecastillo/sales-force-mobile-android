package biz.belcorp.salesforce.modules.postulants.core.data.repository.uneteconfiguracion

import biz.belcorp.salesforce.core.entities.sql.unete.ConfiguracionEntity
import biz.belcorp.salesforce.core.entities.sql.unete.ConfiguracionEntity_Table
import biz.belcorp.salesforce.modules.postulants.core.data.exceptions.DBDataStoreException
import com.raizlabs.android.dbflow.sql.language.Select
import io.reactivex.Single

class ConfiguracionDBStore {

    fun get(pais: String, rol: String): Single<ConfiguracionEntity?> {
        return Single.create {
            try {
                val entity = Select().from(ConfiguracionEntity::class.java)
                        .where(
                                ConfiguracionEntity_Table.Pais.eq(pais),
                                ConfiguracionEntity_Table.Rol.eq(rol)
                        )
                        .querySingle()
                it.onSuccess(entity!!)
            } catch (ex: Exception) {
                it.onError(DBDataStoreException(ex))
            }
        }
    }

}
