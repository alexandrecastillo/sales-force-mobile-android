package biz.belcorp.salesforce.modules.developmentpath.features.profile.old.cabecera

import biz.belcorp.salesforce.core.utils.WordUtils
import biz.belcorp.salesforce.core.utils.parseToDateItem
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.GerenteRegionRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.GerenteZonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.Persona
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PosibleConsultoraRdd

class CabeceraPerfilMapper {

    private var nombres: String? = null
    private var iniciales: String? = null
    private var cumpleanios: String? = null
    private var telefono: String? = null
    private var telefonoConPrefijo: String? = null
    private var email: String? = null

    fun parse(persona: Persona) = this
        .parsearDatosBasicos(persona)
        .crearPorTipo(persona)

    private fun parsearDatosBasicos(persona: Persona): CabeceraPerfilMapper {
        this.nombres =
            WordUtils.capitalizeFully(
                "${persona.primerNombre} " +
                    "${persona.primerApellido} " +
                    "${persona.segundoApellido}"
            )
        this.iniciales = persona.iniciales
        this.cumpleanios = persona.cumpleanios
        this.telefono = persona.directorio?.obtenerFavorito()?.numero
        this.telefonoConPrefijo = persona.directorio?.obtenerFavoritoConPrefijo()?.numero
        this.email = persona.email

        return this
    }

    private fun crearPorTipo(persona: Persona): CabeceraPerfilModel {
        return when (persona) {
            is PosibleConsultoraRdd -> mapPosibleConsultora()
            is GerenteZonaRdd -> mapGerenteZona(persona)
            is GerenteRegionRdd -> mapGerenteRegion(persona)
            else -> throw Exception("Rol no soportado en cabecera")
        }
    }

    private fun mapPosibleConsultora() = CabeceraPerfilModel.PosibleConsultora(
        nombres,
        iniciales,
        cumpleanios,
        telefono,
        telefonoConPrefijo,
        email
    )

    private fun mapGerenteZona(persona: GerenteZonaRdd) = CabeceraPerfilModel.GerenteZona(
        cumpleanios = persona.fechaNacimiento?.parseToDateItem(),
        email = email,
        iniciales = iniciales,
        nombres = WordUtils.capitalizeFully(persona.nombreApellido),
        telefono = telefono,
        telefonoConPrefijo = telefonoConPrefijo,
        estadoProductividad = persona.estadoProductividad,
        esNueva = persona.esNueva,
        textoNueva = persona.textoNueva
    )

    private fun mapGerenteRegion(persona: GerenteRegionRdd) = CabeceraPerfilModel.GerenteRegion(
        cumpleanios = persona.fechaNacimiento?.parseToDateItem(),
        email = email,
        iniciales = iniciales,
        nombres = WordUtils.capitalizeFully(persona.nombreApellido),
        telefono = telefono,
        telefonoConPrefijo = telefonoConPrefijo,
        estadoProductividad = persona.estadoProductividad
    )

}
