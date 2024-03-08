package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.focos.data.asignacion

import biz.belcorp.salesforce.core.entities.sql.campania.CampaniaUaEntity
import biz.belcorp.salesforce.core.entities.sql.campania.CampaniaUaEntity_Table
import biz.belcorp.salesforce.core.entities.sql.directorio.DirectorioVentaUsuarioEntity
import biz.belcorp.salesforce.core.entities.sql.directorio.DirectorioVentaUsuarioEntity_Table
import biz.belcorp.salesforce.core.entities.sql.focos.asignacion.GrCampaniaJoined
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.focos.asignacion.GrMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.GerenteRegionRdd
import com.raizlabs.android.dbflow.kotlinextensions.*
import com.raizlabs.android.dbflow.sql.language.Select

class ListadoGrDBDataStore(private val grMapper: GrMapper) {

    fun obtenerGRs(): List<GerenteRegionRdd> {
        val where = (Select(
            DirectorioVentaUsuarioEntity_Table.consultoraID.withTable(),
            DirectorioVentaUsuarioEntity_Table.CodRegion.withTable(),
            DirectorioVentaUsuarioEntity_Table.PrimerNombre.withTable(),
            DirectorioVentaUsuarioEntity_Table.PrimerApellido.withTable(),
            DirectorioVentaUsuarioEntity_Table.Usuario.withTable(),
            DirectorioVentaUsuarioEntity_Table.Estado.withTable(),
            CampaniaUaEntity_Table.Codigo.withTable(),
            CampaniaUaEntity_Table.FechaInicio.withTable(),
            CampaniaUaEntity_Table.FechaInicioFacturacion.withTable(),
            CampaniaUaEntity_Table.FechaFin.withTable(),
            CampaniaUaEntity_Table.Periodo.withTable(),
            CampaniaUaEntity_Table.PrimerDiaFacturacion.withTable())

            from DirectorioVentaUsuarioEntity::class
            innerJoin CampaniaUaEntity::class
            on ((DirectorioVentaUsuarioEntity_Table.CodRegion.withTable()
            eq CampaniaUaEntity_Table.Region.withTable())
            and (CampaniaUaEntity_Table.Zona.withTable()
            eq "-")
            and (CampaniaUaEntity_Table.Seccion.withTable()
            eq "-")
            and (CampaniaUaEntity_Table.Orden.withTable()
            eq CampaniaUaEntity.ORDEN_CAMPANIA_ACTUAL))
            where (DirectorioVentaUsuarioEntity_Table.Usuario.isNotNull
            and (DirectorioVentaUsuarioEntity_Table.CodRol eq Rol.GERENTE_REGION.codigoRol))
            orderBy (DirectorioVentaUsuarioEntity_Table.CodRegion.asc()))

        return grMapper.parsearGRs(
            where.queryCustomList(GrCampaniaJoined::class.java))
    }
}
