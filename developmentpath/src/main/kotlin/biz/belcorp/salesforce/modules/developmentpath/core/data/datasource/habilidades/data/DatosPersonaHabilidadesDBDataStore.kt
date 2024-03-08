package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.habilidades.data


import biz.belcorp.salesforce.core.domain.exceptions.UnsupportedRoleException
import biz.belcorp.salesforce.core.entities.sql.directorio.DirectorioVentaUsuarioEntity
import biz.belcorp.salesforce.core.entities.sql.directorio.DirectorioVentaUsuarioEntity_Table
import biz.belcorp.salesforce.core.entities.sql.habilidades.RegionDirectorioVentasUsuarioJoined
import biz.belcorp.salesforce.core.entities.sql.habilidades.ZonaDirectorioVentasUsuarioJoinned
import biz.belcorp.salesforce.core.entities.sql.ua.RegionEntity
import biz.belcorp.salesforce.core.entities.sql.ua.RegionEntity_Table
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.habilidades.rol.GerenteRegionMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.habilidades.rol.GerenteZonaMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.habilidades.DatosPersonaHabilidadesRepository
import com.raizlabs.android.dbflow.kotlinextensions.*
import com.raizlabs.android.dbflow.sql.language.Select

class DatosPersonaHabilidadesDBDataStore(
    private val gerenteZonaMapper: GerenteZonaMapper,
    private val gerenteRegionMapper: GerenteRegionMapper
) :
    DatosPersonaHabilidadesRepository {

    override fun obtener(personaId: Long, rol: Rol):
        DatosPersonaHabilidadesRepository.DatosPersonaHabilidades {
        return when (rol) {
            Rol.GERENTE_ZONA -> obtenerGz(personaId)
            Rol.GERENTE_REGION -> obtenerGr(personaId)
            else -> throw UnsupportedRoleException(rol)
        }
    }

    private fun obtenerGz(personaId: Long):
        DatosPersonaHabilidadesRepository.DatosPersonaHabilidades {
        val where = (Select(
            DirectorioVentaUsuarioEntity_Table.CodZona.withTable(),
            DirectorioVentaUsuarioEntity_Table.CodRegion.withTable(),
            DirectorioVentaUsuarioEntity_Table.PrimerNombre.withTable(),
            DirectorioVentaUsuarioEntity_Table.PrimerApellido.withTable(),
            DirectorioVentaUsuarioEntity_Table.Usuario.withTable(),
            DirectorioVentaUsuarioEntity_Table.Estado.withTable()
        )
            from DirectorioVentaUsuarioEntity::class
            where (DirectorioVentaUsuarioEntity_Table.consultoraID.withTable() eq personaId)
            and (DirectorioVentaUsuarioEntity_Table.CodRol.withTable() eq Rol.GERENTE_ZONA.codigoRol))

        val join = where.queryCustomSingle(ZonaDirectorioVentasUsuarioJoinned::class.java)

        return gerenteZonaMapper.parsearGerenteZona(checkNotNull(join))
    }

    private fun obtenerGr(personaId: Long):
        DatosPersonaHabilidadesRepository.DatosPersonaHabilidades {
        val where = (Select(
            DirectorioVentaUsuarioEntity_Table.CodRegion.withTable(),
            DirectorioVentaUsuarioEntity_Table.PrimerNombre.withTable(),
            DirectorioVentaUsuarioEntity_Table.PrimerApellido.withTable(),
            DirectorioVentaUsuarioEntity_Table.Usuario.withTable(),
            DirectorioVentaUsuarioEntity_Table.Estado.withTable(),
            DirectorioVentaUsuarioEntity_Table.CodRol.withTable()
        )
            from DirectorioVentaUsuarioEntity::class
            innerJoin RegionEntity::class
            on (DirectorioVentaUsuarioEntity_Table.CodRegion.withTable()
            eq RegionEntity_Table.Codigo.withTable())
            where (DirectorioVentaUsuarioEntity_Table.consultoraID.withTable() eq personaId)
            and (DirectorioVentaUsuarioEntity_Table.CodRol.withTable() eq Rol.GERENTE_REGION.codigoRol))

        val join = where.queryCustomSingle(RegionDirectorioVentasUsuarioJoined::class.java)

        return gerenteRegionMapper.parsearGerenteRegion(checkNotNull(join))
    }
}
