package biz.belcorp.salesforce.core.domain.entities.configuration

data class Configuration(
    val country: String,
    val countryName: String,
    val currencySymbol: String,
    val phoneCode: String,
    val minimalOrderAmount: Long,
    val tippingPoint: Long,
    val isPdv: Boolean,
    @MainBrandType val mainBrand: String,
    val isGanaMas: Boolean,
    val isOnlineStoreEnabled: Boolean,
    val onlineStoreTitle: String,
    @OnlineStoreType val onlineStoreType: String,
    val updateType: String,
    val flagMto: Int,
    val flagShowProactive: Boolean
)
