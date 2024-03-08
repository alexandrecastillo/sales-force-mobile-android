package biz.belcorp.salesforce.modules.developmentpath.features.profile.puntajepremio

interface PuntajePremioView {

    fun pintarParaPuntajeAlcanzado(modelo: PuntajePremioModel)

    fun pintarParaPuntajeNoAlcanzado(modelo: PuntajePremioModel)

    fun pintarError()

    fun pintarCampania(campania: String)
}
