package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.unidadadministrativa

import biz.belcorp.salesforce.core.entities.sql.campania.CampaniaUaEntity
import biz.belcorp.salesforce.core.entities.sql.campania.CampaniaUaEntity_Table
import biz.belcorp.salesforce.core.entities.sql.directorio.DirectorioVentaUsuarioEntity
import biz.belcorp.salesforce.core.entities.sql.directorio.DirectorioVentaUsuarioEntity_Table
import biz.belcorp.salesforce.core.entities.sql.plan.PlanRutaRDDEntity
import biz.belcorp.salesforce.core.entities.sql.plan.PlanRutaRDDEntity_Table
import biz.belcorp.salesforce.core.entities.sql.ua.RegionEntity
import biz.belcorp.salesforce.core.entities.sql.ua.RegionEntity_Table
import biz.belcorp.salesforce.core.entities.sql.ua.RegionGrJoinned
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.doOnSingle
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import com.raizlabs.android.dbflow.kotlinextensions.*
import com.raizlabs.android.dbflow.sql.language.Select
import io.reactivex.Single

class RegionDBDataStore {

    fun recuperarAvance(): Single<List<RegionGrJoinned>> {
        return doOnSingle { recuperarAvancesSincrono() }
    }

    private fun recuperarAvancesSincrono(): List<RegionGrJoinned> {

        val query = (Select(
            RegionEntity_Table.Codigo.withTable(),
            CampaniaUaEntity_Table.Codigo.withTable().`as`("CodigoCampania"),
            CampaniaUaEntity_Table.NombreCorto.withTable(),
            CampaniaUaEntity_Table.FechaInicio.withTable(),
            CampaniaUaEntity_Table.FechaInicioFacturacion.withTable(),
            CampaniaUaEntity_Table.FechaFin.withTable(),
            CampaniaUaEntity_Table.Periodo.withTable(),
            CampaniaUaEntity_Table.PrimerDiaFacturacion.withTable(),
            PlanRutaRDDEntity_Table.ID.withTable(),
            PlanRutaRDDEntity_Table.TotalPlanificadas.withTable(),
            PlanRutaRDDEntity_Table.TotalVisitadas.withTable(),
            DirectorioVentaUsuarioEntity_Table.consultoraID.withTable(),
            DirectorioVentaUsuarioEntity_Table.PrimerNombre.withTable(),
            DirectorioVentaUsuarioEntity_Table.PrimerApellido.withTable(),
            DirectorioVentaUsuarioEntity_Table.Estado.withTable(),
            DirectorioVentaUsuarioEntity_Table.MailBelcorp.withTable(),
            DirectorioVentaUsuarioEntity_Table.Usuario.withTable())
            from RegionEntity::class

            leftOuterJoin DirectorioVentaUsuarioEntity::class
            on ((RegionEntity_Table.Codigo.withTable()
            eq DirectorioVentaUsuarioEntity_Table.CodRegion.withTable())
            and (DirectorioVentaUsuarioEntity_Table.CodRol.withTable()
            eq Rol.Builder.COD_GERENTE_REGION))

            leftOuterJoin CampaniaUaEntity::class
            on ((RegionEntity_Table.Codigo.withTable()
            eq CampaniaUaEntity_Table.Region.withTable())
            and (CampaniaUaEntity_Table.Zona.withTable() eq Constant.HYPHEN)
            and (CampaniaUaEntity_Table.Seccion.withTable() eq Constant.HYPHEN)
            and (CampaniaUaEntity_Table.Orden.withTable()
            eq CampaniaUaEntity.ORDEN_CAMPANIA_ACTUAL))

            leftOuterJoin PlanRutaRDDEntity::class
            on ((RegionEntity_Table.Codigo.withTable()
            eq PlanRutaRDDEntity_Table.Region.withTable())
            and (PlanRutaRDDEntity_Table.Zona.withTable().isNull)
            and (PlanRutaRDDEntity_Table.Seccion.withTable().isNull)
            and (PlanRutaRDDEntity_Table.Campania.withTable()
            eq CampaniaUaEntity_Table.Codigo.withTable())
            and (CampaniaUaEntity_Table.Orden.withTable()
            eq CampaniaUaEntity.ORDEN_CAMPANIA_ACTUAL))

            orderBy (RegionEntity_Table.Codigo.withTable().asc()))

        return query.queryCustomList(RegionGrJoinned::class.java)
    }
}
