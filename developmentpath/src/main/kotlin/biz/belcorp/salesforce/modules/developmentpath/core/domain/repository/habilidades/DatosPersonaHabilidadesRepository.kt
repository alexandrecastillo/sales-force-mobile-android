package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.habilidades

import biz.belcorp.salesforce.core.entities.zonificacion.Rol

interface DatosPersonaHabilidadesRepository {

    fun obtener(personaId: Long, rol: Rol): DatosPersonaHabilidades

    class DatosPersonaHabilidades(
        val nombre: String,
        val apellido: String,
        val rol: Rol,
        val estado: String,
        val usuario: String,
        val codigoRegion: String,
        val codigoZona: String
    )
}
