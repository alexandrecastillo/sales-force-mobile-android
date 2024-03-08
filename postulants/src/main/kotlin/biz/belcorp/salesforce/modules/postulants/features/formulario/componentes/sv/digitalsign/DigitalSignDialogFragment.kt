package biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.sv.digitalsign

import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.features.widget.dialogs.CustomDialog
import biz.belcorp.salesforce.modules.postulants.utils.customDialog
import kotlinx.android.synthetic.main.dialog_fragment_digital_sign.*
import kotlinx.android.synthetic.main.dialog_material.*

class DigitalSignDialogFragment : DialogFragment() {

    private var listener: Listener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_fragment_digital_sign, container, false)
    }

    override fun getTheme(): Int {
        return R.style.DialogTheme
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        canvas.isDrawingCacheEnabled = true
        canvas.buildDrawingCache()

        btnBorrar.setOnClickListener {
            canvas.clear()
        }

        btnAceptar.setOnClickListener {
            if (!canvas.getOnSignDone()) {
                showNotFoundSign()
                return@setOnClickListener
            }

            //mandar el Bitmap
            Bitmap.createBitmap(canvas.drawingCache)?.let { bt ->
                listener?.onSignDone(bt)
            } ?: run {
                listener?.onSignError()
            }

            dismissAllowingStateLoss()
        }
    }

    fun setListener(listener: Listener) {
        if (this@DigitalSignDialogFragment.listener != null) return

        this@DigitalSignDialogFragment.listener = listener
    }

    private fun showNotFoundSign() {
        context?.customDialog(R.layout.dialog_material) {
            ivIcon.visibility = View.GONE
            tvTitle.setText(R.string.permisos_rechazados)
            tvContent.text = context.getString(R.string.debes_ingresar_firma)
            btnOk.setText(R.string.accept)
            btnOk.setOnClickListener {
                dismiss()
            }
        }?.show()
    }

    companion object {
        interface Listener {
            fun onSignDone(bitmap: Bitmap)
            fun onSignError()
        }
    }
}
