package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view.mapa

import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model.mapa.PersonaEnMapaModel

interface MapaView : MapaBaseView {

    fun refrescarMapa()

    fun mostrarPersonasCerca(personasCerca: List<PersonaEnMapaModel>)
}
