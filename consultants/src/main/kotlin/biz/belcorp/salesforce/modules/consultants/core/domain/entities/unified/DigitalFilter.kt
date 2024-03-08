package biz.belcorp.salesforce.modules.consultants.core.domain.entities.unified

class DigitalFilter(
    val isSubscribed: Boolean = false,
    val isNotSubscribed: Boolean = false,
    val buy: Boolean = false,
    val noBuy: Boolean = false,
    val share: Boolean = false,
    val noShare : Boolean = false
) : Filterable
