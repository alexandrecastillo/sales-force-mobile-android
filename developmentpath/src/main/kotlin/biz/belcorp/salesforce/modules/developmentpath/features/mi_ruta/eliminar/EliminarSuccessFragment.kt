package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eliminar

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import biz.belcorp.salesforce.core.base.BaseDialogFragment
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.FirebaseAnalyticsPresenter
import kotlinx.android.synthetic.main.dialog_mi_ruta_eliminar_de_plan_confirm.*

class EliminarSuccessFragment : BaseDialogFragment() {

    private lateinit var nombre: String
    var listener: Listener? = null

    private val firebaseAnalyticsPresenter: FirebaseAnalyticsPresenter by injectFragment()

    companion object {

        private const val NOMBRE = "NOMBRE"

        fun newInstance(nombre: String): EliminarSuccessFragment {
            val args = Bundle()
            val fragment = EliminarSuccessFragment()
            args.putString(NOMBRE, nombre)
            fragment.arguments = args

            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        nombre = arguments!!.getString(NOMBRE, "")
    }

    override fun getLayout() = R.layout.dialog_mi_ruta_eliminar_de_plan_confirm


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        text_ruta_eliminar_title.text = getString(R.string.mi_ruta_eliminar_title_confirm,
                nombre)

        button_eliminar_de_plan_ok.setOnClickListener {
            firebaseAnalyticsPresenter.enviarEvento(TagAnalytics.EVENTO_MENU_MODAL_ELIMINAR_PLANIFICADA_ACEPTAR)
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
