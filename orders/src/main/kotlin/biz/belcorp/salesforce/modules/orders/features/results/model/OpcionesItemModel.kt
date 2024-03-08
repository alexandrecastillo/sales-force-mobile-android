package biz.belcorp.salesforce.modules.orders.features.results.model


import androidx.annotation.StringRes


class OpcionesItemModel {

    @StringRes
    var nameResId: Int = -1
    var icon: Int = 0
    var action: String? = null
    var position: String? = null

    companion object {

        const val CALL = "call"
        const val PROFILE = "profile"
        const val LOCK = "lock"
        const val UNLOCK = "unlock"
        const val CLOSE = "close"

    }

}
