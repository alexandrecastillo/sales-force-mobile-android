package biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.reconocer

data class GuardarReconocimientoModel(
    var reconocimientos: List<ReconocimientoModel> = emptyList(),
    var seleccionados: Int = 0,
    var total: Int = 0
)
