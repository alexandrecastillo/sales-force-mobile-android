package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.habilidades.rol

import biz.belcorp.salesforce.core.entities.sql.habilidades.ZonaDirectorioVentasUsuarioJoinned
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.habilidades.DatosPersonaHabilidadesRepository

class GerenteZonaMapper {

    fun parsearGerenteZona(join: ZonaDirectorioVentasUsuarioJoinned): DatosPersonaHabilidadesRepository.DatosPersonaHabilidades {
        return DatosPersonaHabilidadesRepository.DatosPersonaHabilidades(
            nombre = join.primerNombre.orEmpty(),
            apellido = join.primerApellido.orEmpty(),
            rol = Rol.Builder.construir(join.codRol),
            estado = join.estado.orEmpty(),
            codigoZona = join.codZona.orEmpty(),
            codigoRegion = join.codRegion ?: throw Exception("Codigo de Region no asignado"),
            usuario = join.usuario.orEmpty()
        )
    }
}
