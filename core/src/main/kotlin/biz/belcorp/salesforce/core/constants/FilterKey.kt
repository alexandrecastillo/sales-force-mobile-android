package biz.belcorp.salesforce.core.constants

object FilterKey {
    const val KEY_ALL = "ALL"
    const val KEY_6D6 = "6D6"
    const val KEY_5D5 = "5D5"
    const val KEY_4D4 = "4D4"
    const val KEY_3D3 = "3D3"
    const val KEY_2D2 = "2D2"
    const val KEY_1D1 = "1D1"
    const val KEY_LOW_VALUE = "PBV"
    const val KEY_LOW_VALUE_PLUS = "PBVP"
    const val KEY_HIGH_VALUE = "PAV"
    const val KEY_HIGH_VALUE_PLUS = "PAVP"
    const val KEY_PEG_WITH_DEBT = "PDEBT"
    const val KEY_PEG_WITHOUT_DEBT = "PWDEBT"
    const val KEY_ORDERS_WITH_DEBT = "OWD"
    const val KEY_ORDERS_MINIMAL_AMOUNT = "OMA"
    const val KEY_ORDERS_NEAR_TP_HIGH_VALUE = "ONTPHV"
    const val KEY_PEGS = "PEGS"
    const val KEY_POSSIBLE_PEGS = "POSSIBLE_PEGS"
    const val KEY_NO_BILLED_ORDERS = "NO_BILLED_ORDERS"
    const val KEY_BILLED_ORDERS = "BILLED_ORDERS"
    const val KEY_ENTRIES = "ENTRIES"
    const val KEY_REENTRIES = "REENTRIES"
    const val KEY_DIGITAL_SUBSCRIBED = "SUBSCRIBED"
    const val KEY_DIGITAL_NO_SUBSCRIBED = "NO_SUBSCRIBED"
    const val KEY_DIGITAL_BUY = "BUY"
    const val KEY_DIGITAL_NO_BUY = "NO_BUY"
    const val KEY_DIGITAL_SHARE = "SHARE"
    const val KEY_DIGITAL_NO_SHARE = "NO_SHARE"
    const val KEY_HAS_CASH_PAYMENT = "HAS_CASH_PAYMENT"
    const val KEY_ORDER_TYPES = "ORDER_TYPES"

    const val KEY_HAS_NOT_CASH_PAYMENT = "HAS_NOT_CASH_PAYMENT"
    const val KEY_HAS_YES_MULTIBRAND = "YES_MULTIBRAND"
    const val KEY_HAS_NO_MULTIBRAND = "NO_MULTIBRAND"

    const val KEY_HAS_YES_MULTICATEGORY = "YES_MULTICATEGORY"
    const val KEY_HAS_NO_MULTICATEGORY = "NO_MULTICATEGORY"


    fun newCycleKeys(): List<String> {
        return listOf(
            KEY_6D6,
            KEY_5D5,
            KEY_4D4,
            KEY_3D3,
            KEY_2D2,
            KEY_1D1
        )
    }

}
