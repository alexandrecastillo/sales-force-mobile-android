package biz.belcorp.ffvv.presentation.feature.rdd.perfil.acompaniamiento.informacionhistorica.contenedor.view

import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.contenedor.model.GraficoResumenModel

interface InformacionHistoricaRegionView {

    fun cargarGraficos(graficos: List<GraficoResumenModel>)
}
