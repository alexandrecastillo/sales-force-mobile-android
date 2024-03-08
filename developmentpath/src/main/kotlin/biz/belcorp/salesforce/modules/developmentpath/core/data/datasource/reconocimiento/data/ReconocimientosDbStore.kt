package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.reconocimiento.data

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.sql.campania.CampaniaUaEntity
import biz.belcorp.salesforce.core.entities.sql.campania.CampaniaUaEntity_Table
import biz.belcorp.salesforce.core.entities.sql.comportamientos.ComportamientoDetalleEntity
import biz.belcorp.salesforce.core.entities.sql.comportamientos.ComportamientoDetalleEntity_Table
import biz.belcorp.salesforce.core.utils.guionSiVacioONull
import biz.belcorp.salesforce.core.utils.update
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import com.raizlabs.android.dbflow.kotlinextensions.*

class ReconocimientosDbStore {

    fun obtenerReconocimientoCampaniaActual(llaveUA: LlaveUA): ComportamientoDetalleEntity? {
        val where = (select from ComportamientoDetalleEntity::class
            innerJoin CampaniaUaEntity::class
            on ((CampaniaUaEntity_Table.Region.withTable()
            eq ComportamientoDetalleEntity_Table.Region.withTable())
            and (CampaniaUaEntity_Table.Zona.withTable()
            eq ComportamientoDetalleEntity_Table.Zona.withTable())
            and (CampaniaUaEntity_Table.Seccion.withTable()
            eq ComportamientoDetalleEntity_Table.Seccion.withTable())
            and (CampaniaUaEntity_Table.Codigo.withTable()
            eq ComportamientoDetalleEntity_Table.Campania.withTable())
            and (CampaniaUaEntity_Table.Orden.withTable()
            eq CampaniaUaEntity.ORDEN_CAMPANIA_ACTUAL))
            where (ComportamientoDetalleEntity_Table.Region.withTable()
            eq llaveUA.codigoRegion.guionSiVacioONull())
            and (ComportamientoDetalleEntity_Table.Zona.withTable()
            eq llaveUA.codigoZona.guionSiVacioONull())
            and (ComportamientoDetalleEntity_Table.Seccion.withTable()
            eq llaveUA.codigoSeccion.guionSiVacioONull())
            and (ComportamientoDetalleEntity_Table.ConsultoraID.withTable()
            eq (llaveUA.consultoraId ?: Constant.MENOS_UNO.toLong())))

        return where.querySingle()
    }

    fun obtenerReconocimientos(llaveUA: LlaveUA): List<ComportamientoDetalleEntity> {
        val where = (select from ComportamientoDetalleEntity::class
            innerJoin CampaniaUaEntity::class
            on ((CampaniaUaEntity_Table.Region.withTable()
            eq ComportamientoDetalleEntity_Table.Region.withTable())
            and (CampaniaUaEntity_Table.Zona.withTable()
            eq ComportamientoDetalleEntity_Table.Zona.withTable())
            and (CampaniaUaEntity_Table.Seccion.withTable()
            eq ComportamientoDetalleEntity_Table.Seccion.withTable()))
            where (ComportamientoDetalleEntity_Table.Region.withTable()
            eq llaveUA.codigoRegion.guionSiVacioONull())
            and (ComportamientoDetalleEntity_Table.Zona.withTable()
            eq llaveUA.codigoZona.guionSiVacioONull())
            and (ComportamientoDetalleEntity_Table.Seccion.withTable()
            eq llaveUA.codigoSeccion.guionSiVacioONull())
            and (ComportamientoDetalleEntity_Table.ConsultoraID.withTable()
            eq (llaveUA.consultoraId ?: Constant.MENOS_UNO.toLong())))

        return where.queryList()
    }

    fun obtenerPendientesDeEnvio(): List<ComportamientoDetalleEntity> {
        val where = (select from ComportamientoDetalleEntity::class
            where (ComportamientoDetalleEntity_Table.Enviado.withTable()
            eq false))
        return where.queryList()
    }

    fun ponerTodosComoEnviado(list: List<ComportamientoDetalleEntity>) {
        list.forEach { it.enviado = true }
        list.takeIf { it.isNotEmpty() }?.update()
    }

    fun guardar(modelo: ComportamientoDetalleEntity) {
        modelo.save()
    }

    fun obtnerReconocimientosCampania(personaId: Long, codigoCampania: String): ComportamientoDetalleEntity? {
        val where = (select from ComportamientoDetalleEntity::class
            where (ComportamientoDetalleEntity_Table.ConsultoraID.withTable()
            eq personaId)
            and (ComportamientoDetalleEntity_Table.Campania.withTable()
            eq codigoCampania))
        return where.querySingle()
    }

}
