package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.metas.data

import biz.belcorp.salesforce.core.data.exceptions.ErrorException
import biz.belcorp.salesforce.core.entities.sql.filtros.TipoMetaEntity
import com.raizlabs.android.dbflow.kotlinextensions.from
import com.raizlabs.android.dbflow.kotlinextensions.select
import io.reactivex.Single

class TipoMetaDBDataStore : TipoMetaDataStore {

    override fun obtener(): Single<List<TipoMetaEntity>> {
        return Single.create { subscriber ->
            try {
                val list = (select from TipoMetaEntity::class).queryList()
                subscriber.onSuccess(list)
            } catch (e: Exception) {
                subscriber.onError(ErrorException(e.cause ?: Throwable(e.message ?: "")))
            }
        }
    }
}
