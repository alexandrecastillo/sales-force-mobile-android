package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.focos.data.dashboard

import biz.belcorp.salesforce.core.entities.sql.campania.CampaniaUaEntity
import biz.belcorp.salesforce.core.entities.sql.campania.CampaniaUaEntity_Table
import biz.belcorp.salesforce.core.entities.sql.directorio.SociaEmpresariaEntity
import biz.belcorp.salesforce.core.entities.sql.directorio.SociaEmpresariaEntity_Table
import biz.belcorp.salesforce.core.entities.sql.focos.FocoDetalleRddEntity
import biz.belcorp.salesforce.core.entities.sql.focos.FocoDetalleRddEntity_Table
import biz.belcorp.salesforce.core.entities.sql.focos.dashboard.SeccionFocosJoined
import biz.belcorp.salesforce.core.entities.sql.ua.SeccionEntity
import biz.belcorp.salesforce.core.entities.sql.ua.SeccionEntity_Table
import com.raizlabs.android.dbflow.kotlinextensions.*
import com.raizlabs.android.dbflow.sql.language.Select

class SeccionesDbDataStore {

    fun obtenerModelosSeccion(): MutableList<SeccionFocosJoined> {
        val where = (Select(
            SeccionEntity_Table.Codigo.withTable().`as`("CodigoSeccion"),
            SociaEmpresariaEntity_Table.ConsultorasId.withTable().`as`("ConsultoraID"),
            CampaniaUaEntity_Table.Codigo.withTable().`as`("CodigoCampania"),
            CampaniaUaEntity_Table.NombreCorto.withTable(),
            CampaniaUaEntity_Table.FechaInicio.withTable(),
            CampaniaUaEntity_Table.FechaInicioFacturacion.withTable(),
            CampaniaUaEntity_Table.FechaFin.withTable(),
            CampaniaUaEntity_Table.Periodo.withTable(),
            CampaniaUaEntity_Table.PrimerDiaFacturacion.withTable(),
            SociaEmpresariaEntity_Table.PrimerNombre.withTable(),
            SociaEmpresariaEntity_Table.PrimerApellido.withTable(),
            FocoDetalleRddEntity_Table.Focos.withTable()
        )
            from SeccionEntity::class

            leftOuterJoin CampaniaUaEntity::class
            on ((SeccionEntity_Table.Region.withTable()
            eq CampaniaUaEntity_Table.Region.withTable())
            and (SeccionEntity_Table.Zona.withTable()
            eq CampaniaUaEntity_Table.Zona.withTable())
            and (SeccionEntity_Table.Codigo.withTable()
            eq CampaniaUaEntity_Table.Seccion.withTable())
            and (CampaniaUaEntity_Table.Orden.withTable()
            eq CampaniaUaEntity.ORDEN_CAMPANIA_ACTUAL))

            leftOuterJoin FocoDetalleRddEntity::class
            on (SeccionEntity_Table.Codigo.withTable()
            eq FocoDetalleRddEntity_Table.Seccion.withTable())

            leftOuterJoin SociaEmpresariaEntity::class
            on (SeccionEntity_Table.ConsultoraCodigo.withTable()
            eq SociaEmpresariaEntity_Table.Codigo.withTable())

            orderBy (SeccionEntity_Table.Codigo.withTable().asc()))

        return where.queryCustomList(SeccionFocosJoined::class.java)
    }
}
