package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.comportamientos

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.sql.comportamientos.*
import biz.belcorp.salesforce.core.utils.guionSiVacioONull
import com.raizlabs.android.dbflow.kotlinextensions.*

class ComportamientoDetallePorcentajeDbStore {

    fun obtenerPorcentajes(llaveUA: LlaveUA, campania: String): List<ComportamientoPorcentajeJoinned> {
        val where = (select from ComportamientoEntity::class
            leftOuterJoin ComportamientoDetallePorcentajeEntity::class on (
            ComportamientoEntity_Table.Codigo.withTable() eq
                ComportamientoDetallePorcentajeEntity_Table.Comportamiento.withTable()
            )
            where (ComportamientoDetallePorcentajeEntity_Table.Region.withTable()
            eq llaveUA.codigoRegion.guionSiVacioONull())
            or (ComportamientoDetallePorcentajeEntity_Table.Region.withTable().isNull)
            and (ComportamientoDetallePorcentajeEntity_Table.Zona.withTable()
            eq llaveUA.codigoZona.guionSiVacioONull())
            or (ComportamientoDetallePorcentajeEntity_Table.Zona.withTable().isNull)
            and (ComportamientoDetallePorcentajeEntity_Table.Seccion.withTable()
            eq llaveUA.codigoSeccion.guionSiVacioONull())
            or (ComportamientoDetallePorcentajeEntity_Table.Seccion.withTable().isNull)
            and (ComportamientoDetallePorcentajeEntity_Table.Campania.withTable()
            eq campania.guionSiVacioONull())
            or (ComportamientoDetallePorcentajeEntity_Table.Campania.withTable().isNull)
            and (ComportamientoEntity_Table.Rol.withTable()
            eq llaveUA.rolHijoAsociado.codigoRol))
        return where.queryCustomList(ComportamientoPorcentajeJoinned::class.java)
    }

}
