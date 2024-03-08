package biz.belcorp.salesforce.modules.postulants.features.indicador.components

interface LoadingView {
    fun showLoading()
    fun hideLoading()
    fun onHeaderClick(modo: Int)
}
