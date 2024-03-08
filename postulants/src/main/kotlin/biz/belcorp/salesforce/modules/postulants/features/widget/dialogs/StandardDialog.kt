package biz.belcorp.salesforce.modules.postulants.features.widget.dialogs

import android.content.Context
import android.os.Bundle
import android.view.View
import biz.belcorp.salesforce.modules.postulants.R
import kotlinx.android.synthetic.main.dialog_standard.*


class StandardDialog(context: Context, theme: Int) : BaseDialog(context, theme) {

    var titleText: String? = null
    var titleTextResId: Int? = null
    var contentText: String? = null
    var contentTextResId: Int? = null
    var iconResId: Int? = null

    private var cancelText: String? = null
    private var cancelTextResId: Int? = null
    private var confirmText: String? = null
    private var confirmTextResId: Int? = null

    private var mOnCancel: ((StandardDialog) -> Unit)? = null
    private var mOnConfirm: ((StandardDialog) -> Unit)? = null

    override val layout: Int get() = R.layout.dialog_standard

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updateStyle()
        updateData()
    }

    private fun updateData() {

        titleTextResId?.also {
            titleText = context.getString(it)
        }

        contentTextResId?.also {
            contentText = context.getString(it)
        }

        confirmTextResId?.also {
            confirmText = context.getString(it)
        }

        cancelTextResId?.also {
            cancelText = context.getString(it)
        }

        titleText?.let {
            tvTitle.text = it
            tvTitle.visibility = View.VISIBLE
        }

        contentText?.let {
            tvContent.text = it
            tvContent.visibility = View.VISIBLE
        }

        confirmText?.let {
            btnConfirm.text = it
            btnConfirm.visibility = View.VISIBLE
            btnConfirm.setOnClickListener {
                mOnConfirm?.invoke(this) ?: dismiss()
            }
        }

        cancelText?.let {
            btnCancel.text = it
            btnCancel.visibility = View.VISIBLE
            btnCancel.setOnClickListener {
                mOnCancel?.invoke(this) ?: dismiss()
            }
        }

        iconResId?.let {
            ivIcon.setImageResource(it)
            ivIcon.visibility = View.VISIBLE
        }

    }

    private fun updateStyle() {

        btnCancel.setBackgroundResource(cancelButtonBackground)
        btnCancel.setTextColor(cancelTextColor)
        btnConfirm.setBackgroundResource(confirmButtonBackground)
        btnConfirm.setTextColor(confirmTextColor)

    }

    fun cancelButton(cancelText: String, listener: ((StandardDialog) -> Unit)? = null) {
        this.cancelText = cancelText
        this.mOnCancel = listener
    }

    fun cancelButton(cancelTextResId: Int, listener: ((StandardDialog) -> Unit)? = null) {
        this.cancelTextResId = cancelTextResId
        this.mOnCancel = listener
    }

    fun confirmButton(confirmText: String, listener: ((StandardDialog) -> Unit)? = null) {
        this.confirmText = confirmText
        this.mOnConfirm = listener
    }

    fun confirmButton(confirmTextResId: Int, listener: ((StandardDialog) -> Unit)? = null) {
        this.confirmTextResId = confirmTextResId
        this.mOnConfirm = listener
    }

}
