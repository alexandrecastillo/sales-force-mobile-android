package biz.belcorp.salesforce.modules.developmentpath.features.habilidades.reconocer.view

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.dip
import biz.belcorp.salesforce.modules.developmentpath.R
import kotlinx.android.synthetic.main.fragment_habilidades_reconocidas_success_fragment.*

class HabilidadesReconocidasSuccessDialogFragment : DialogFragment(){

    lateinit var rol: Rol

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            rol = it?.getSerializable(ARG_ROL) as Rol
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(
            R.layout.fragment_habilidades_reconocidas_success_fragment, container,
            false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        iv_close.setOnClickListener {
            dialog?.dismiss()
        }
        tv_reconocidas_sugerencia?.text = getString(R.string.reconocida_sugerencia, rol.comoTexto())
    }

    override fun onStart() {
        super.onStart()
        ajustarTamanioAPantalla()
    }

    private fun ajustarTamanioAPantalla() {
        val width = resources.displayMetrics.widthPixels - dip(40)
        val height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog?.window?.setLayout(width, height)
    }

    companion object {

        const val ARG_ROL = "ARG_ROL"

        fun newInstance(rol: Rol): HabilidadesReconocidasSuccessDialogFragment {
            val mBundel = Bundle()
            val mFragment = HabilidadesReconocidasSuccessDialogFragment()
            mBundel.apply {
                putSerializable(ARG_ROL, rol)
            }
            mFragment.arguments = mBundel
            return mFragment
        }
    }
}
