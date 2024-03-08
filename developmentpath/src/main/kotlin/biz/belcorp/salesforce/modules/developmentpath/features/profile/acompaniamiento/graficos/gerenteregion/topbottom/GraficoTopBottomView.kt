package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.graficos.gerenteregion.topbottom

interface GraficoTopBottomView {
    fun pintarTops(tops: List<TopBottomModel>, mostrarEnDosLineas: Boolean)
    fun pintarBottoms(bottoms: List<TopBottomModel>, mostrarEnDosLineas: Boolean)
    fun pintarCampaniaEnTitulo(campania: String)
    fun pintarCampaniaEnTituloConComparativa(campania: String)
}
