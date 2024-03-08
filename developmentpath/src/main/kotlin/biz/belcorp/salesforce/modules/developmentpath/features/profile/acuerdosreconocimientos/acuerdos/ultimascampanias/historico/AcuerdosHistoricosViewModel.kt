package biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.acuerdos.ultimascampanias.historico

data class AcuerdosHistoricosViewModel(
    var acuerdosNoCumplidos: List<AcuerdosPorCampaniaModel> = emptyList(),
    var acuerdosCumplidos: List<AcuerdosPorCampaniaModel> = emptyList()
) {

    val mostrarSinAcuerdosHistoricos
        get() = acuerdosCumplidos.isEmpty() && acuerdosNoCumplidos.isEmpty()

    val mostrarSinAcuerdosCumplidos
        get() = acuerdosCumplidos.isEmpty() && acuerdosNoCumplidos.isNotEmpty()

    val mostrarSinAcuerdosNoCumplidos
        get() = acuerdosNoCumplidos.isEmpty() && acuerdosCumplidos.isNotEmpty()
}
