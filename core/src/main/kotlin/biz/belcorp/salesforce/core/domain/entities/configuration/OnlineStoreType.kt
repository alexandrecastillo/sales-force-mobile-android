package biz.belcorp.salesforce.core.domain.entities.configuration

import androidx.annotation.StringDef

@Retention(AnnotationRetention.RUNTIME)
@StringDef(
    OnlineStoreType.ONLINE_STORE,
    OnlineStoreType.DIGITAL_CATALOG
)
annotation class OnlineStoreType {
    companion object {
        const val ONLINE_STORE = "ONLINE_STORE"
        const val DIGITAL_CATALOG = "DIGITAL_CATALOG"
    }
}
