package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.planificar

import android.os.Bundle
import android.view.View
import biz.belcorp.salesforce.core.base.BaseDialogFragment
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.ruta.data.SenderCambioRDD
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.core.utils.safeDismiss
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.FirebaseAnalyticsPresenter
import kotlinx.android.synthetic.main.fragment_mi_ruta_registrar_replanificar.*
import biz.belcorp.salesforce.core.utils.toast
import java.util.*


class PlanificarFragment : BaseDialogFragment(),
        PlanificarView,
        PlanificarSuccessFragment.Listener {

    private var visitaId = -1L

    private val presenter: PlanificarPresenter by injectFragment()

    private val senderCambioRDD: SenderCambioRDD by injectFragment()

    private val firebaseAnalytics: FirebaseAnalyticsPresenter by injectFragment()

    companion object {

        private const val ID_VISITA = "ID_PERSONA"

        fun newInstance(visitaId: Long): PlanificarFragment {
            val args = Bundle()
            val fragment = PlanificarFragment()
            args.putLong(ID_VISITA, visitaId)
            fragment.arguments = args

            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        visitaId = arguments!!.getLong(ID_VISITA)
    }

    override fun getLayout(): Int {
        return R.layout.fragment_mi_ruta_registrar_replanificar
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpActions()
        setupComponents()
        presenter.view = this
        firebaseAnalytics.enviarPantallaPlanificar(TagAnalytics.PLANIFICAR_VISITA, visitaId)
    }

    private fun setUpActions() {

        button_save.setOnClickListener {
            if (selector_fecha.date < Date()) {
                context?.toast(R.string.mi_ruta_fecha_invalida)
                return@setOnClickListener
            } else {
                firebaseAnalytics.enviarEvento(TagAnalytics.EVENTO_PLANIFICAR_VISITA)
                presenter.planificar(visitaId, selector_fecha.date)
                dialog?.hide()
            }
        }

        button_registrar_visita_cancel.setOnClickListener {
            this.dismiss()
        }
    }

    private fun setupComponents() {
        tv_titulo.text =
                context?.getString(R.string.mi_ruta_planificar_visita_title)
        button_save.text =
                context?.getString(R.string.mi_ruta_planificar_replanificar_adicionar_visita_accept)
        selector_fecha.title =
                getString(R.string.mi_ruta_planificar_accion)
    }

    override fun mostrarExito(fecha: String, hora: String) {
        if (!isResumed) return

        val successFragment = PlanificarSuccessFragment.newInstance(fecha, hora)
        successFragment.listener = this
        successFragment.show(childFragmentManager, "PlanificarExito")
    }

    override fun mostrarError(mensaje: String) {
        toast(mensaje)
        dismiss()
    }

    override fun notificarCambioPlan() {
        senderCambioRDD.notificarCambioEnPlanificacion()
    }

    override fun alCerrarDialogoDeExito() {
        safeDismiss()
    }
}
