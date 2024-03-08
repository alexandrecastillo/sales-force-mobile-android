package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.header

import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.section.TipDesarrolloModel

sealed class DetalleTipsDesarrolloViewState {

    class ShowTips(val tips: List<TipDesarrolloModel>) : DetalleTipsDesarrolloViewState()

}
