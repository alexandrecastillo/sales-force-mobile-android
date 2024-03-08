package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view.mapa

import android.os.Bundle
import android.view.View
import biz.belcorp.salesforce.core.base.BaseDialogFragment
import biz.belcorp.salesforce.modules.developmentpath.R
import kotlinx.android.synthetic.main.dialog_rdd_sin_conexion.*

class SinConexionFragment : BaseDialogFragment() {
    override fun getLayout() = R.layout.dialog_rdd_sin_conexion

    companion object {
        fun newInstance(): SinConexionFragment {
            return SinConexionFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpActions()
    }

    private fun setUpActions() {
        button_dialog_rdd_sin_conexion_aceptar.setOnClickListener {
            this.dismiss()
        }
    }
}
