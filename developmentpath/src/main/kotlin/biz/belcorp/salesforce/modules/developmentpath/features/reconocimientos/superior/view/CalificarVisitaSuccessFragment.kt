package biz.belcorp.salesforce.modules.developmentpath.features.reconocimientos.superior.view

import android.os.Bundle
import android.view.View
import biz.belcorp.salesforce.core.base.BaseDialogFragment
import biz.belcorp.salesforce.modules.developmentpath.R
import kotlinx.android.synthetic.main.dialog_calificacion_visita_success.*

class CalificarVisitaSuccessFragment : BaseDialogFragment() {

    override fun getLayout(): Int {
        isCancelable = false
        return R.layout.dialog_calificacion_visita_success
    }

    companion object {
        private val NAME_CALIFICADA = "NAME_CALIFICADA"

        fun newInstance(name: String): CalificarVisitaSuccessFragment {
            val args = Bundle()
            val fragment = CalificarVisitaSuccessFragment()
            args.putString(NAME_CALIFICADA, name)
            fragment.arguments = args

            return fragment
        }
    }

    private var listener: OnFragmentDialogInteractionListener? = null

    private var name = ""

    fun establecerListener(listener: OnFragmentDialogInteractionListener) {
        this.listener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        name = arguments?.getString(NAME_CALIFICADA) ?: ""
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pintarNombre()
        escucharAcciones()
    }

    private fun pintarNombre() {
        text_dialog_registrar_success_title.text =
            resources.getString(R.string.gracias_por_reconocer_a, name)
    }

    private fun escucharAcciones() {
        button_dialog_registrar_success_accept.setOnClickListener {
            listener?.onAccept()
            dismiss()
        }
    }

    interface OnFragmentDialogInteractionListener {
        fun onAccept()
    }
}
