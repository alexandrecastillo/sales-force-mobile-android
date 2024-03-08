package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.focos.data.dashboard

import biz.belcorp.salesforce.core.entities.sql.campania.CampaniaUaEntity
import biz.belcorp.salesforce.core.entities.sql.campania.CampaniaUaEntity_Table
import biz.belcorp.salesforce.core.entities.sql.directorio.DirectorioVentaUsuarioEntity
import biz.belcorp.salesforce.core.entities.sql.directorio.DirectorioVentaUsuarioEntity_Table
import biz.belcorp.salesforce.core.entities.sql.focos.FocoDetalleRddEntity
import biz.belcorp.salesforce.core.entities.sql.focos.FocoDetalleRddEntity_Table
import biz.belcorp.salesforce.core.entities.sql.focos.dashboard.ZonaFocosJoined
import biz.belcorp.salesforce.core.entities.sql.habilidades.HabilidadesAsignadasRDDEntity
import biz.belcorp.salesforce.core.entities.sql.habilidades.HabilidadesAsignadasRDDEntity_Table
import biz.belcorp.salesforce.core.entities.sql.ua.ZonaEntity
import biz.belcorp.salesforce.core.entities.sql.ua.ZonaEntity_Table
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import com.raizlabs.android.dbflow.kotlinextensions.*
import com.raizlabs.android.dbflow.sql.language.Select

class ZonasDbDataStore {

    fun obtenerModelosZona(): MutableList<ZonaFocosJoined> {
        val where = (Select(
            ZonaEntity_Table.Codigo.withTable(),
            ZonaEntity_Table.GerenteZona.withTable(),
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
            from ZonaEntity::class

            leftOuterJoin DirectorioVentaUsuarioEntity::class
            on ((DirectorioVentaUsuarioEntity_Table.CodZona.withTable()
            eq ZonaEntity_Table.Codigo.withTable())
            and (DirectorioVentaUsuarioEntity_Table.CodRol.withTable()
            eq Rol.GERENTE_ZONA.codigoRol))

            leftOuterJoin CampaniaUaEntity::class
            on ((ZonaEntity_Table.Region.withTable()
            eq CampaniaUaEntity_Table.Region.withTable())
            and (ZonaEntity_Table.Codigo.withTable()
            eq CampaniaUaEntity_Table.Zona.withTable())
            and (CampaniaUaEntity_Table.Seccion.withTable() eq Constant.HYPHEN)
            and (CampaniaUaEntity_Table.Orden.withTable()
            eq CampaniaUaEntity.ORDEN_CAMPANIA_ACTUAL))

            leftOuterJoin FocoDetalleRddEntity::class
            on ((ZonaEntity_Table.Codigo.withTable()
            eq FocoDetalleRddEntity_Table.Zona.withTable()
            and (FocoDetalleRddEntity_Table.Seccion.withTable()
            eq  Constant.HYPHEN))
            and (FocoDetalleRddEntity_Table.Campania.withTable()
            eq CampaniaUaEntity_Table.Codigo.withTable())
            and (CampaniaUaEntity_Table.Orden.withTable()
            eq CampaniaUaEntity.ORDEN_CAMPANIA_ACTUAL))

            leftOuterJoin HabilidadesAsignadasRDDEntity::class
            on ((ZonaEntity_Table.Codigo.withTable()
            eq HabilidadesAsignadasRDDEntity_Table.Zona.withTable())
            and (HabilidadesAsignadasRDDEntity_Table.Seccion.withTable()
            eq  Constant.HYPHEN)
            and (HabilidadesAsignadasRDDEntity_Table.Campania.withTable()
            eq CampaniaUaEntity_Table.Codigo.withTable())
            and (CampaniaUaEntity_Table.Orden.withTable()
            eq CampaniaUaEntity.ORDEN_CAMPANIA_ACTUAL))

            orderBy (ZonaEntity_Table.Codigo.withTable().asc()))

        return where.queryCustomList(ZonaFocosJoined::class.java)
    }
}
