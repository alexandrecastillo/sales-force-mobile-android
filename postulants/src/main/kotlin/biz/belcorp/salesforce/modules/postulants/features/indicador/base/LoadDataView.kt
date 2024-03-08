package biz.belcorp.salesforce.modules.postulants.features.indicador.base

import android.content.Context

interface LoadDataView {
    fun showLoading() = Unit

    fun hideLoading() = Unit

    fun showRetry() = Unit

    fun hideRetry() = Unit

    fun showError(message: String) = Unit

    fun context(): Context
}
