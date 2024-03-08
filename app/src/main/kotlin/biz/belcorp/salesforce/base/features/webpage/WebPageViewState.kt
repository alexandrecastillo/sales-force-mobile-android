package biz.belcorp.salesforce.base.features.webpage

sealed class WebPageViewState {

    class LoadWebPage(val url: String) : WebPageViewState()
    object Failed : WebPageViewState()

}
