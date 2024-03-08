package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.reconocimiento.data

import biz.belcorp.salesforce.core.entities.sql.path.ReconocimientoEntity
import biz.belcorp.salesforce.core.entities.sql.path.ReconocimientoEntity_Table
import biz.belcorp.salesforce.core.utils.doOnCompletable
import biz.belcorp.salesforce.core.utils.doOnSingle
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.reconocimientos.ReconocimientoDataMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import com.raizlabs.android.dbflow.kotlinextensions.*
import io.reactivex.Completable
import io.reactivex.Single

class ReconocimientoLocalStore(val mapper: ReconocimientoDataMapper) {

    fun saveRecognition(recognition: ReconocimientoEntity) {
        recognition.grabar()
    }

    fun recuperarLista(): Single<List<ReconocimientoEntity>> {
        return doOnSingle {
            val entities = (select from ReconocimientoEntity::class)
            entities.queryList()
        }
    }

    fun recuperar(idReconocimiento: Long): Single<ReconocimientoEntity> {
        return doOnSingle {
            val query = (select
                from ReconocimientoEntity::class
                where (ReconocimientoEntity_Table.IdReconocimiento.withTable() eq idReconocimiento))

            requireNotNull(query.querySingle())
        }
    }

    fun reconocer(reconocimiento: ReconocimientoEntity): Completable {
        return doOnCompletable {
            val query = update<ReconocimientoEntity>()
                .set(
                    ReconocimientoEntity_Table.Pendiente eq Constant.UNO,
                    ReconocimientoEntity_Table.Comentarios eq reconocimiento.comentarios,
                    ReconocimientoEntity_Table.Calificacion eq reconocimiento.valoracion)
                .where(
                    ReconocimientoEntity_Table.IdReconocimiento eq reconocimiento.id)
            query.execute()
        }
    }

    fun recuperarPendientes(): Single<List<ReconocimientoEntity>> {
        return doOnSingle {
            val query = (select
                from ReconocimientoEntity::class
                where (ReconocimientoEntity_Table.Pendiente eq Constant.UNO))

            query.queryList()
        }
    }

    fun marcarAEnviados(): Completable {
        return doOnCompletable {
            val update = update<ReconocimientoEntity>()
                .set(ReconocimientoEntity_Table.Pendiente eq Constant.CERO)
                .where(
                    ReconocimientoEntity_Table.Pendiente.withTable().eq(Constant.UNO)
                        .and(ReconocimientoEntity_Table.Calificacion.notEq(Constant.MENOS_UNO))
                )

            update.execute()
        }
    }
}
