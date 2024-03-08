package biz.belcorp.salesforce.modules.orders.features.results.dialogs

import biz.belcorp.salesforce.modules.orders.R

class LockOrderWebDialog(click: OrderWebDialogClick) : OrderWebDialog(click) {

    override fun getLayout() = R.layout.fragment_dialog_lock_order_web

}
