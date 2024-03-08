package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.replanificar

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import biz.belcorp.salesforce.core.base.BaseDialogFragment
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.FirebaseAnalyticsPresenter
import kotlinx.android.synthetic.main.fragment_mi_ruta_replanificar_success.*

class ReprogramarSuccessFragment : BaseDialogFragment() {

    private lateinit var fecha: String
    private lateinit var hora: String
    var listener: Listener? = null

    private val firebaseAnalytics: FirebaseAnalyticsPresenter by injectFragment()

    companion object {
        private const val FECHA = "FECHA"
        private const val HORA = "HORA"

        fun newInstance(fecha: String, hora: String): ReprogramarSuccessFragment {
            val args = Bundle()
            val fragment = ReprogramarSuccessFragment()
            args.putString(FECHA, fecha)
            args.putString(HORA, hora)
            fragment.arguments = args

            return fragment
        }
    }

    override fun getLayout(): Int {
        return R.layout.fragment_mi_ruta_replanificar_success
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fecha = arguments!!.getString(FECHA, "")
        hora = arguments!!.getString(HORA, "")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tv_titulo.text = context?.getString(R.string.mi_ruta_replanificar_visita_success_title)
        tv_fecha.text = String.format(obtenerRecursoFecha(),
                fecha)
        tv_hora.text = String.format(obtenerRecursoHora(),
                hora)

        button_ok.setOnClickListener {
            firebaseAnalytics.enviarEvento(TagAnalytics.EVENTO_RE_PLANIFICAR_ACEPTAR_VISITA)
            this.dismiss()
        }
    }

    private fun obtenerRecursoFecha() =
            context?.getString(R.string.mi_ruta_replanificar_visita_success_fecha).toString()

    private fun obtenerRecursoHora() =
            context?.getString(R.string.mi_ruta_replanificar_visita_success_hora).toString()

    override fun onDismiss(dialog: DialogInterface) {
        listener?.alCerrarDialogoDeExito()
        super.onDismiss(dialog)
    }

    interface Listener {
        fun alCerrarDialogoDeExito()
    }
}
