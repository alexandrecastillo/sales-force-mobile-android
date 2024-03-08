package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.metas.data

import biz.belcorp.salesforce.core.entities.sql.datos.MetasEntity
import biz.belcorp.salesforce.core.entities.sql.datos.MetasEntity_Table
import biz.belcorp.salesforce.core.utils.doOnCompletable
import com.raizlabs.android.dbflow.kotlinextensions.eq
import com.raizlabs.android.dbflow.kotlinextensions.update
import com.raizlabs.android.dbflow.sql.language.Select
import io.reactivex.Completable
import io.reactivex.Single

class MetaConsultoraDBDataStore {

    fun guardar(metaConsultora: MetasEntity): Single<MetasEntity> {
        return Single.create {
            metaConsultora.marcarseComoPendiente()
            metaConsultora.save()
            it.onSuccess(metaConsultora)
        }
    }

    fun marcarComoEnviado(metaConsultora: MetasEntity): Completable {
        return doOnCompletable {
            metaConsultora.marcarseComoEnviado()
            metaConsultora.update()
        }
    }

    fun obtener(consultoraId: Int): Single<List<MetasEntity>> {
        return Single.create {
            val modelos = Select()
                .from(MetasEntity::class.java)
                .where(MetasEntity_Table.ConsultoraID.eq(consultoraId))
                .queryList()

            it.onSuccess(modelos)
        }
    }

    fun obtenerNoEnviadas(): Single<List<MetasEntity>> {
        return Single.create {
            val metas = Select().from(MetasEntity::class.java)
                .where(MetasEntity_Table.Pendiente.eq(1)).queryList()
            it.onSuccess(metas)
        }
    }

    fun marcarComoEnviadas(): Completable {
        return doOnCompletable {
            val update = update<MetasEntity>()
                .set(MetasEntity_Table.Pendiente eq 0)
                .where(MetasEntity_Table.Pendiente eq 1)

            update.execute()
        }
    }
}
