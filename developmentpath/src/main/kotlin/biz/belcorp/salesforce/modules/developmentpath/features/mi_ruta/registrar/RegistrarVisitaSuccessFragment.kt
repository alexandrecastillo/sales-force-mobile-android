package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.registrar

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import biz.belcorp.salesforce.modules.developmentpath.R
import kotlinx.android.synthetic.main.dialog_rdd_registrar_visita_success.*

/**
 * Created by Oscar Sequeiros on 2/03/2018.
 * Fragment de mensaje de éxito de registro de visita
 */
class RegistrarVisitaSuccessFragment : DialogFragment() {

    var listener: Listener? = null

    companion object {
        fun newInstance(): RegistrarVisitaSuccessFragment =
                RegistrarVisitaSuccessFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return inflater.inflate(
                R.layout.dialog_rdd_registrar_visita_success,
                container,
                false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button_dialog_registrar_success_accept.setOnClickListener {
            this.dismiss()
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        listener?.alCerrarDialogoDeExito()
        super.onDismiss(dialog)
    }

    interface Listener {
        fun alCerrarDialogoDeExito()
    }
}
