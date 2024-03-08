package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.maps


import android.content.Context

/**
 * Interface representing a View that will use to load data.
 */
interface LoadDataView {
    /**
     * Show a view with a progress bar indicating a loading process.
     */
    fun showLoading() = Unit

    /**
     * Hide a loading view.
     */
    fun hideLoading() = Unit

    /**
     * Show a retry view in case of an error when retrieving data.
     */
    fun showRetry() = Unit

    /**
     * Hide a retry view shown if there was an error when retrieving data.
     */
    fun hideRetry() = Unit

    /**
     * Show an error message
     *
     * @param message A string representing an error.
     */
    fun showError(message: String) = Unit

    /**
     * Get a [android.content.Context].
     */
    fun context(): Context?
}
