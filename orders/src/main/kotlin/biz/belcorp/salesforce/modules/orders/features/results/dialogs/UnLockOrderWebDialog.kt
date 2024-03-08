package biz.belcorp.salesforce.modules.orders.features.results.dialogs

import biz.belcorp.salesforce.modules.orders.R

class UnLockOrderWebDialog(click: OrderWebDialogClick) : OrderWebDialog(click) {

    override fun getLayout() = R.layout.fragment_dialog_unlock_order_web

}
