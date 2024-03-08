package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.anotaciones.data

import biz.belcorp.salesforce.core.entities.sql.anotaciones.AnotacionEntity
import biz.belcorp.salesforce.core.entities.sql.anotaciones.AnotacionEntity_Table
import biz.belcorp.salesforce.core.utils.doOnCompletable
import biz.belcorp.salesforce.core.utils.doOnSingle
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import com.raizlabs.android.dbflow.kotlinextensions.*
import com.raizlabs.android.dbflow.sql.language.Delete
import io.reactivex.Completable
import io.reactivex.Single

class AnotacionDBStore {

    fun guardar(entidad: AnotacionEntity): Single<AnotacionEntity> {
        return Single.create {
            entidad.apply {
                marcarseComoNoEnviado()
                setearIdServerTemporal()
                marcarseComoActivo()
            }
            if (allowToSave(entidad.personaId)) {
                entidad.save()
                it.onSuccess(entidad)
            } else {
                it.onError(Exception(MSG_NOTAS))
            }
        }
    }

    private fun allowToSave(personaId: Long?) : Boolean {
        val id = personaId ?: Constant.ZERO_NUMBER.toLong()
        return listarMetasSincrono(id).size < MAX_META
    }

    fun actualizar(entidad: AnotacionEntity): Single<AnotacionEntity> {
        return Single.create {
            entidad.actualizar()

            it.onSuccess(entidad)
        }
    }

    fun eliminar(entidad: AnotacionEntity): Completable {
        return Completable.create {
            entidad.eliminarLogicamente()

            it.onComplete()
        }
    }

    private fun listarMetasSincrono(personaId: Long): List<AnotacionEntity> {
        val where = (select from AnotacionEntity::class
            where (AnotacionEntity_Table.Activo eq Constant.UNO)
            and (AnotacionEntity_Table.Tipo eq META)
            and (AnotacionEntity_Table.IDConsultora eq personaId))
        return where.queryList().sortedByDescending { it.idLocal }
    }

    fun listarMetas(personaId: Long): Single<List<AnotacionEntity>> {
        return Single.create {
            it.onSuccess(listarMetasSincrono(personaId))
        }
    }

    fun listarNotas(region: String, zona: String, seccion: String): Single<List<AnotacionEntity>> {
        return Single.create {
            val where = (select from AnotacionEntity::class
                where (AnotacionEntity_Table.Activo eq Constant.UNO)
                and (AnotacionEntity_Table.Tipo eq NOTA)
                and (AnotacionEntity_Table.Region eq region)
                and (AnotacionEntity_Table.Zona eq zona)
                and (AnotacionEntity_Table.Seccion eq seccion))

            it.onSuccess(where.queryList())
        }
    }

    fun listarNotas(personaId: Long): Single<List<AnotacionEntity>> {
        return Single.create {

            val where = (select
                from AnotacionEntity::class
                where (AnotacionEntity_Table.Activo eq Constant.UNO)
                and (AnotacionEntity_Table.Tipo eq NOTA)
                and (AnotacionEntity_Table.IDConsultora eq personaId))

            it.onSuccess(where.queryList())
        }
    }

    private fun AnotacionEntity.actualizar() {
        val query = com.raizlabs.android.dbflow.sql.language.Update(AnotacionEntity::class.java)
            .set(
                AnotacionEntity_Table.Descripcion eq descripcion,
                AnotacionEntity_Table.Campania eq campania,
                AnotacionEntity_Table.FechaModificacion eq fechaModificacion,
                AnotacionEntity_Table.Enviado eq Constant.CERO
            )
            .where(AnotacionEntity_Table.IDLocal eq idLocal)

        query.execute()
    }

    private fun AnotacionEntity.eliminarLogicamente() {
        val query = com.raizlabs.android.dbflow.sql.language.Update(AnotacionEntity::class.java)
            .set(
                AnotacionEntity_Table.Activo eq Constant.CERO,
                AnotacionEntity_Table.Enviado eq Constant.CERO
            )
            .where(AnotacionEntity_Table.IDLocal eq idLocal)

        query.execute()
    }

    private fun AnotacionEntity.setearIdServer() {
        val query = com.raizlabs.android.dbflow.sql.language.Update(AnotacionEntity::class.java)
            .set(
                AnotacionEntity_Table.ID eq idServer,
                AnotacionEntity_Table.Enviado eq Constant.UNO
            )
            .where(AnotacionEntity_Table.IDLocal eq idLocal)

        query.execute()
    }

    private fun AnotacionEntity.marcarseComoNoEnviado() {
        enviado = Constant.CERO
    }

    private fun AnotacionEntity.setearIdServerTemporal() {
        idServer = Constant.CERO.toLong()
    }

    private fun AnotacionEntity.marcarseComoActivo() {
        activo = Constant.UNO
    }

    fun recuperarEliminadosNoEnviados(): Single<List<AnotacionEntity>> {
        return doOnSingle {
            requireNotNull(
                select from AnotacionEntity::class
                    where (AnotacionEntity_Table.Activo eq Constant.CERO)
                    and (AnotacionEntity_Table.Enviado eq Constant.CERO)
                    and (AnotacionEntity_Table.ID notEq Constant.CERO.toLong())
            ).queryList()
        }

    }

    fun recuperarEditadosNoEnviados(): Single<List<AnotacionEntity>> {
        return doOnSingle {
            requireNotNull(
                select
                    from AnotacionEntity::class
                    where (AnotacionEntity_Table.Activo eq Constant.UNO)
                    and (AnotacionEntity_Table.Enviado eq Constant.CERO)
                    and (AnotacionEntity_Table.ID notEq Constant.CERO.toLong())
            ).queryList()
        }
    }

    fun recuperarNuevosNoEnviados(): Single<List<AnotacionEntity>> {
        return doOnSingle {
            requireNotNull(
                select
                    from AnotacionEntity::class
                    where (AnotacionEntity_Table.Activo eq Constant.UNO)
                    and (AnotacionEntity_Table.Enviado eq Constant.CERO)
                    and (AnotacionEntity_Table.ID eq Constant.CERO.toLong())
            ).queryList()
        }

    }

    fun marcarEliminadosComoEnviados(): Completable {
        return doOnCompletable {
            val update = update<AnotacionEntity>()
                .set(AnotacionEntity_Table.Enviado eq Constant.UNO)
                .where(
                    (AnotacionEntity_Table.Enviado eq Constant.CERO)
                        and (AnotacionEntity_Table.Activo eq Constant.CERO)
                )
            update.execute()
        }
    }

    fun marcarEditadosComoEnviados(): Completable {

        return doOnCompletable {
            val update = update<AnotacionEntity>()
                .set(AnotacionEntity_Table.Enviado eq Constant.UNO)
                .where(
                    (AnotacionEntity_Table.Enviado eq Constant.CERO)
                        and (AnotacionEntity_Table.ID notEq Constant.CERO.toLong())
                        and (AnotacionEntity_Table.Activo notEq Constant.CERO)
                )
            update.execute()
        }
    }

    fun marcarNuevosComoEnviados(entidades: List<AnotacionEntity>): Completable {
        return doOnCompletable {
            entidades.forEach { it.setearIdServer() }
        }
    }

    fun eliminarSegunUa(anotacion: AnotacionEntity) {
        val query = Delete().from(AnotacionEntity::class.java)
            .where(
                (AnotacionEntity_Table.Region eq anotacion.region)
                    and (AnotacionEntity_Table.Zona eq anotacion.zona)
                    and (AnotacionEntity_Table.Seccion eq anotacion.seccion)
                    and (AnotacionEntity_Table.IDConsultora eq anotacion.personaId)
            )
        query.execute()
    }

    private fun AnotacionEntity.actualizarPorServerId() {
        val query = com.raizlabs.android.dbflow.sql.language.Update(AnotacionEntity::class.java)
            .set(
                AnotacionEntity_Table.Enviado eq Constant.UNO,
                AnotacionEntity_Table.Activo eq Constant.UNO,
                AnotacionEntity_Table.Campania eq campania,
                AnotacionEntity_Table.FechaModificacion eq fechaModificacion,
                AnotacionEntity_Table.Descripcion eq descripcion
            )
            .where(AnotacionEntity_Table.ID eq idServer)
        query.execute()
    }

    private fun AnotacionEntity.existe(): Boolean {
        val query = (select
            from AnotacionEntity::class
            where (AnotacionEntity_Table.ID eq idServer))

        return (query.queryList().isNotEmpty())
    }

    private fun guardarPorServerId(anotacion: AnotacionEntity) {
        if (anotacion.existe()) {
            anotacion.actualizarPorServerId()
        } else {
            anotacion.idLocal = Constant.CERO.toLong()
            anotacion.activo = Constant.UNO
            anotacion.enviado = Constant.UNO

            anotacion.insert()
        }
    }

    fun guardarPorServerId(anotaciones: List<AnotacionEntity>) {
        anotaciones.forEach { guardarPorServerId(it) }
    }

    companion object {

        private const val META = "M"
        private const val NOTA = "N"
        private const val MAX_META = 3

        private const val MSG_NOTAS = "Excede el límite de notas"

    }
}
