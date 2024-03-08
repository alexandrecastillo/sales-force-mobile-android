package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eliminar

import android.os.Bundle
import android.view.View
import biz.belcorp.salesforce.core.base.BaseDialogFragment
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.core.utils.toast
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.ruta.data.SenderCambioRDD
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.FirebaseAnalyticsPresenter
import kotlinx.android.synthetic.main.dialog_mi_ruta_eliminar_de_plan.*
import java.util.*

class EliminarDePlanFragment : BaseDialogFragment(),
    EliminarView,
    EliminarSuccessFragment.Listener {

    private var visitaId: Long = -1
    private lateinit var nombre: String
    private lateinit var fechaOriginal: Date

    private val presenter: EliminarPresenter by injectFragment()

    private val senderCambioRDD: SenderCambioRDD by injectFragment()

    private val firebaseAnalytics: FirebaseAnalyticsPresenter by injectFragment()

    companion object {
        private const val ID_VISITA = "ID_VISITA"
        private const val NOMBRE = "NOMBRE"
        private const val FECHA_ORIGINAL = "FECHA_ORIGINAL"

        fun newInstance(
            visitaId: Long,
            fechaAnterior: Date,
            nombre: String?
        ): EliminarDePlanFragment {
            val args = Bundle()
            val fragment =
                EliminarDePlanFragment()
            args.putLong(ID_VISITA, visitaId)
            args.putString(NOMBRE, nombre)
            args.putSerializable(FECHA_ORIGINAL, fechaAnterior)
            fragment.arguments = args

            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        visitaId = arguments!!.getLong(ID_VISITA)
        nombre = arguments!!.getString(NOMBRE, "")
        fechaOriginal = arguments!!.getSerializable(FECHA_ORIGINAL) as Date
    }

    override fun getLayout(): Int {
        return R.layout.dialog_mi_ruta_eliminar_de_plan
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpMessage()
        setUpActions()
        firebaseAnalytics.enviarPantallaEliminarCalendario(
            TagAnalytics.ELIMINA_TU_CALENDARIO,
            visitaId
        )
    }

    private fun setUpMessage() {
        text_ruta_eliminar_title.text = getString(
            R.string.mi_ruta_eliminar_message,
            nombre
        )
    }

    private fun setUpActions() {
        button_eliminar_de_plan_cancel.setOnClickListener {
            this.dismiss()

        }

        button_eliminar_de_plan_confirm.setOnClickListener {
            firebaseAnalytics.enviarEvento(TagAnalytics.EVENTO_MENU_MODAL_ELIMINAR_PLANIFICADA)
            this.dialog?.hide()
            presenter.eliminar(visitaId, fechaOriginal)
        }
    }

    override fun mostrarExito() {
        val fragment = EliminarSuccessFragment.newInstance(nombre)
        fragment.listener = this

        fragment.show(childFragmentManager, "EliminarSuccess")
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
