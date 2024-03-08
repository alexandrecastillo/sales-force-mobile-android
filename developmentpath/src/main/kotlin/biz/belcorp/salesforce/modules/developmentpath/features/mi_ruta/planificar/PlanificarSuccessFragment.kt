package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.planificar

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import biz.belcorp.salesforce.core.base.BaseDialogFragment
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.FirebaseAnalyticsPresenter
import kotlinx.android.synthetic.main.fragment_mi_ruta_replanificar_success.*

class PlanificarSuccessFragment : BaseDialogFragment() {

    private lateinit var fecha: String
    private lateinit var hora: String
    private var esVisitaAdicional = false
    var listener: Listener? = null

    private val firebaseAnalytics: FirebaseAnalyticsPresenter by injectFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        obtainArguments()
    }


    override fun getLayout() = R.layout.fragment_mi_ruta_replanificar_success

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        establecerTituloVerificandoSiEsAdicional()

        tv_fecha?.text = String.format(
            obtenerRecursoFecha(),
            fecha
        )
        tv_hora?.text = String.format(
            obtenerRecursoHora(),
            hora
        )

        button_ok?.setOnClickListener {
            firebaseAnalytics.enviarEvento(TagAnalytics.EVENTO_PLANIFICAR_ACEPTAR_VISITA)
            this.dismiss()
        }
    }

    private fun obtainArguments() {
        arguments?.let {
            fecha = it.getString(FECHA, Constant.EMPTY_STRING)
            hora = it.getString(HORA, Constant.EMPTY_STRING)
        }
    }

    fun comoVisitaAdicional(): PlanificarSuccessFragment {
        esVisitaAdicional = true

        return this
    }

    private fun establecerTituloVerificandoSiEsAdicional() {
        if (esVisitaAdicional) {
            tv_titulo?.text =
                context?.getString(R.string.mi_ruta_planificar_visita_adicional_success_title)
        } else {
            tv_titulo?.text = context?.getString(R.string.mi_ruta_planificar_visita_success_title)
        }
    }

    private fun obtenerRecursoFecha() =
        context?.getString(R.string.mi_ruta_planificar_visita_success_fecha).toString()

    private fun obtenerRecursoHora() =
        context?.getString(R.string.mi_ruta_replanificar_visita_success_hora).toString()

    override fun onDismiss(dialog: DialogInterface) {
        listener?.alCerrarDialogoDeExito()
        super.onDismiss(dialog)
    }

    interface Listener {
        fun alCerrarDialogoDeExito()
    }

    companion object {
        private const val FECHA = "FECHA"
        private const val HORA = "HORA"

        fun newInstance(fecha: String, hora: String): PlanificarSuccessFragment {
            val args = Bundle()
            val fragment =
                PlanificarSuccessFragment()
            args.putString(FECHA, fecha)
            args.putString(HORA, hora)
            fragment.arguments = args

            return fragment
        }
    }
}
