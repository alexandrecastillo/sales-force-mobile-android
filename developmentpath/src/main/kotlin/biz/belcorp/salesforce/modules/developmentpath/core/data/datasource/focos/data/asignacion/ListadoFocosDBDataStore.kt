package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.focos.data.asignacion


import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.sql.campania.CampaniaUaEntity
import biz.belcorp.salesforce.core.entities.sql.campania.CampaniaUaEntity_Table
import biz.belcorp.salesforce.core.entities.sql.focos.*
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.doOnCompletable
import biz.belcorp.salesforce.core.utils.doOnSingle
import biz.belcorp.salesforce.core.utils.guionSiNull
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.focos.FocoRddMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.focos.DetalleFocoSE
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.focos.Foco
import com.raizlabs.android.dbflow.kotlinextensions.*
import io.reactivex.Completable
import io.reactivex.Single

class ListadoFocosDBDataStore(private val focoRddMapper: FocoRddMapper,
                              private val focosSeStore: FocosSeDBDataStore) {

    fun obtenerFocos(): List<Foco> {
        val where = select from FocoRddEntity::class
        return focoRddMapper.parsearFocos(where.queryList())
    }

    fun obtenerFocosPorUa(llaveUA: LlaveUA): Array<Long> {
        val where = (select from FocoDetalleRddEntity::class
            innerJoin CampaniaUaEntity::class
            on ((FocoDetalleRddEntity_Table.Campania.withTable()
            eq CampaniaUaEntity_Table.Codigo.withTable())
            and (FocoDetalleRddEntity_Table.Region.withTable()
            eq CampaniaUaEntity_Table.Region.withTable())
            and (FocoDetalleRddEntity_Table.Zona.withTable()
            eq CampaniaUaEntity_Table.Zona.withTable())
            and (FocoDetalleRddEntity_Table.Seccion.withTable()
            eq CampaniaUaEntity_Table.Seccion.withTable())
            and (CampaniaUaEntity_Table.Orden.withTable()
            eq CampaniaUaEntity.ORDEN_CAMPANIA_ACTUAL))
            where (FocoDetalleRddEntity_Table.Region.withTable()
            eq llaveUA.codigoRegion.guionSiNull())
            and (FocoDetalleRddEntity_Table.Zona.withTable()
            eq llaveUA.codigoZona.guionSiNull())
            and (FocoDetalleRddEntity_Table.Seccion.withTable()
            eq llaveUA.codigoSeccion.guionSiNull()))

        val modelData = where.querySingle()
        return focoRddMapper.parsearIds(modelData?.focos)
    }

    fun obtenerFocosSE(personaId: Long): Array<Long> {
        return focosSeStore.obtenerFocosSe(personaId)
    }

    fun recuperarFocosPara(personaId: Long, rol: Rol): List<Foco> {
        val focosId = obtenerFocosSE(personaId)
        return focosId.mapNotNull { recuperarFocoPorId(it) }
    }

    private fun recuperarFocoPorId(focoId: Long): Foco? {
        val query = (select from FocoRddEntity::class
            where (FocoRddEntity_Table.Codigo eq focoId))
        val modelo = query.querySingle()
        return if (modelo != null) focoRddMapper.parsearFoco(modelo) else null
    }

    fun recuperarPendientes(): Single<List<DetalleFocoSE>> {
        return doOnSingle {
            obtenerDetallesNoEnviados()
        }
    }

    fun marcarComoEnviados(): Completable {
        return doOnCompletable {
            marcarDetallesComoEnviados()
        }
    }

    private fun marcarDetallesComoEnviados() {
        val update = update<FocoDetalleSERddEntity>()
            .set(FocoDetalleSERddEntity_Table.Enviado eq Constant.UNO)
            .where(FocoDetalleSERddEntity_Table.Enviado eq Constant.CERO)
        update.execute()
    }

    private fun obtenerDetallesNoEnviados(): List<DetalleFocoSE> {
        val where = (select from FocoDetalleSERddEntity::class
            where (FocoDetalleSERddEntity_Table.Enviado eq Constant.CERO))
        return focoRddMapper.parsearDetallesSE(where.queryList())
    }
}
