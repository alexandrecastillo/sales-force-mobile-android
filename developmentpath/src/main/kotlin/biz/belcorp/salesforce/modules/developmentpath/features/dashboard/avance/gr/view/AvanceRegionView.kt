package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.gr.view

import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.gr.model.AvanceZonaModel

interface AvanceRegionView {

    fun pintarAvanceGerentesZona(avancesZona: List<AvanceZonaModel>)

    fun mostrarToast(mensaje: String)

    fun mostrarReconocimientos()

    fun ocultarReconocimientos()
}
