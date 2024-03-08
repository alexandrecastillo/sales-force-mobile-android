package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.section

sealed class TipsDesarrolloViewState {

    class ShowTips(val tips: List<TipDesarrolloModel>) : TipsDesarrolloViewState()

    object ShowEmptyView : TipsDesarrolloViewState()

}
