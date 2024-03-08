package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view.no_planificadas

import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model.NoPlanificadasViewModel

interface NoPlanificadasView {

    fun cargar(noPlanificadasViewModel: NoPlanificadasViewModel)

    fun ocultarPostulantesProactivas()

    fun ocultarPostulantesNoProactivas()

    fun mostrarPostulantesProactivas()

    fun mostrarPostulantesNoProactivas()
}
