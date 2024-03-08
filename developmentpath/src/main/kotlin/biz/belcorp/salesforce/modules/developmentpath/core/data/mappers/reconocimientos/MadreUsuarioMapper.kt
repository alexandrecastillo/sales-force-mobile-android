package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.reconocimientos

import biz.belcorp.salesforce.core.entities.sql.directorio.UsuarioPadreSesionEntity
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.UsuarioMadre

class MadreUsuarioMapper {

    fun parse(entidad: UsuarioPadreSesionEntity): UsuarioMadre {
        return UsuarioMadre(
            nombre = entidad.nombre ?: Constant.EMPTY_STRING,
            rol = Rol.Builder.construir(entidad.rol),
            codigoUA = entidad.codigo ?: Constant.EMPTY_STRING)
    }
}
