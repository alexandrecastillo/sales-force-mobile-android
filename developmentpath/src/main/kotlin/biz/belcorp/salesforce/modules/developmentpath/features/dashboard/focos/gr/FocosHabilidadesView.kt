package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos.gr

interface FocosHabilidadesView {
    fun cargar(modelos: List<FocosYHabilidadesPorUa>)
    fun updateItem(modelos: List<FocosYHabilidadesPorUa>, position: Int)
}
