package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.ventas.view

import biz.belcorp.salesforce.core.domain.exceptions.UnsupportedRoleException
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.ConsultoraRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.SociaEmpresariaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.profile.ProfileInfo
import java.util.Date

class BrightPathCabeceraMapper() {

    private var nombres: String? = null
    private var iniciales: String? = null
    private var birthdate: Date? = null
    private var telefono: String? = null
    private var telefonoConPrefijo: String? = null
    private var email: String? = null
    private var digitalSegment: String? = null

    fun parse(info: ProfileInfo, flagPdvPais: Boolean = false) = this
        .parsearDatosBasicos(info)
        .crearPorTipo(info, flagPdvPais)

    private fun parsearDatosBasicos(info: ProfileInfo): BrightPathCabeceraMapper {
        this.nombres = info.contact.fullname
        this.iniciales = info.contact.iniciales
        this.birthdate = info.contact.birthdate
        this.telefono = info.contact.phoneNumber
        this.telefonoConPrefijo = info.contact.phoneNumberWithPrefix
        this.email = info.contact.email
        this.digitalSegment = info.contact.digitalSegment
        return this
    }

    private fun crearPorTipo(persona: ProfileInfo, flagPdvPais: Boolean): BrightPathCabeceraModel {
        return when (persona) {
            is ProfileInfo.DatoPersonaPc ->
                BrightPathCabeceraModel.PosibleConsultora(
                    nombres = nombres,
                    iniciales = iniciales,
                    telefono = telefono,
                    telefonoConPrefijo = telefonoConPrefijo,
                    email = email,
                    fechaNacimiento = birthdate,
                    digitalSegment = digitalSegment
                )

            is ProfileInfo.DatoPersonaCo ->
                when (flagPdvPais) {
                    true ->
                        BrightPathCabeceraModel.ConsultoraConNivel(
                            segmento = persona.segmentoInterno,
                            nombres = nombres,
                            iniciales = iniciales,
                            telefono = telefono,
                            telefonoConPrefijo = telefonoConPrefijo,
                            email = email,
                            fechaNacimiento = birthdate,
                            digitalSegment = digitalSegment
                        )

                    else ->
                        BrightPathCabeceraModel.Consultora(
                            segmento = persona.segmentoInterno,
                            tipo = persona.tipo,
                            nombres = nombres,
                            iniciales = iniciales,
                            telefono = telefono,
                            telefonoConPrefijo = telefonoConPrefijo,
                            email = email,
                            fechaAniversario = persona.contact.anniversaryDate,
                            fechaNacimiento = birthdate,
                            digitalSegment = persona.contact.digitalSegment
                        )
                }

            is ProfileInfo.DatoPersonaSe ->
                BrightPathCabeceraModel.SociaEmpresaria(
                    exitosa = persona.exitosa,
                    nivelProductividad = persona.levelProductivity,
                    nombres = nombres,
                    iniciales = iniciales,
                    telefono = telefono,
                    telefonoConPrefijo = telefonoConPrefijo,
                    email = email,
                    nivel = persona.level,
                    fechaNacimiento = birthdate,
                    fechaAniversario = persona.contact.anniversaryDate,
                    digitalSegment = persona.contact.digitalSegment
                )

            is ProfileInfo.DatoPersonaGz ->
                BrightPathCabeceraModel.GerenteZona(
                    email = email,
                    iniciales = iniciales,
                    nombres = persona.contact.shortname,
                    telefono = telefono,
                    telefonoConPrefijo = telefonoConPrefijo,
                    fechaNacimiento = birthdate,
                    estadoProductividad = persona.estado,
                    esNueva = persona.esNueva,
                    textoNueva = "$LABEL_NEW ${persona.nroCampaniasComoNueva}",
                    digitalSegment = persona.contact.digitalSegment
                )

            is ProfileInfo.DatoPersonaGr ->
                BrightPathCabeceraModel.GerenteRegion(
                    email = email,
                    iniciales = iniciales,
                    nombres = persona.contact.shortname,
                    telefono = telefono,
                    telefonoConPrefijo = telefonoConPrefijo,
                    fechaNacimiento = birthdate,
                    estadoProductividad = persona.estado,
                    digitalSegment = persona.contact.digitalSegment
                )

            else -> throw UnsupportedRoleException(Rol.NINGUNO)
        }
    }

    private val ProfileInfo.DatoPersonaCo.tipo
        get() = when {
            segmentoInterno.isBlank() -> ConsultoraRdd.Tipo.NINGUNA
            segmentoInterno.contains(ConsultoraRdd.TIPO_NUEVA, true) -> ConsultoraRdd.Tipo.NUEVA
            segmentoInterno.contains(ConsultoraRdd.TIPO_C3, true) -> ConsultoraRdd.Tipo.C3
            else -> ConsultoraRdd.Tipo.ESTABLECIDA
        }

    private val ProfileInfo.DatoPersonaSe.level
        get() = when {
            nivel.trim().startsWith(
                SociaEmpresariaRdd.Nivel.BRONCE.value,
                true
            ) -> SociaEmpresariaRdd.Nivel.BRONCE

            nivel.trim().startsWith(
                SociaEmpresariaRdd.Nivel.PRE_BRONCE.value,
                true
            ) -> SociaEmpresariaRdd.Nivel.PRE_BRONCE

            nivel.trim().startsWith(
                SociaEmpresariaRdd.Nivel.ORO.value,
                true
            ) -> SociaEmpresariaRdd.Nivel.ORO

            nivel.trim().startsWith(
                SociaEmpresariaRdd.Nivel.PLATA.value,
                true
            ) -> SociaEmpresariaRdd.Nivel.PLATA

            nivel.trim().startsWith(
                SociaEmpresariaRdd.Nivel.PLATINUM.name,
                true
            ) -> SociaEmpresariaRdd.Nivel.PLATINUM

            nivel.trim().startsWith(
                SociaEmpresariaRdd.Nivel.DIAMANTE.value,
                true
            ) -> SociaEmpresariaRdd.Nivel.DIAMANTE

            else -> SociaEmpresariaRdd.Nivel.NINGUNA
        }

    private val ProfileInfo.DatoPersonaSe.levelProductivity
        get() = "${nivel.trim()} - ${productividad.trim()}"

    companion object {

        private const val LABEL_NEW = "Nueva"

    }

}
