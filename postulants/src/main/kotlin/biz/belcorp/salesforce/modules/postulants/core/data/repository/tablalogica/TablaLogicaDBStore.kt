package biz.belcorp.salesforce.modules.postulants.core.data.repository.tablalogica

import biz.belcorp.salesforce.core.entities.sql.unete.TablaLogicaEntity
import biz.belcorp.salesforce.core.entities.sql.unete.TablaLogicaEntity_Table
import biz.belcorp.salesforce.modules.postulants.core.data.exceptions.DBDataStoreException
import com.raizlabs.android.dbflow.sql.language.OrderBy
import com.raizlabs.android.dbflow.sql.language.Select
import io.reactivex.Single

class TablaLogicaDBStore {

    fun list(tipoTablaLogica: Int): Single<List<TablaLogicaEntity>> {
        return Single.create {
            try {
                val entity = Select().from(TablaLogicaEntity::class.java)
                        .where(TablaLogicaEntity_Table.TablaLogicaID.eq(tipoTablaLogica))
                        .orderBy(OrderBy.fromProperty(TablaLogicaEntity_Table.Descripcion).ascending())
                        .queryList()
                it.onSuccess(entity)
            } catch (ex: Exception) {
                it.onError(DBDataStoreException(ex))
            }
        }
    }

}
