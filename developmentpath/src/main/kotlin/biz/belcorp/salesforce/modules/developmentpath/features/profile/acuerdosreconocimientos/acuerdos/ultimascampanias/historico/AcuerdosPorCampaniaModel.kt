package biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.acuerdos.ultimascampanias.historico

data class AcuerdosPorCampaniaModel(
    val codigoCampania: String,
    val numeroCampania: String,
    val acuerdos: List<AcuerdoModel>
)
