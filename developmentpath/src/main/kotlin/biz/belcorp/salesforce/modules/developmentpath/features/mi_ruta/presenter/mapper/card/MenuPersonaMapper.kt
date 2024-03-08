package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.presenter.mapper.card

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.utils.WordUtils
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PersonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas.Visita
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model.MenuPersonaModel
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.presenter.mapper.PersonaEnMapaMapper

class MenuPersonaMapper(private val personaEnMapaMapper: PersonaEnMapaMapper) {

    fun map(visita: Visita): MenuPersonaModel {
        return MenuPersonaModel(
            personaId = visita.persona.id,
            personCode = visita.persona.personCode,
            rol = visita.persona.rol,
            nombreConsultora = WordUtils.capitalizeFully(visita.persona.primerNombre.orEmpty()),
            numeroTelefono = visita.persona.directorio?.obtenerFavorito()?.numero,
            enMapaModel = personaEnMapaMapper.map(visita.persona),
            mostrarContactaA = visita.visibilidadMenuHandler.contactaA,
            mostrarWhatsapp = visita.visibilidadMenuHandler.whatsapp,
            mostrarSms = visita.visibilidadMenuHandler.sms,
            email = visita.persona.email,
            mostrarEmail = visita.visibilidadMenuHandler.email,
            mostrarTelefono = visita.visibilidadMenuHandler.telefono,
            mostrarUbicar = visita.visibilidadMenuHandler.ubicar,
            mostrarAdicionarVisita = visita.visibilidadMenuHandler.adicionarVisita,
            mostrarReplanificar = visita.visibilidadMenuHandler.replanificar,
            mostrarRegistrar = visita.visibilidadMenuHandler.registrar,
            mostrarEliminar = visita.visibilidadMenuHandler.eliminar,
            fecha = visita.horaAMostrar,
            visitaId = visita.id
        )
    }

    fun map(persona: PersonaRdd): MenuPersonaModel {
        return MenuPersonaModel(
            personaId = persona.id,
            personCode = persona.personCode,
            rol = persona.rol,
            nombreConsultora = WordUtils.capitalizeFully(persona.primerNombre.orEmpty()),
            numeroTelefono = persona.directorio?.obtenerFavorito()?.numero,
            enMapaModel = personaEnMapaMapper.map(persona),
            mostrarContactaA = persona.visibilidadMenuHandler.contactaA,
            mostrarWhatsapp = persona.visibilidadMenuHandler.whatsapp,
            mostrarSms = persona.visibilidadMenuHandler.sms,
            email = persona.email,
            mostrarEmail = persona.visibilidadMenuHandler.email,
            mostrarTelefono = persona.visibilidadMenuHandler.telefono,
            mostrarUbicar = persona.visibilidadMenuHandler.ubicar,
            mostrarAdicionarVisita = persona.visibilidadMenuHandler.adicionarVisita,
            mostrarReplanificar = persona.visibilidadMenuHandler.replanificar,
            mostrarRegistrar = persona.visibilidadMenuHandler.registrar,
            mostrarEliminar = persona.visibilidadMenuHandler.eliminar,
            fecha = persona.primeraVisitaNoRegistrada?.horaAMostrar,
            visitaId = Constant.ONE_NEGATIVE.toLong()
        )
    }
}
