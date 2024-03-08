package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.replanificar

import android.os.Bundle
import android.view.View
import biz.belcorp.salesforce.core.base.BaseDialogFragment
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.core.utils.safeDismiss
import biz.belcorp.salesforce.core.utils.toast
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.ruta.data.SenderCambioRDD
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.FirebaseAnalyticsPresenter
import kotlinx.android.synthetic.main.fragment_mi_ruta_registrar_replanificar.*
import java.util.*

class ReprogramarFragment : BaseDialogFragment(),
        ReprogramarView,
        ReprogramarSuccessFragment.Listener {

    private var visitaId: Long = -1
    private lateinit var fechaAnterior: Date

    private val presenter: ReprogramarPresenter by injectFragment()

    private val firebaseAnalytics: FirebaseAnalyticsPresenter by injectFragment()

    private val senderCambioRDD: SenderCambioRDD by injectFragment()

    companion object {
        private const val ID_VISITA = "ID_PERSONA"
        private const val FECHA_ANTERIOR = "FECHA_ANTERIOR"

        fun newInstance(visitaId: Long,
                        fechaAnterior: Date): ReprogramarFragment {

            val fragment = ReprogramarFragment()
            val args = Bundle()
            args.putLong(ID_VISITA, visitaId)
            args.putSerializable(FECHA_ANTERIOR, fechaAnterior)
            fragment.arguments = args

            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        visitaId = arguments!!.getLong(ID_VISITA)
        fechaAnterior = arguments!!.getSerializable(FECHA_ANTERIOR) as Date
    }

    override fun getLayout(): Int {
        return R.layout.fragment_mi_ruta_registrar_replanificar
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupActions()
        setupComponents()
        presenter.view = this
        firebaseAnalytics.enviarPantallaPlanificar(TagAnalytics.RE_PLANIFICAR_VISITA, visitaId)
    }

    private fun setupActions() {
        button_save.setOnClickListener {
            if (selector_fecha.date < Date()) {
                context?.toast(R.string.mi_ruta_fecha_invalida)
                return@setOnClickListener
            } else {
                firebaseAnalytics.enviarEvento(TagAnalytics.EVENTO_RE_PLANIFICAR_VISITA)
                presenter.replanificar(visitaId,
                        fechaAnterior,
                        selector_fecha.date)
                dialog?.hide()
            }
        }

        button_registrar_visita_cancel.setOnClickListener {
            this.dismiss()
        }
    }

    private fun setupComponents() {
        tv_titulo.text =
                context?.getString(R.string.mi_ruta_replanificar_visita_title)
        button_save.text =
                context?.getString(R.string.mi_ruta_planificar_replanificar_adicionar_visita_accept)
        selector_fecha.title =
                getString(R.string.mi_ruta_replanificar_accion)
    }

    override fun mostrarExito(fecha: String, hora: String) {
        if (!isResumed) return
        val successFragment = ReprogramarSuccessFragment.newInstance(fecha, hora)
        successFragment.listener = this
        successFragment.show(childFragmentManager, "ReprogramarExito")
    }

    override fun mostrarError(mensaje: String) {
        context?.let {
            toast(mensaje)
            safeDismiss()
        }
    }

    override fun alCerrarDialogoDeExito() {
        safeDismiss()
    }

    override fun notificarCambioEnPlanificacion() {
        senderCambioRDD.notificarCambioEnPlanificacion()
    }
}
