package biz.belcorp.salesforce.modules.developmentpath.features.profile.information.mapper

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.profile.ProfileInfo

class DatosPersonaFactory {
    fun crearMapper(datosPersona: ProfileInfo): DatosPersonaMapper {
        return when (datosPersona) {
            is ProfileInfo.DatoPersonaCo -> DatosConsultoraMapper(datosPersona)
            is ProfileInfo.DatoPersonaSe -> DatosSociaEmpresariaMapper(datosPersona)
            else -> throw Exception("No soportado")
        }
    }
}
