package biz.belcorp.salesforce.modules.orders.features.results.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import biz.belcorp.salesforce.modules.orders.R
import kotlinx.android.synthetic.main.fragment_dialog_lock_order_web.*

abstract class OrderWebDialog(private var click: OrderWebDialogClick) : DialogFragment() {

    abstract fun getLayout(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentView = inflater.inflate(getLayout(), container, false)
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return fragmentView
    }

    override fun onStart() {
        super.onStart()
        val width = resources.getDimensionPixelSize(R.dimen.popup_width)
        val height = resources.getDimensionPixelSize(R.dimen.popup_height)
        dialog?.window?.setLayout(width, height)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view_close?.setOnClickListener {
            dialog?.dismiss()
        }

        tv_cancel?.setOnClickListener {
            dialog?.dismiss()
        }

        rl_accepted?.setOnClickListener {
            click.onClick()
            dialog?.dismiss()
        }

    }

    interface OrderWebDialogClick {
        fun onClick()
    }

}
