package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.acuerdo.data

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.sql.campania.CampaniaUaEntity
import biz.belcorp.salesforce.core.entities.sql.campania.CampaniaUaEntity_Table
import biz.belcorp.salesforce.core.entities.sql.datos.AcuerdoEntity
import biz.belcorp.salesforce.core.entities.sql.datos.AcuerdoEntity_Table
import biz.belcorp.salesforce.core.utils.guionSiVacioONull
import biz.belcorp.salesforce.core.utils.insert
import biz.belcorp.salesforce.core.utils.save
import biz.belcorp.salesforce.core.utils.update
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.toDate
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.toLongString
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.acuerdos.Acuerdo
import com.raizlabs.android.dbflow.kotlinextensions.*
import com.raizlabs.android.dbflow.sql.language.Delete
import io.reactivex.Completable
import io.reactivex.Single
import java.util.*

class AcuerdosLocalDataStore {

    fun obtener(unidadAdministrativa: LlaveUA): List<Acuerdo> {
        val where = (select from AcuerdoEntity::class
            where (AcuerdoEntity_Table.Region.withTable()
            eq (unidadAdministrativa.codigoRegion.guionSiVacioONull()))
            and (AcuerdoEntity_Table.Zona.withTable()
            eq (unidadAdministrativa.codigoZona.guionSiVacioONull()))
            and (AcuerdoEntity_Table.Seccion.withTable()
            eq (unidadAdministrativa.codigoSeccion.guionSiVacioONull()))
            and (AcuerdoEntity_Table.ConsultoraID.withTable()
            eq (unidadAdministrativa.consultoraId ?: Constant.MENOS_UNO.toLong()))
            and (AcuerdoEntity_Table.Activo eq Constant.UNO))

        val modelos = where.queryList()

        return modelos.mapNotNull { modelo -> mapearEntidad(modelo) }
    }

    fun obtenerCampaniasCXMenos1(
        unidadAdministrativa: LlaveUA,
        cantidadCampanias: Int
    ): List<Acuerdo> {
        val where = (select from AcuerdoEntity::class
            innerJoin CampaniaUaEntity::class
            on (AcuerdoEntity_Table.Campania.withTable()
            eq CampaniaUaEntity_Table.Codigo.withTable()
            and (AcuerdoEntity_Table.Region.withTable()
            eq CampaniaUaEntity_Table.Region.withTable())
            and (AcuerdoEntity_Table.Zona.withTable()
            eq CampaniaUaEntity_Table.Zona.withTable())
            and (AcuerdoEntity_Table.Seccion.withTable()
            eq CampaniaUaEntity_Table.Seccion.withTable())
            and (CampaniaUaEntity_Table.Orden.withTable()
            greaterThan (CampaniaUaEntity.ORDEN_CAMPANIA_ACTUAL))
            and (CampaniaUaEntity_Table.Orden.withTable()
            lessThanOrEq (CampaniaUaEntity.ORDEN_CAMPANIA_ACTUAL + cantidadCampanias)))
            where (AcuerdoEntity_Table.Region.withTable()
            eq (unidadAdministrativa.codigoRegion.guionSiVacioONull()))
            and (AcuerdoEntity_Table.Zona.withTable()
            eq (unidadAdministrativa.codigoZona.guionSiVacioONull()))
            and (AcuerdoEntity_Table.Seccion.withTable()
            eq (unidadAdministrativa.codigoSeccion.guionSiVacioONull()))
            and (AcuerdoEntity_Table.ConsultoraID.withTable()
            eq (unidadAdministrativa.consultoraId ?: Constant.MENOS_UNO.toLong()))
            and (AcuerdoEntity_Table.Activo.withTable() eq Constant.UNO))

        val modelos = where.queryList()

        return modelos.mapNotNull { modelo -> mapearEntidad(modelo) }
    }

    fun getCampaignByCode(uaKey: LlaveUA,
                          campaignCode: String): List<Acuerdo>{

        val where = (
            select from AcuerdoEntity::class
            innerJoin CampaniaUaEntity::class
            on (AcuerdoEntity_Table.Campania.withTable()
            eq CampaniaUaEntity_Table.Codigo.withTable()
            and (AcuerdoEntity_Table.Region.withTable()
            eq CampaniaUaEntity_Table.Region.withTable())
            and (AcuerdoEntity_Table.Zona.withTable()
            eq CampaniaUaEntity_Table.Zona.withTable())
            and (AcuerdoEntity_Table.Seccion.withTable()
            eq CampaniaUaEntity_Table.Seccion.withTable()))
            where  (AcuerdoEntity_Table.Region.withTable()
            eq (uaKey.codigoRegion.guionSiVacioONull()))
            and (AcuerdoEntity_Table.Zona.withTable()
            eq (uaKey.codigoZona.guionSiVacioONull()))
            and (AcuerdoEntity_Table.Seccion.withTable()
            eq (uaKey.codigoSeccion.guionSiVacioONull()))
            and (AcuerdoEntity_Table.ConsultoraID.withTable()
            eq (uaKey.consultoraId ?: Constant.MENOS_UNO.toLong()))
            and (AcuerdoEntity_Table.Activo.withTable() eq Constant.UNO))

        val models = where.queryList()
        return models.mapNotNull { model -> mapearEntidad(model)}

    }

    private fun mapearEntidad(modelo: AcuerdoEntity): Acuerdo? {
        return Acuerdo(
            id = modelo.idLocal,
            contenido = modelo.comentario ?: Constant.EMPTY_STRING,
            codigoCampania = modelo.campania ?: return null,
            fechaCreacion = modelo.fechaComentario.toDate() ?: return null,
            cumplido = modelo.cumplimiento == AcuerdoEntity.CUMPLIDO
        )
    }

    fun obtenerParaCampaniaActual(unidadAdministrativa: LlaveUA): List<Acuerdo> {
        val where = (select from AcuerdoEntity::class
            innerJoin CampaniaUaEntity::class
            on ((AcuerdoEntity_Table.Campania.withTable()
            eq CampaniaUaEntity_Table.Codigo.withTable())
            and (AcuerdoEntity_Table.Region.withTable()
            eq CampaniaUaEntity_Table.Region.withTable())
            and (AcuerdoEntity_Table.Zona.withTable()
            eq CampaniaUaEntity_Table.Zona.withTable())
            and (AcuerdoEntity_Table.Seccion.withTable()
            eq CampaniaUaEntity_Table.Seccion.withTable())
            and (CampaniaUaEntity_Table.Orden.withTable()
            eq CampaniaUaEntity.ORDEN_CAMPANIA_ACTUAL))
            where (AcuerdoEntity_Table.Region.withTable()
            eq (unidadAdministrativa.codigoRegion.guionSiVacioONull()))
            and (AcuerdoEntity_Table.Zona.withTable()
            eq (unidadAdministrativa.codigoZona.guionSiVacioONull()))
            and (AcuerdoEntity_Table.Seccion.withTable()
            eq (unidadAdministrativa.codigoSeccion.guionSiVacioONull()))
            and (AcuerdoEntity_Table.ConsultoraID.withTable()
            eq (unidadAdministrativa.consultoraId ?: Constant.MENOS_UNO.toLong()))
            and (AcuerdoEntity_Table.Activo eq Constant.UNO))

        val modelos = where.queryList()

        return modelos.mapNotNull { modelo -> mapearEntidad(modelo) }
    }


    fun obtener(id: Long): Acuerdo? {
        val where =
            (select from AcuerdoEntity::class where (AcuerdoEntity_Table.IdLocal.withTable() eq id))

        val modelo = where.querySingle() ?: return null

        return Acuerdo(
            id = modelo.idLocal,
            contenido = modelo.comentario ?: Constant.EMPTY_STRING,
            codigoCampania = requireNotNull(modelo.campania),
            fechaCreacion = Date(),
            cumplido = modelo.cumplimiento == AcuerdoEntity.CUMPLIDO
        )
    }

    fun insertar(acuerdos: List<Acuerdo.ModeloCreacion>) {
        val modelos = acuerdos.map { convertir(it) }
        modelos.save()
    }

    fun guardar(acuerdo: Acuerdo) {
        val modelo = (select from AcuerdoEntity::class
            where (AcuerdoEntity_Table.IdLocal.withTable() eq acuerdo.id)).querySingle()

        modelo?.comentario = acuerdo.contenido
        modelo?.cumplimiento = obtenerNumeroAsociadoACumplimiento(acuerdo.cumplido)
        modelo?.enviado = Constant.CERO

        modelo?.save()
    }

    private fun obtenerNumeroAsociadoACumplimiento(cumplido: Boolean): Int {
        return if (cumplido) Constant.UNO else Constant.CERO
    }

    fun eliminar(id: Long) {
        val modelo = (select from AcuerdoEntity::class
            where (AcuerdoEntity_Table.IdLocal.withTable() eq id)).querySingle()

        modelo?.activo = Constant.CERO
        modelo?.enviado = Constant.CERO

        modelo?.save()
    }

    private fun convertir(acuerdo: Acuerdo.ModeloCreacion): AcuerdoEntity {
        val modelo = AcuerdoEntity()

        modelo.campania = acuerdo.codigoCampania
        modelo.region = acuerdo.unidadAdministrativa.codigoRegion ?: "-"
        modelo.zona = acuerdo.unidadAdministrativa.codigoZona ?: "-"
        modelo.seccion = acuerdo.unidadAdministrativa.codigoSeccion ?: "-"
        modelo.consultoraId =
            acuerdo.unidadAdministrativa.consultoraId ?: Constant.MENOS_UNO.toLong()

        modelo.comentario = acuerdo.contenido
        modelo.fechaComentario = acuerdo.fecha.toLongString()
        modelo.activo = Constant.UNO
        modelo.enviado = Constant.CERO

        return modelo
    }

    fun recuperarEditadosNoEnviados(): Single<List<AcuerdoEntity>> {
        return Single.create {
            val where = (select
                from AcuerdoEntity::class
                where (AcuerdoEntity_Table.Activo eq Constant.UNO)
                and (AcuerdoEntity_Table.Enviado eq Constant.CERO)
                and (AcuerdoEntity_Table.ID.isNotNull))

            it.onSuccess(where.queryList())
        }
    }

    fun recuperarEliminadosNoEnviados(): Single<List<AcuerdoEntity>> {
        return Single.create {
            val where = (select
                from AcuerdoEntity::class
                where (AcuerdoEntity_Table.Activo eq Constant.CERO)
                and (AcuerdoEntity_Table.Enviado eq Constant.CERO)
                and (AcuerdoEntity_Table.ID.isNotNull))

            it.onSuccess(where.queryList())
        }
    }

    fun recuperarNuevosNoEnviados(): Single<List<AcuerdoEntity>> {
        return Single.create {
            val where = (select
                from AcuerdoEntity::class
                where (AcuerdoEntity_Table.Activo eq Constant.UNO)
                and (AcuerdoEntity_Table.Enviado eq Constant.CERO)
                and (AcuerdoEntity_Table.ID.isNull))

            it.onSuccess(where.queryList())
        }
    }

    fun marcarEliminadosComoEnviados(): Completable {
        return Completable.create {
            val query = update<AcuerdoEntity>()
                .set(AcuerdoEntity_Table.Enviado eq Constant.UNO)
                .where(
                    (AcuerdoEntity_Table.Enviado eq Constant.CERO)
                        and (AcuerdoEntity_Table.Activo eq Constant.CERO)
                )
            query.execute()

            it.onComplete()
        }
    }

    fun marcarEditadosComoEnviados(): Completable {
        return Completable.create {
            val query = update<AcuerdoEntity>()
                .set(AcuerdoEntity_Table.Enviado eq Constant.UNO)
                .where(
                    (AcuerdoEntity_Table.Enviado eq Constant.CERO)
                        and (AcuerdoEntity_Table.ID.isNotNull)
                        and (AcuerdoEntity_Table.Activo notEq Constant.CERO)
                )
            query.execute()

            it.onComplete()
        }
    }

    fun marcarNuevosComoEnviados(entidades: List<AcuerdoEntity>): Completable {
        return Completable.create {

            entidades.forEach { it.setearIdServer() }

            it.onComplete()
        }
    }

    private fun AcuerdoEntity.setearIdServer() {
        val query = com.raizlabs.android.dbflow.sql.language.Update(AcuerdoEntity::class.java)
            .set(
                AcuerdoEntity_Table.ID eq serverId,
                AcuerdoEntity_Table.Enviado eq Constant.UNO
            )
            .where(AcuerdoEntity_Table.IdLocal eq idLocal)

        query.execute()
    }

    fun eliminarSegunUa(acuerdo: AcuerdoEntity) {
        val query = Delete().from(AcuerdoEntity::class.java)
            .where(
                (AcuerdoEntity_Table.Region eq acuerdo.region)
                    and (AcuerdoEntity_Table.Zona eq acuerdo.zona)
                    and (AcuerdoEntity_Table.Seccion eq acuerdo.seccion)
                    and (AcuerdoEntity_Table.ConsultoraID eq acuerdo.consultoraId)
            )
        query.execute()
    }

    fun guardarSegunIdServer(acuerdos: List<AcuerdoEntity>) {
        val pair = separarExistentesNuevos(acuerdos)
        pair.first.update()
        pair.second.insert()
    }

    private fun separarExistentesNuevos(acuerdos: List<AcuerdoEntity>): Pair<List<AcuerdoEntity>, List<AcuerdoEntity>> {
        val query = (select from AcuerdoEntity::class)
        val acuerdosDB = query.queryList()
        val idAcuerdosDB = acuerdosDB.mapNotNull { it.serverId }
        val groups = acuerdos.partition { it.serverId in idAcuerdosDB }
        val existentes = manejarExistentes(groups.first, acuerdosDB)
        val nuevos = manejarNuevos(groups.second)
        return Pair(existentes, nuevos)
    }

    private fun manejarExistentes(
        acuerdosCloud: List<AcuerdoEntity>,
        acuerdosDB: List<AcuerdoEntity>
    ): List<AcuerdoEntity> {
        val mapAcuerdos = acuerdosCloud.associateBy({ it.serverId }, { it })
        return acuerdosDB.mapNotNull { acuerdoDB ->
            mapAcuerdos[acuerdoDB.serverId]?.let { acuerdoCloud ->
                acuerdoDB.apply {
                    enviado = Constant.UNO
                    activo = Constant.UNO
                    comentario = acuerdoCloud.comentario
                    fechaComentario = acuerdoCloud.fechaComentario
                    cumplimiento = acuerdoCloud.cumplimiento
                }
            }
        }
    }

    private fun manejarNuevos(acuerdosCloud: List<AcuerdoEntity>): List<AcuerdoEntity> {
        return acuerdosCloud.map { acuerdoCloud ->
            acuerdoCloud.apply {
                idLocal = Constant.CERO.toLong()
                activo = Constant.UNO
                enviado = Constant.UNO
            }
        }
    }

}
