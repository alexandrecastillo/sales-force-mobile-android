package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.menu

import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PersonaRdd

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas.Visita

class VisibilidadMenuHandler private constructor(val persona: PersonaRdd, val visita: Visita?) {

    companion object {
        fun porPersona(persona: PersonaRdd): VisibilidadMenuHandler {
            return VisibilidadMenuHandler(persona, null)
        }

        fun porVisita(visita: Visita): VisibilidadMenuHandler {
            return VisibilidadMenuHandler(visita.persona, visita)
        }
    }

    val contactaA
        get() = if (whatsapp == Visibilidad.INVISIBLE &&
            sms == Visibilidad.INVISIBLE &&
            telefono == Visibilidad.INVISIBLE
        ) {
            Visibilidad.INVISIBLE
        } else {
            Visibilidad.HABILITADO
        }


    val whatsapp get() = visibilidadPorTelefono()

    val sms get() = visibilidadDeSMS()

    val email get() = visibilidadDeEmail()

    val telefono get() = visibilidadPorTelefono()

    val ubicar
        get() = if (persona.ubicacion.poseeCoordenadas) Visibilidad.HABILITADO
        else Visibilidad.INVISIBLE

    val adicionarVisita
        get() = if (persona.puedeAdicionarVisita) {
            Visibilidad.HABILITADO
        } else {
            Visibilidad.INVISIBLE
        }

    val registrar
        get() = if (visita == null || visita.estaRegistrada) {
            Visibilidad.DESHABILITADO
        } else {
            Visibilidad.HABILITADO
        }

    val replanificar
        get() = if (visita == null || visita.estaRegistrada) {
            Visibilidad.DESHABILITADO
        } else {
            Visibilidad.HABILITADO
        }
    val eliminar
        get() = if (visita == null || visita.estaRegistrada) {
            Visibilidad.DESHABILITADO
        } else {
            Visibilidad.HABILITADO
        }

    private fun visibilidadPorTelefono() =
        if (persona.directorio?.obtenerFavorito()?.numero.isNullOrBlank()) Visibilidad.INVISIBLE
        else Visibilidad.HABILITADO

    private fun visibilidadDeSMS(): Visibilidad {
        return when (persona.rol) {
            Rol.GERENTE_ZONA -> {
                Visibilidad.INVISIBLE
            }
            else -> {
                visibilidadPorTelefono()
            }
        }
    }

    private fun visibilidadDeEmail(): Visibilidad {
        return when (persona.rol) {
            Rol.GERENTE_ZONA -> {
                if (persona.email.isNullOrBlank()) Visibilidad.INVISIBLE else Visibilidad.HABILITADO
            }
            else -> {
                Visibilidad.INVISIBLE
            }
        }
    }
}
