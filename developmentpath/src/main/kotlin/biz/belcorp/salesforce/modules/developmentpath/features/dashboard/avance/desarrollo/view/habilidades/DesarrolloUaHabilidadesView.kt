package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.desarrollo.view.habilidades

import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.gr.model.DesarrolloHabilidadModel

interface DesarrolloUaHabilidadesView {
    fun showCurrentCampaign(periodoCampania: String)
    fun showTitle(uaCampaniaAnterior: String)
    fun showDesarrolloRegion(list: List<DesarrolloHabilidadModel>)
}
