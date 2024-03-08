package biz.belcorp.salesforce.base.features.home.shortcuts

sealed class ShortcutsViewState {
    class Success(val data: List<ShortcutModel>) : ShortcutsViewState()
    class LoadExternalWebPage(val url: String) : ShortcutsViewState()
}
