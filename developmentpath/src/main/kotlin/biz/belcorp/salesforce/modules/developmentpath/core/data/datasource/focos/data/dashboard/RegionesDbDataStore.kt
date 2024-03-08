package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.focos.data.dashboard

import biz.belcorp.salesforce.core.entities.sql.campania.CampaniaUaEntity
import biz.belcorp.salesforce.core.entities.sql.campania.CampaniaUaEntity_Table
import biz.belcorp.salesforce.core.entities.sql.directorio.DirectorioVentaUsuarioEntity
import biz.belcorp.salesforce.core.entities.sql.directorio.DirectorioVentaUsuarioEntity_Table
import biz.belcorp.salesforce.core.entities.sql.focos.FocoDetalleRddEntity
import biz.belcorp.salesforce.core.entities.sql.focos.FocoDetalleRddEntity_Table
import biz.belcorp.salesforce.core.entities.sql.focos.dashboard.RegionFocosJoinned
import biz.belcorp.salesforce.core.entities.sql.habilidades.HabilidadesAsignadasRDDEntity
import biz.belcorp.salesforce.core.entities.sql.habilidades.HabilidadesAsignadasRDDEntity_Table
import biz.belcorp.salesforce.core.entities.sql.ua.RegionEntity
import biz.belcorp.salesforce.core.entities.sql.ua.RegionEntity_Table
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import com.raizlabs.android.dbflow.kotlinextensions.*
import com.raizlabs.android.dbflow.sql.language.Select

class RegionesDbDataStore {

    fun obtenerModelosRegion(): MutableList<RegionFocosJoinned> {
        val where = (Select(
            RegionEntity_Table.Codigo.withTable(),
            DirectorioVentaUsuarioEntity_Table.consultoraID.withTable(),
            DirectorioVentaUsuarioEntity_Table.CodCliente.withTable(),
            DirectorioVentaUsuarioEntity_Table.Usuario.withTable(),
            DirectorioVentaUsuarioEntity_Table.PrimerNombre.withTable(),
            DirectorioVentaUsuarioEntity_Table.PrimerApellido.withTable(),
            CampaniaUaEntity_Table.Codigo.withTable().`as`("CodigoCampania"),
            CampaniaUaEntity_Table.NombreCorto.withTable(),
            CampaniaUaEntity_Table.FechaInicio.withTable(),
            CampaniaUaEntity_Table.FechaInicioFacturacion.withTable(),
            CampaniaUaEntity_Table.FechaFin.withTable(),
            CampaniaUaEntity_Table.Periodo.withTable(),
            CampaniaUaEntity_Table.PrimerDiaFacturacion.withTable(),
            FocoDetalleRddEntity_Table.Focos.withTable(),
            HabilidadesAsignadasRDDEntity_Table.Habilidades.withTable())
            from RegionEntity::class

            leftOuterJoin DirectorioVentaUsuarioEntity::class
            on (DirectorioVentaUsuarioEntity_Table.CodRegion.withTable()
            eq RegionEntity_Table.Codigo.withTable()
            and (DirectorioVentaUsuarioEntity_Table.CodRol.withTable()
            eq Rol.GERENTE_REGION.codigoRol))

            leftOuterJoin CampaniaUaEntity::class
            on ((RegionEntity_Table.Codigo.withTable()
            eq CampaniaUaEntity_Table.Region.withTable())
            and (CampaniaUaEntity_Table.Zona.withTable() eq Constant.HYPHEN)
            and (CampaniaUaEntity_Table.Seccion.withTable() eq  Constant.HYPHEN)
            and (CampaniaUaEntity_Table.Orden.withTable()
            eq CampaniaUaEntity.ORDEN_CAMPANIA_ACTUAL))

            leftOuterJoin FocoDetalleRddEntity::class
            on ((RegionEntity_Table.Codigo.withTable()
            eq FocoDetalleRddEntity_Table.Region.withTable())
            and (FocoDetalleRddEntity_Table.Zona.withTable() eq Constant.HYPHEN)
            and (FocoDetalleRddEntity_Table.Seccion.withTable() eq  Constant.HYPHEN)
            and (FocoDetalleRddEntity_Table.Campania.withTable()
            eq CampaniaUaEntity_Table.Codigo.withTable())
            and (CampaniaUaEntity_Table.Orden.withTable()
            eq CampaniaUaEntity.ORDEN_CAMPANIA_ACTUAL))

            leftOuterJoin HabilidadesAsignadasRDDEntity::class
            on ((RegionEntity_Table.Codigo.withTable()
            eq HabilidadesAsignadasRDDEntity_Table.Region.withTable())
            and (HabilidadesAsignadasRDDEntity_Table.Zona.withTable() eq Constant.HYPHEN)
            and (HabilidadesAsignadasRDDEntity_Table.Seccion.withTable() eq Constant.HYPHEN)
            and (HabilidadesAsignadasRDDEntity_Table.Campania.withTable()
            eq CampaniaUaEntity_Table.Codigo.withTable())
            and (CampaniaUaEntity_Table.Orden.withTable()
            eq CampaniaUaEntity.ORDEN_CAMPANIA_ACTUAL))

            orderBy (RegionEntity_Table.Codigo.withTable().asc()))

        return where.queryCustomList(RegionFocosJoinned::class.java)
    }
}
