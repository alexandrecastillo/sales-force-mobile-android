package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view.mapa

import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model.ListaMarcadores

interface ListenerAccionesEnMapa {

    fun alCargarMapa()

    fun alBuscarPersonasCerca(marcadores: ListaMarcadores)
}
