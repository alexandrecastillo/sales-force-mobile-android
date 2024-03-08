package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.adicionar

import android.os.Bundle
import android.view.View
import biz.belcorp.salesforce.core.base.BaseDialogFragment
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.core.utils.setOnOneClickListener
import biz.belcorp.salesforce.core.utils.toast
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.ruta.data.SenderCambioRDD
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.FirebaseAnalyticsPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.planificar.PlanificarSuccessFragment
import kotlinx.android.synthetic.main.fragment_mi_ruta_registrar_replanificar.*
import java.util.*

/**
 * Created by soporte on 19/02/2018. */

class AdicionarVisitaFragment : BaseDialogFragment(),
        AdicionarVisitaView,
        PlanificarSuccessFragment.Listener {

    override fun getLayout(): Int {
        return R.layout.fragment_mi_ruta_registrar_replanificar
    }

    private var personaId = -1L
    private lateinit var rol: Rol

    private val presenter: AdicionarVisitaPresenter by injectFragment()

    private val senderCambioRDD: SenderCambioRDD by injectFragment()

    private val firebaseAnalytics: FirebaseAnalyticsPresenter by injectFragment()

    companion object {

        private const val ID_VISITA = "ID_PERSONA"
        private const val ROL = "ROL"

        fun newInstance(personaId: Long, rol: Rol): AdicionarVisitaFragment {
            val args = Bundle()
            val fragment = AdicionarVisitaFragment()
            args.putLong(ID_VISITA, personaId)
            args.putSerializable(ROL, rol)
            fragment.arguments = args

            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        personaId = arguments!!.getLong(ID_VISITA)
        rol = arguments!!.getSerializable(ROL) as Rol
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpActions()
        setupComponents()
        firebaseAnalytics.enviarPantallaPerfil(TagAnalytics.PLANIFICAR_VISITA,rol, personaId)
    }

    private fun setUpActions() {

        button_save?.setOnOneClickListener {
            if (selector_fecha.date < Date()) {
                context?.toast(R.string.mi_ruta_fecha_invalida)
                return@setOnOneClickListener
            } else {
                presenter.adicionar(personaId, rol, selector_fecha.date)
                dialog?.hide()
            }
        }

        button_registrar_visita_cancel?.setOnClickListener {
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

        val successFragment = PlanificarSuccessFragment
                .newInstance(fecha, hora)
                .comoVisitaAdicional()

        successFragment.listener = this
        successFragment.show(childFragmentManager, "AdicionarExito")
    }

    override fun mostrarError(mensaje: String) {
        toast(mensaje)
        dismiss()
    }

    override fun notificarCambioPlan() {
        senderCambioRDD.notificarCambioEnPlanificacion()
    }

    override fun alCerrarDialogoDeExito() {
        dismiss()
    }
}
