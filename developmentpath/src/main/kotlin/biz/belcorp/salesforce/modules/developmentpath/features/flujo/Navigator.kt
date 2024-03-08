package biz.belcorp.salesforce.modules.developmentpath.features.flujo

import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier

interface Navigator {
    fun irAMiRuta(planId: Long)
    fun irAListaResultadoVisitasFacturaron()
    fun irAListaResultadoVisitasNoFacturaron()
    fun irAPerfil(personIdentifier: PersonIdentifier)
    fun retroceder()
    fun retrocederARaiz()
}
