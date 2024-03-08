package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model

import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.menu.Visibilidad
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model.mapa.PersonaEnMapaModel
import java.io.Serializable
import java.util.*

class MenuPersonaModel(val personaId: Long,
                       val personCode: String,
                       val rol: Rol,
                       val nombreConsultora: String,
                       val numeroTelefono: String?,
                       val email: String?,
                       val enMapaModel: PersonaEnMapaModel,
                       val mostrarContactaA: Visibilidad,
                       val mostrarWhatsapp: Visibilidad,
                       val mostrarSms: Visibilidad,
                       val mostrarEmail: Visibilidad,
                       val mostrarTelefono: Visibilidad,
                       val mostrarUbicar: Visibilidad,
                       val mostrarAdicionarVisita: Visibilidad,
                       val mostrarReplanificar: Visibilidad,
                       val mostrarRegistrar: Visibilidad,
                       val mostrarEliminar: Visibilidad,
                       val fecha: Date?,
                       val visitaId: Long) : Serializable
