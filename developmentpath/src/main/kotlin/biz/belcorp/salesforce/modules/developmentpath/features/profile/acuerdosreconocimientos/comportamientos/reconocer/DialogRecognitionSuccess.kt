package biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.reconocer

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.R
import kotlinx.android.synthetic.main.dialog_consultant_recognition.*

class DialogRecognitionSuccess : DialogFragment() {

    var onDismissCallback: (() -> Unit)? = null
    private lateinit var rol: Rol

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rol = arguments!!.getSerializable(ROL) as Rol
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return inflater.inflate(R.layout.dialog_consultant_recognition, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        when (rol) {
            Rol.CONSULTORA -> text_dialog_registrar_success_title.text =
                getString(R.string.reconocida_success_consultora)
            else -> text_dialog_registrar_success_title.text =
                getString(R.string.reconocida_success_no_consultora)
        }
        imgExit.setOnClickListener {
            onDismissCallback?.invoke()
            dismiss()
        }
    }

    companion object {

        const val ROL = "PERSON_PERFIL"

        fun newInstance(rol: Rol): DialogRecognitionSuccess {
            val fragment = DialogRecognitionSuccess()
            val bundle = Bundle()
            bundle.putSerializable(ROL, rol)
            fragment.arguments = bundle
            return fragment
        }
    }
}
