package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.personas


import biz.belcorp.salesforce.core.entities.sql.campania.CampaniaUaEntity
import biz.belcorp.salesforce.core.entities.sql.campania.CampaniaUaEntity_Table
import biz.belcorp.salesforce.core.entities.sql.directorio.DirectorioVentaUsuarioEntity
import biz.belcorp.salesforce.core.entities.sql.directorio.DirectorioVentaUsuarioEntity_Table
import biz.belcorp.salesforce.core.entities.sql.personas.CabeceraGZJoinned
import biz.belcorp.salesforce.core.entities.sql.plan.DetallePlanRutaRDDEntity
import biz.belcorp.salesforce.core.entities.sql.plan.DetallePlanRutaRDDEntity_Table
import biz.belcorp.salesforce.core.entities.sql.ua.ZonaEntity
import biz.belcorp.salesforce.core.entities.sql.ua.ZonaEntity_Table
import biz.belcorp.salesforce.core.entities.sql.visitas.GzDetalleRutaJoinned
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.personas.GerenteZonaMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.GerenteZonaRdd
import com.raizlabs.android.dbflow.kotlinextensions.*
import com.raizlabs.android.dbflow.sql.language.Select

class GerenteZonaDBDataStore(private val gerenteZonaMapper: GerenteZonaMapper) :
    GerenteZonaDataStore {

    override fun obtenerPlanificables(planId: Long) =
        gerenteZonaMapper.parse(recuperarDetallesGZ(planId))

    private fun recuperarDetallesGZ(planId: Long): List<GzDetalleRutaJoinned> {
        val query = (select
            from DetallePlanRutaRDDEntity::class
            innerJoin DirectorioVentaUsuarioEntity::class
            on (DetallePlanRutaRDDEntity_Table.CodigoConsultora.withTable()
            eq DirectorioVentaUsuarioEntity_Table.Usuario.withTable())
            innerJoin ZonaEntity::class
            on ((DirectorioVentaUsuarioEntity_Table.CodRegion.withTable()
            eq ZonaEntity_Table.Region.withTable())
            and (DirectorioVentaUsuarioEntity_Table.CodZona.withTable()
            eq ZonaEntity_Table.Codigo.withTable())
            and (DetallePlanRutaRDDEntity_Table.Rol.withTable()
            eq Rol.GERENTE_ZONA.codigoRol))
            where (DetallePlanRutaRDDEntity_Table.PlanVisitaID.withTable() eq planId))

        return query.queryCustomList(GzDetalleRutaJoinned::class.java)
    }

    override fun recuperarPorId(personaId: Long): GerenteZonaRdd? {
        val where = (Select(
            DirectorioVentaUsuarioEntity_Table.consultoraID.withTable(),
            DirectorioVentaUsuarioEntity_Table.PrimerNombre.withTable(),
            DirectorioVentaUsuarioEntity_Table.PrimerApellido.withTable(),
            DirectorioVentaUsuarioEntity_Table.SegundoApellido.withTable(),
            DirectorioVentaUsuarioEntity_Table.TelefMovil.withTable(),
            DirectorioVentaUsuarioEntity_Table.Estado.withTable(),
            DirectorioVentaUsuarioEntity_Table.MailBelcorp.withTable(),
            DirectorioVentaUsuarioEntity_Table.FechaNacimiento.withTable(),
            DirectorioVentaUsuarioEntity_Table.CodRegion.withTable(),
            DirectorioVentaUsuarioEntity_Table.CodZona.withTable(),
            DirectorioVentaUsuarioEntity_Table.Usuario.withTable(),
            DirectorioVentaUsuarioEntity_Table.NroDoc.withTable(),
            DirectorioVentaUsuarioEntity_Table.EsNueva.withTable(),
            DirectorioVentaUsuarioEntity_Table.NroCampaniasComoNueva.withTable(),
            CampaniaUaEntity_Table.Codigo.withTable().`as`("CodigoCampania"),
            CampaniaUaEntity_Table.FechaInicio.withTable(),
            CampaniaUaEntity_Table.FechaFin.withTable(),
            CampaniaUaEntity_Table.FechaInicioFacturacion.withTable(),
            CampaniaUaEntity_Table.NombreCorto.withTable(),
            CampaniaUaEntity_Table.Orden.withTable(),
            CampaniaUaEntity_Table.Periodo.withTable(),
            CampaniaUaEntity_Table.PrimerDiaFacturacion.withTable()
        )

            from DirectorioVentaUsuarioEntity::class
            innerJoin CampaniaUaEntity::class
            on (DirectorioVentaUsuarioEntity_Table.CodRegion.withTable()
            eq CampaniaUaEntity_Table.Region.withTable()
            and (DirectorioVentaUsuarioEntity_Table.CodZona.withTable()
            eq CampaniaUaEntity_Table.Zona.withTable()))

            where (DirectorioVentaUsuarioEntity_Table.consultoraID.withTable() eq personaId)
            and (CampaniaUaEntity_Table.Seccion.withTable() eq GerenteRegionDBDataStore.UA_VACIA)
            and (CampaniaUaEntity_Table.Orden.withTable() eq 1))

        val modelo = where.queryCustomSingle(CabeceraGZJoinned::class.java) ?: return null

        if (modelo.usuario == null) return null

        return gerenteZonaMapper.parsear(modelo)
    }

    override fun recuperarIdSegunUsuario(usuario: String): Long? {
        val query = (select
            from DirectorioVentaUsuarioEntity::class
            where (DirectorioVentaUsuarioEntity_Table.Usuario eq usuario)).querySingle()

        return query?.consultoraId
    }
}
