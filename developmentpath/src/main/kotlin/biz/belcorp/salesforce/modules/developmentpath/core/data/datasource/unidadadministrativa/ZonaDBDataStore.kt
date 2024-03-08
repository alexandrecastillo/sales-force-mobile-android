package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.unidadadministrativa

import biz.belcorp.salesforce.core.entities.sql.campania.CampaniaUaEntity
import biz.belcorp.salesforce.core.entities.sql.campania.CampaniaUaEntity_Table
import biz.belcorp.salesforce.core.entities.sql.directorio.DirectorioVentaUsuarioEntity
import biz.belcorp.salesforce.core.entities.sql.directorio.DirectorioVentaUsuarioEntity_Table
import biz.belcorp.salesforce.core.entities.sql.plan.PlanRutaRDDEntity
import biz.belcorp.salesforce.core.entities.sql.plan.PlanRutaRDDEntity_Table
import biz.belcorp.salesforce.core.entities.sql.ua.ZonaEntity
import biz.belcorp.salesforce.core.entities.sql.ua.ZonaEntity_Table
import biz.belcorp.salesforce.core.entities.sql.ua.ZonaGzJoinned
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import com.raizlabs.android.dbflow.kotlinextensions.*
import com.raizlabs.android.dbflow.sql.language.Select

class ZonaDBDataStore : ZonaDataStore {

    override fun recuperarAvances(codigoRegion: String): List<ZonaGzJoinned> {

        val query = (Select(
            ZonaEntity_Table.Codigo.withTable(),
            ZonaEntity_Table.Region.withTable(),
            ZonaEntity_Table.GerenteZona.withTable(),
            CampaniaUaEntity_Table.Codigo.withTable().`as`("CodigoCampania"),
            CampaniaUaEntity_Table.NombreCorto.withTable(),
            CampaniaUaEntity_Table.FechaInicio.withTable(),
            CampaniaUaEntity_Table.FechaInicioFacturacion.withTable(),
            CampaniaUaEntity_Table.FechaFin.withTable(),
            PlanRutaRDDEntity_Table.ID.withTable(),
            PlanRutaRDDEntity_Table.TotalPlanificadas.withTable(),
            PlanRutaRDDEntity_Table.TotalVisitadas.withTable(),
            DirectorioVentaUsuarioEntity_Table.consultoraID.withTable(),
            DirectorioVentaUsuarioEntity_Table.PrimerNombre.withTable(),
            DirectorioVentaUsuarioEntity_Table.PrimerApellido.withTable(),
            DirectorioVentaUsuarioEntity_Table.Estado.withTable(),
            DirectorioVentaUsuarioEntity_Table.Usuario.withTable())
            from ZonaEntity::class

            leftOuterJoin DirectorioVentaUsuarioEntity::class
            on ((ZonaEntity_Table.Region.withTable()
            eq DirectorioVentaUsuarioEntity_Table.CodRegion.withTable())
            and (ZonaEntity_Table.Codigo.withTable()
            eq DirectorioVentaUsuarioEntity_Table.CodZona.withTable()))

            leftOuterJoin CampaniaUaEntity::class
            on ((ZonaEntity_Table.Region.withTable()
            eq CampaniaUaEntity_Table.Region.withTable())
            and (ZonaEntity_Table.Codigo.withTable()
            eq CampaniaUaEntity_Table.Zona.withTable())
            and (CampaniaUaEntity_Table.Seccion.withTable() eq "-")
            and (CampaniaUaEntity_Table.Orden.withTable()
            eq CampaniaUaEntity.ORDEN_CAMPANIA_ACTUAL))

            leftOuterJoin PlanRutaRDDEntity::class
            on ((ZonaEntity_Table.Region.withTable()
            eq PlanRutaRDDEntity_Table.Region.withTable())
            and (ZonaEntity_Table.Codigo.withTable()
            eq PlanRutaRDDEntity_Table.Zona.withTable())
            and (PlanRutaRDDEntity_Table.Rol.withTable()
            eq Rol.GERENTE_ZONA.codigoRol)
            and (PlanRutaRDDEntity_Table.Campania.withTable()
            eq CampaniaUaEntity_Table.Codigo.withTable())
            and (CampaniaUaEntity_Table.Orden.withTable()
            eq CampaniaUaEntity.ORDEN_CAMPANIA_ACTUAL))

            where (ZonaEntity_Table.Region.withTable() eq codigoRegion)
            orderBy (ZonaEntity_Table.Codigo.withTable().asc()))

        return query.queryCustomList(ZonaGzJoinned::class.java)
    }
}
