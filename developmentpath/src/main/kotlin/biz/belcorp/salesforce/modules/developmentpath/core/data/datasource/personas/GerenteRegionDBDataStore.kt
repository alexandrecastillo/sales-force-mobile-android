package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.personas

import biz.belcorp.salesforce.core.entities.sql.campania.CampaniaUaEntity
import biz.belcorp.salesforce.core.entities.sql.campania.CampaniaUaEntity_Table
import biz.belcorp.salesforce.core.entities.sql.directorio.DirectorioVentaUsuarioEntity
import biz.belcorp.salesforce.core.entities.sql.directorio.DirectorioVentaUsuarioEntity_Table
import biz.belcorp.salesforce.core.entities.sql.directorio.GerenteRegionJoinned
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.visitas.VisitaRddDBDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.personas.GerenteRegionMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.GerenteRegionRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas.AgendaVisitas
import com.raizlabs.android.dbflow.kotlinextensions.*
import com.raizlabs.android.dbflow.sql.language.Select

class GerenteRegionDBDataStore(private val mapper: GerenteRegionMapper,
                               private val visitaDataStore: VisitaRddDBDataStore
) {

    fun recuperarPersonaPorId(personaId: Long): GerenteRegionRdd? {
        val gr = recuperarGrDesdeBd(personaId) ?: return null
        val agenda = recuperarAgendaDesdeBd(gr.usuario)

        gr.establecerAgenda(agenda)
        agenda.establecerPersona(gr)

        return gr
    }

    private fun recuperarGrDesdeBd(personaId: Long): GerenteRegionRdd? {
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
            CampaniaUaEntity_Table.Codigo.withTable(),
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
            eq CampaniaUaEntity_Table.Region.withTable())
            where (DirectorioVentaUsuarioEntity_Table.consultoraID.withTable() eq personaId)
            and (DirectorioVentaUsuarioEntity_Table.CodRol.withTable() eq Rol.GERENTE_REGION.codigoRol)
            and (CampaniaUaEntity_Table.Zona.withTable() eq UA_VACIA)
            and (CampaniaUaEntity_Table.Seccion.withTable() eq UA_VACIA)
            and (CampaniaUaEntity_Table.Orden.withTable() eq 1))

        val modelo = where.queryCustomSingle(GerenteRegionJoinned::class.java) ?: return null

        if (modelo.usuario == null) return null

        return mapper.parsear(modelo)
    }

    private fun recuperarAgendaDesdeBd(usuario: String): AgendaVisitas {
        return visitaDataStore.obtenerVisitasDeGR(usuario)
    }

    companion object {
        const val UA_VACIA = "-"
    }
}
