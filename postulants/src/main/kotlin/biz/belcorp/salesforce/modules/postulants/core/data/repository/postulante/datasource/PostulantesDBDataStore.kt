package biz.belcorp.salesforce.modules.postulants.core.data.repository.postulante.datasource

import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import biz.belcorp.salesforce.core.entities.sql.unete.*
import biz.belcorp.salesforce.core.utils.doOnSingle
import biz.belcorp.salesforce.modules.postulants.core.data.exceptions.DBDataStoreException
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.UneteTipoParametro
import biz.belcorp.salesforce.modules.postulants.utils.Constant
import com.raizlabs.android.dbflow.config.FlowManager
import com.raizlabs.android.dbflow.sql.language.Delete
import com.raizlabs.android.dbflow.sql.language.OrderBy
import com.raizlabs.android.dbflow.sql.language.Select
import io.reactivex.Single

class PostulantesDBDataStore {

    fun listOffline(): Single<List<PostulanteEntity>> {
        return doOnSingle {
            try {
                Select().from(PostulanteEntity::class.java)
                    .where(PostulanteEntity_Table.Sincronizado.eq(false))
                    .queryList()

            } catch (ex: Exception) {
                throw DBDataStoreException(ex)
            }
        }
    }

    fun get(uuid: String, id: Int): Single<PostulanteEntity> {
        return doOnSingle {
            try {

                var entity = Select().from(PostulanteEntity::class.java)
                    .where(PostulanteEntity_Table.uuid.eq(uuid))
                    .querySingle()

                if (entity == null) {
                    entity = Select().from(PostulanteEntity::class.java)
                        .where(PostulanteEntity_Table.SolicitudPostulanteID.eq(id))
                        .querySingle()
                }

                entity!!

            } catch (ex: Exception) {
                throw DBDataStoreException(ex)
            }
        }
    }

    fun getPre(uuid: String, id: Int) = doOnSingle { requireNotNull(postulanteExiste(uuid, id)) }

    fun postulanteExiste(uuid: String, id: Int): PrePostulanteEntity? {
        var entity = Select().from(PrePostulanteEntity::class.java)
            .where(PrePostulanteEntity_Table.uuid.eq(uuid))
            .querySingle()

        if (entity == null) {
            entity = Select().from(PrePostulanteEntity::class.java)
                .where(PrePostulanteEntity_Table.SolicitudPrePostulanteID.eq(id))
                .querySingle()
        }
        return entity
    }

    fun existeNumeroDocumento(numeroDocumento: String): Single<Boolean> {
        return doOnSingle {
            try {

                val entity = Select().from(PostulanteEntity::class.java)
                    .where(PostulanteEntity_Table.NumeroDocumento.eq(numeroDocumento))
                    .querySingle()

                entity == null
            } catch (ex: Exception) {
                throw DBDataStoreException(ex)
            }
        }
    }

    fun listAll(): Single<List<PostulanteEntity>> {
        return doOnSingle {
            Select()
                .from(PostulanteEntity::class.java)
                .queryList()

        }
    }

    fun list(estados: List<String>, seccion: String?): Single<List<PostulanteEntity>> {
        return doOnSingle {
            try {

                val entities = if (estados.isNotEmpty()) {
                    if (seccion.isNullOrEmpty())
                        Select().from(PostulanteEntity::class.java)
                            .where(
                                PostulanteEntity_Table.EstadoPostulante.`in`(estados),
                                PostulanteEntity_Table.uuid.isNot(Constant.EMPTY_STRING)
                            )
                            .orderBy(
                                OrderBy.fromProperty(PostulanteEntity_Table.FechaCreacion)
                                    .descending()
                            )
                            .queryList()
                    else
                        Select().from(PostulanteEntity::class.java)
                            .where(
                                PostulanteEntity_Table.EstadoPostulante.`in`(estados),
                                PostulanteEntity_Table.CodigoSeccion.eq(seccion),
                                PostulanteEntity_Table.uuid.isNot(Constant.EMPTY_STRING)
                            )
                            .orderBy(
                                OrderBy.fromProperty(PostulanteEntity_Table.FechaCreacion)
                                    .descending()
                            )
                            .queryList()
                } else {
                    if (seccion.isNullOrEmpty())
                        Select().from(PostulanteEntity::class.java)
                            .where(PostulanteEntity_Table.uuid.isNot(Constant.EMPTY_STRING))
                            .orderBy(
                                OrderBy.fromProperty(PostulanteEntity_Table.FechaCreacion)
                                    .descending()
                            )
                            .queryList()
                    else
                        Select().from(PostulanteEntity::class.java)
                            .where(
                                PostulanteEntity_Table.CodigoSeccion.eq(seccion),
                                PostulanteEntity_Table.uuid.isNot(Constant.EMPTY_STRING)
                            )
                            .orderBy(
                                OrderBy.fromProperty(PostulanteEntity_Table.FechaCreacion)
                                    .descending()
                            )
                            .queryList()
                }

                val tipoRechazoList = Select().from(ParametroUneteEntity::class.java)
                    .where(
                        ParametroUneteEntity_Table.FK_IdTipoParametro
                            .eq(UneteTipoParametro.MOTIVORECHAZOSE.tipo)
                    )
                    .queryList()

                entities.filter { it.tipoRechazo != null }.forEach { entity ->
                    entity.tipoRechazoExplanation = tipoRechazoList.find {
                        it.valor == entity?.tipoRechazo
                    }?.nombre
                }

                entities

            } catch (ex: Exception) {
                throw DBDataStoreException(ex)
            }
        }
    }


    fun listPre(estados: List<String>, seccion: String?): Single<List<PrePostulanteEntity>> {
        return doOnSingle {
            try {
                if (estados.isNotEmpty()) {
                    getPrepostulantesConEstados(seccion, estados)
                } else {
                    getPrepostulantesSinEstados(seccion)
                }
            } catch (ex: Exception) {
                throw DBDataStoreException(ex)
            }
        }
    }

    private fun getPrepostulantesConEstados(
        seccion: String?, estados: List<String>
    ): MutableList<PrePostulanteEntity> {
        return if (seccion.isNullOrEmpty())
            Select().from(PrePostulanteEntity::class.java)
                .where(
                    PrePostulanteEntity_Table.EstadoPrePostulante.`in`(estados),
                    PostulanteEntity_Table.uuid.isNot(Constant.EMPTY_STRING)
                )
                .orderBy(
                    OrderBy.fromProperty(PrePostulanteEntity_Table.FechaCreacion)
                        .descending()
                ).queryList()
        else
            Select().from(PrePostulanteEntity::class.java)
                .where(
                    PrePostulanteEntity_Table.EstadoPrePostulante.`in`(estados),
                    PostulanteEntity_Table.CodigoSeccion.eq(seccion),
                    PostulanteEntity_Table.uuid.isNot(Constant.EMPTY_STRING)
                )
                .orderBy(
                    OrderBy.fromProperty(PrePostulanteEntity_Table.FechaCreacion)
                        .descending()
                ).queryList()
    }

    private fun getPrepostulantesSinEstados(
        seccion: String?
    ): MutableList<PrePostulanteEntity> {
        return if (seccion.isNullOrEmpty())
            Select().from(PrePostulanteEntity::class.java)
                .where(PostulanteEntity_Table.uuid.isNot(Constant.EMPTY_STRING))
                .orderBy(
                    OrderBy.fromProperty(PrePostulanteEntity_Table.FechaCreacion)
                        .descending()
                ).queryList()
        else
            Select().from(PrePostulanteEntity::class.java)
                .where(
                    PrePostulanteEntity_Table.Codigoseccion.eq(seccion),
                    PostulanteEntity_Table.uuid.isNot(Constant.EMPTY_STRING)
                )
                .orderBy(
                    OrderBy.fromProperty(PrePostulanteEntity_Table.FechaCreacion)
                        .descending()
                ).queryList()
    }


    fun create(postulante: PostulanteEntity): Single<Int> {
        return doOnSingle {
            try {
                postulante.save()
                postulante.solicitudPostulanteID
            } catch (ex: Exception) {
                throw DBDataStoreException(ex)
            }
        }
    }

    fun create(postulantes: List<PostulanteEntity>): Single<Int> {
        return doOnSingle {
            try {
                FlowManager.getDatabase(AppDatabase::class.java)
                    .executeTransaction { databaseWrapper ->
                        Delete().from(PostulanteEntity::class.java)
                            .where(PostulanteEntity_Table.Sincronizado.eq(true)).execute()

                        postulantes.forEach {
                            val entity = Select().from(PostulanteEntity::class.java)
                                .where(PostulanteEntity_Table.SolicitudPostulanteID.eq(it.solicitudPostulanteID))
                                .querySingle()
                            if (entity == null) {
                                it.insert(databaseWrapper)
                            }
                        }
                    }
                1
            } catch (ex: Exception) {
                throw DBDataStoreException(ex)
            }
        }
    }

    fun createPre(prepostulantes: List<PrePostulanteEntity>): Single<Int> {
        return doOnSingle {
            try {
                FlowManager.getDatabase(AppDatabase::class.java)
                    .executeTransaction { databaseWrapper ->

                        Delete().from(PrePostulanteEntity::class.java)
                            .where(PrePostulanteEntity_Table.Sincronizado.eq(true)).execute()

                        prepostulantes.forEach {
                            val entity = Select().from(PrePostulanteEntity::class.java)
                                .where(PrePostulanteEntity_Table.SolicitudPrePostulanteID.eq(it.solicitudPrePostulanteID))
                                .querySingle()
                            if (entity == null) {
                                it.insert(databaseWrapper)
                            }
                        }
                    }
                1
            } catch (ex: Exception) {
                throw DBDataStoreException(ex)
            }
        }
    }

    fun update(postulante: PostulanteEntity): Single<Int> {
        return doOnSingle {
            try {
                postulante.update()
                postulante.solicitudPostulanteID
            } catch (ex: Exception) {
                throw DBDataStoreException(ex)
            }
        }
    }

    fun update(prePostulante: PrePostulanteEntity): Single<Int> {
        return doOnSingle {
            try {
                prePostulante.update()
                prePostulante.solicitudPrePostulanteID
            } catch (ex: Exception) {
                throw DBDataStoreException(ex)
            }
        }
    }

    fun update(postulantes: List<PostulanteEntity>): Single<Int> {
        return doOnSingle {
            try {
                FlowManager.getDatabase(AppDatabase::class.java)
                    .executeTransaction { _ ->
                        postulantes.forEach {
                            it.update()
                        }
                    }
                1
            } catch (ex: Exception) {
                throw DBDataStoreException(ex)
            }
        }
    }

}
