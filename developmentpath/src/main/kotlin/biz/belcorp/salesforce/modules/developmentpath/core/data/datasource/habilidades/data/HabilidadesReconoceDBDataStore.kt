package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.habilidades.data

import biz.belcorp.salesforce.core.entities.sql.directorio.DirectorioVentaUsuarioEntity
import biz.belcorp.salesforce.core.entities.sql.directorio.DirectorioVentaUsuarioEntity_Table
import biz.belcorp.salesforce.core.entities.sql.habilidades.CampaniaHabilidadesJoinned
import biz.belcorp.salesforce.core.entities.sql.habilidades.ReconocimientoHabilidadesRDDEntity
import biz.belcorp.salesforce.core.entities.sql.habilidades.ReconocimientoHabilidadesRDDEntity_Table
import biz.belcorp.salesforce.core.utils.doOnCompletable
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.habilidades.HabilidadMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.habilidades.HabilidadesReconocidasCampania
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.habilidades.reconocer.HabilidadesReconoceRepository
import com.google.gson.Gson
import com.raizlabs.android.dbflow.kotlinextensions.*
import com.raizlabs.android.dbflow.sql.language.Method
import com.raizlabs.android.dbflow.sql.language.Select
import io.reactivex.Completable

class HabilidadesReconoceDBDataStore(
    private val gson: Gson,
    private val mapper: HabilidadMapper
) : HabilidadesReconoceRepository {

    override fun obtener(personaId: Long, codigoCampania: String): Array<Long> {
        val where = (Select(
            ReconocimientoHabilidadesRDDEntity_Table.Habilidades.withTable())
            from ReconocimientoHabilidadesRDDEntity::class
            leftOuterJoin DirectorioVentaUsuarioEntity::class on
            (ReconocimientoHabilidadesRDDEntity_Table.Zona.withTable() eq
                DirectorioVentaUsuarioEntity_Table.CodZona.withTable() and (
                ReconocimientoHabilidadesRDDEntity_Table.Region.withTable() eq
                    DirectorioVentaUsuarioEntity_Table.CodRegion.withTable()))
            where (DirectorioVentaUsuarioEntity_Table.consultoraID.withTable() eq personaId)
            and (ReconocimientoHabilidadesRDDEntity_Table.Campania eq codigoCampania))

        val modeloDetalles = where.querySingle()
        return gson.fromJson(modeloDetalles?.habilidades
            ?: Constant.EMPTY_ARRAY, Array<Long>::class.java)
    }

    override fun obtener(personaId: Long): List<HabilidadesReconocidasCampania> {
        val where = (Select(
            ReconocimientoHabilidadesRDDEntity_Table.Campania.withTable(),
            ReconocimientoHabilidadesRDDEntity_Table.Region.withTable(),
            ReconocimientoHabilidadesRDDEntity_Table.Habilidades.withTable(),
            DirectorioVentaUsuarioEntity_Table.CodZona.withTable())

            from ReconocimientoHabilidadesRDDEntity::class
            leftOuterJoin DirectorioVentaUsuarioEntity::class on
            (ReconocimientoHabilidadesRDDEntity_Table.Zona.withTable() eq
                DirectorioVentaUsuarioEntity_Table.CodZona.withTable() and (
                ReconocimientoHabilidadesRDDEntity_Table.Region.withTable() eq
                    DirectorioVentaUsuarioEntity_Table.CodRegion.withTable()))
            where (DirectorioVentaUsuarioEntity_Table.consultoraID.withTable() eq personaId))

        val habilidadesReconocidas = where.queryCustomList(CampaniaHabilidadesJoinned::class.java)
        return habilidadesReconocidas.map { mapper.parsearHabilidadReconocidaJoinned(it) }
    }

    override fun obtenerReconocidasNoEnvidas(): List<HabilidadesReconoceRepository.HabilidadReconocida> {
        val where = (select from ReconocimientoHabilidadesRDDEntity::class
            where (ReconocimientoHabilidadesRDDEntity_Table.Enviado eq Constant.CERO))

        return where.queryList().map { mapper.parsearHabilidadReconocida(it) }
    }

    override fun marcarReconocidasComoEnviadas() {
        val update = com.raizlabs.android.dbflow.kotlinextensions.update<ReconocimientoHabilidadesRDDEntity>()
            .set(ReconocimientoHabilidadesRDDEntity_Table.Enviado eq Constant.UNO)
            .where(ReconocimientoHabilidadesRDDEntity_Table.Enviado eq Constant.CERO)
        update.execute()
    }

    override fun contarReconocimientos(personaId: Long, codigoCampania: String): Long {
        return (Select(Method.count())
            from ReconocimientoHabilidadesRDDEntity::class
            innerJoin DirectorioVentaUsuarioEntity::class
            on ((ReconocimientoHabilidadesRDDEntity_Table.Region.withTable()
            eq DirectorioVentaUsuarioEntity_Table.CodRegion.withTable())
            and (ReconocimientoHabilidadesRDDEntity_Table.Zona.withTable()
            eq DirectorioVentaUsuarioEntity_Table.CodZona.withTable()))
            where (DirectorioVentaUsuarioEntity_Table.consultoraID eq personaId)
            and (ReconocimientoHabilidadesRDDEntity_Table.Campania eq codigoCampania))
            .longValue()
    }

    override fun guardar(request: HabilidadesReconoceRepository.Request): Completable {
        return doOnCompletable {
            val habilidades = gson.toJson(request.habilidades)
            val model = ReconocimientoHabilidadesRDDEntity()
            model.habilidades = habilidades
            model.campania = request.campania
            model.enviado = Constant.CERO
            model.region = request.codigoRegion
            model.usuarioReconoce = request.usuarioReconoce
            model.usuarioReconocida = request.usuarioReconocida
            model.seccion = request.codigoSeccion ?: Constant.HYPHEN
            model.zona = request.codigoZona ?: Constant.HYPHEN
            model.save()
        }
    }
}
