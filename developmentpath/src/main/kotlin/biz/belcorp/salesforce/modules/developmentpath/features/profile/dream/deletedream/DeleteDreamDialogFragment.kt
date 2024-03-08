package biz.belcorp.salesforce.modules.developmentpath.features.profile.dream.deletedream

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import biz.belcorp.salesforce.modules.developmentpath.R
import kotlinx.android.synthetic.main.dialog_fragment_delete_dream.*

class DeleteDreamDialogFragment : DialogFragment() {

    private var listener: DeleteDreamListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =
            inflater.inflate(R.layout.dialog_fragment_delete_dream, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpButtons()
    }

    private fun setUpButtons() {
        btn_cancel.setOnClickListener {
            dismiss()
        }
        btn_confirm.setOnClickListener {
            listener?.confirm()
            dismiss()
        }
    }

    fun setListener(action: () -> Unit) {
        listener = object : DeleteDreamListener {
            override fun confirm() {
                action.invoke()
            }
        }
    }

    interface DeleteDreamListener {
        fun confirm()
    }

    companion object {
        const val TAG = "DeleteDreamDialogFragment"
    }
}
