package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.focos.data.asignacion

import biz.belcorp.salesforce.core.entities.sql.campania.CampaniaUaEntity
import biz.belcorp.salesforce.core.entities.sql.campania.CampaniaUaEntity_Table
import biz.belcorp.salesforce.core.entities.sql.directorio.SociaEmpresariaEntity
import biz.belcorp.salesforce.core.entities.sql.directorio.SociaEmpresariaEntity_Table
import biz.belcorp.salesforce.core.entities.sql.focos.asignacion.SeCampaniaJoined
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.focos.asignacion.SociaMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.SociaEmpresariaRdd
import com.raizlabs.android.dbflow.kotlinextensions.*
import com.raizlabs.android.dbflow.sql.language.Select

class ListadoSeDBDataStore(private val sociaMapper: SociaMapper) {

    fun obtenerSocias(): List<SociaEmpresariaRdd> {
        val where = (Select(
            SociaEmpresariaEntity_Table.ConsultorasId.withTable(),
            SociaEmpresariaEntity_Table.Region.withTable(),
            SociaEmpresariaEntity_Table.Zona.withTable(),
            SociaEmpresariaEntity_Table.Seccion.withTable(),
            SociaEmpresariaEntity_Table.PrimerNombre.withTable(),
            SociaEmpresariaEntity_Table.PrimerApellido.withTable(),
            CampaniaUaEntity_Table.Codigo.withTable(),
            CampaniaUaEntity_Table.FechaInicio.withTable(),
            CampaniaUaEntity_Table.FechaInicioFacturacion.withTable(),
            CampaniaUaEntity_Table.FechaFin.withTable(),
            CampaniaUaEntity_Table.Periodo.withTable(),
            CampaniaUaEntity_Table.FechaInicioFacturacion.withTable())
            from SociaEmpresariaEntity::class
            innerJoin CampaniaUaEntity::class
            on ((SociaEmpresariaEntity_Table.Region.withTable()
            eq CampaniaUaEntity_Table.Region.withTable())
            and (SociaEmpresariaEntity_Table.Zona.withTable()
            eq CampaniaUaEntity_Table.Zona.withTable())
            and (SociaEmpresariaEntity_Table.Seccion.withTable()
            eq CampaniaUaEntity_Table.Seccion.withTable())
            and (CampaniaUaEntity_Table.Orden.withTable()
            eq CampaniaUaEntity.ORDEN_CAMPANIA_ACTUAL))
            orderBy (SociaEmpresariaEntity_Table.Seccion.withTable().asc()))

        return sociaMapper.parsearSocias(where.queryCustomList(SeCampaniaJoined::class.java))
    }
}
