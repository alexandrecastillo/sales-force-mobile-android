package biz.belcorp.salesforce.modules.developmentpath.features

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import biz.belcorp.salesforce.core.utils.dip
import biz.belcorp.salesforce.core.utils.withArguments
import biz.belcorp.salesforce.modules.developmentpath.R
import kotlinx.android.synthetic.main.fragment_confirmacion_navegacion_dialog.*

class ConfirmacionDialogFragment : DialogFragment() {

    private var listener: Listener? = null
    private var titulo: String? = null
    private var ok: String? = null

    companion object {

        private const val ARG_TITULO = "param1"
        private const val ARG_AL_PRESIONAR_OK = "param2"
        private const val WINDOW_PADDING = 36

        fun newInstance(titulo: String?, ok: String?): ConfirmacionDialogFragment {
            return ConfirmacionDialogFragment()
                .withArguments(
                    ARG_TITULO to titulo,
                    ARG_AL_PRESIONAR_OK to ok
                )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        titulo = arguments?.getString(ARG_TITULO)
        ok = arguments?.getString(ARG_AL_PRESIONAR_OK)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =
            inflater.inflate(R.layout.fragment_confirmacion_navegacion_dialog, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configurarBotones()
        tvRutaEliminarTitle?.text = titulo
        buttonOk?.text = ok.orEmpty()
    }

    override fun onStart() {
        super.onStart()
        ajustarTamanio()
    }

    private fun ajustarTamanio() {
        val width = requireContext().resources.displayMetrics.widthPixels - dip(WINDOW_PADDING)
        val height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog?.window?.setLayout(width, height)
    }

    private fun configurarBotones() {
        buttonCancel?.setOnClickListener { dismiss() }

        buttonOk?.setOnClickListener {
            listener?.alPresionarOk()
            dismiss()
        }
    }

    fun setListener(accion: () -> Unit) {
        listener = object : Listener {
            override fun alPresionarOk() {
                accion.invoke()
            }
        }
    }

    interface Listener {
        fun alPresionarOk()
    }
}
