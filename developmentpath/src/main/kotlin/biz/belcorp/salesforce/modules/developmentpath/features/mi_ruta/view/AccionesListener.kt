package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view

import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model.MenuPersonaModel
import java.util.*

interface AccionesListener {

    fun alPresionarEliminacion(visitaId: Long,
                               fechaAnterior: Date,
                               nombre: String)

    fun alPresionarRegistro(personIdentifier: PersonIdentifier)

    fun alPresionarReplanificacion(visitaId: Long, fechaAnterior: Date)

    fun alPresionarAdicionarVisita(personaId: Long, rol: Rol)

    fun alLlamar(numero: String)

    fun alEnviarSMS(numero: String)

    fun alEnviarWhatsApp(numero: String)

    fun alEviarEmail(email: String)

    fun alPresionarUbicacion(modelo: MenuPersonaModel)
}
