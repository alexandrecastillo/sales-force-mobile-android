package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import biz.belcorp.mobile.components.core.extensions.setSafeOnClickListener
import biz.belcorp.salesforce.core.utils.gone
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.core.utils.invisible
import biz.belcorp.salesforce.core.utils.visible
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.menu.Visibilidad
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.FirebaseAnalyticsPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model.MenuPersonaModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_mi_ruta_menu_consultora.*
import biz.belcorp.salesforce.components.R as ComponentR

class MenuPersonaFragment : BottomSheetDialogFragment() {

    var model: MenuPersonaModel? = null
    var listener: AccionesListener? = null

    private val firebaseAnalytics: FirebaseAnalyticsPresenter by injectFragment()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mi_ruta_menu_consultora, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tv_contacto?.text = getString(R.string.mi_ruta_contacta_a, model?.nombreConsultora)
        mostrarUOcultarOpciones()
        setUpActions()
    }

    private fun mostrarUOcultarOpciones() {
        mostrarContactaA()
        mostrarWhatsapp()
        mostrarSms()
        mostrarEmail()
        mostrarTelefono()
        mostrarUbicar()
        mostrarAdicionarVisita()
        mostrarReplanificar()
        mostrarRegistrar()
        mostrarEliminar()
    }

    private fun mostrarEliminar() {
        when (model?.mostrarEliminar) {
            Visibilidad.HABILITADO -> {
                ll_eliminar_de_calendario?.visible()
                ll_eliminar_de_calendario?.isEnabled = true
                tv_eliminar?.setTextColor(obtenerColor(R.color.rdd_danger))
                iv_eliminar?.setImageDrawable(obtenerDrawable(R.drawable.ic_rdd_eliminar))
            }
            Visibilidad.DESHABILITADO -> {
                ll_eliminar_de_calendario?.visible()
                ll_eliminar_de_calendario?.isEnabled = false
                tv_eliminar?.setTextColor(obtenerColor(R.color.rdd_danger_disabled))
                iv_eliminar?.setImageDrawable(obtenerDrawable(R.drawable.ic_rdd_eliminar_disabled))
            }
            Visibilidad.INVISIBLE -> {
                ll_eliminar_de_calendario?.invisible()
                ll_eliminar_de_calendario?.isEnabled = false
            }
        }
    }

    private fun mostrarRegistrar() {
        when (model?.mostrarRegistrar) {
            Visibilidad.HABILITADO -> {
                ll_registrar?.visible()
                ll_registrar?.isEnabled = true
                tv_registrar?.setTextColor(obtenerColor(ComponentR.color.colorButtonVariant))
                iv_registrar?.setImageDrawable(obtenerDrawable(R.drawable.ic_rdd_registrar))
            }
            Visibilidad.DESHABILITADO -> {
                ll_registrar?.visible()
                ll_registrar?.isEnabled = false
                tv_registrar?.setTextColor(obtenerColor(R.color.rdd_green_disabled))
                iv_registrar?.setImageDrawable(obtenerDrawable(R.drawable.ic_rdd_registrar_disabled))
            }
            Visibilidad.INVISIBLE -> {
                ll_registrar?.invisible()
                ll_registrar?.isEnabled = false
            }
        }
    }

    private fun mostrarAdicionarVisita() {
        when (model?.mostrarAdicionarVisita) {
            Visibilidad.HABILITADO -> {
                ll_adicionar?.visible()
                ll_adicionar?.isEnabled = true
            }
            Visibilidad.DESHABILITADO ->
                throw UnsupportedOperationException()
            Visibilidad.INVISIBLE -> {
                ll_adicionar?.gone()
                separador_adicionar?.gone()
            }
        }
    }

    private fun mostrarReplanificar() {
        when (model?.mostrarReplanificar) {
            Visibilidad.HABILITADO -> {
                ll_replanificar?.visible()
                ll_replanificar?.isEnabled = true
                tv_replanificar?.setTextColor(obtenerColor(R.color.rdd_primary_text))
                iv_replanificar?.setImageDrawable(obtenerDrawable(R.drawable.ic_rdd_replanificar))
            }
            Visibilidad.DESHABILITADO -> {
                ll_replanificar?.visible()
                ll_replanificar?.isEnabled = false
                tv_replanificar?.setTextColor(obtenerColor(R.color.rdd_primary_text_disabled))
                iv_replanificar?.setImageDrawable(obtenerDrawable(R.drawable.ic_rdd_replanificar_disabled))
            }
            Visibilidad.INVISIBLE -> {
                ll_replanificar?.invisible()
                ll_replanificar?.isEnabled = false
            }
        }
    }

    private fun mostrarUbicar() {
        when (model?.mostrarUbicar) {
            Visibilidad.HABILITADO,
            Visibilidad.DESHABILITADO -> {
                ll_ubicar?.visible()
                separador_ubicar?.visible()
            }
            Visibilidad.INVISIBLE -> {
                ll_ubicar?.gone()
                separador_ubicar?.gone()
            }
        }
    }

    private fun mostrarTelefono() {
        when (model?.mostrarTelefono) {
            Visibilidad.HABILITADO,
            Visibilidad.DESHABILITADO -> {
                ll_phone?.visible()
            }
            Visibilidad.INVISIBLE -> {
                ll_phone?.gone()
            }
        }
    }

    private fun mostrarSms() {
        when (model?.mostrarSms) {
            Visibilidad.HABILITADO,
            Visibilidad.DESHABILITADO -> {
                ll_sms?.visible()
            }
            Visibilidad.INVISIBLE -> {
                ll_sms?.gone()
            }
        }
    }

    private fun mostrarEmail() {
        when (model?.mostrarEmail) {
            Visibilidad.HABILITADO,
            Visibilidad.DESHABILITADO -> {
                ll_email?.visible()
            }
            Visibilidad.INVISIBLE -> {
                ll_email?.gone()
            }
        }
    }

    private fun mostrarWhatsapp() {
        when (model?.mostrarWhatsapp) {
            Visibilidad.HABILITADO,
            Visibilidad.DESHABILITADO -> {
                ll_whatsapp?.visible()
            }
            Visibilidad.INVISIBLE -> {
                ll_whatsapp?.gone()
            }
        }
    }

    private fun mostrarContactaA() {
        when (model?.mostrarContactaA) {
            Visibilidad.HABILITADO,
            Visibilidad.DESHABILITADO -> {
                ll_contacta_a?.visible()
                ll_opciones_contacto?.visible()
            }
            Visibilidad.INVISIBLE -> {
                ll_contacta_a?.gone()
                ll_opciones_contacto?.gone()
            }
        }
    }

    private fun setUpActions() {
        val model = this.model ?: return
        ll_eliminar_de_calendario?.setOnClickListener {
            firebaseAnalytics.enviarEvento(TagAnalytics.EVENTO_MENU_MI_RUTA_ELIMINAR)
            this.dismiss()
            listener?.alPresionarEliminacion(
                model.visitaId,
                requireNotNull(model.fecha),
                model.nombreConsultora
            )
        }

        ll_replanificar?.setOnClickListener {
            firebaseAnalytics.enviarEvento(TagAnalytics.EVENTO_MENU_MI_RUTA_REPLANIFICAR)
            this.dismiss()
            listener?.alPresionarReplanificacion(model.visitaId, requireNotNull(model.fecha))
        }

        ll_registrar?.setSafeOnClickListener {
            firebaseAnalytics.enviarEvento(TagAnalytics.EVENTO_MENU_MI_RUTA_VISITA)
            this.dismiss()
            val personIdentifier = PersonIdentifier(model.personaId, model.personCode, model.rol)
            listener?.alPresionarRegistro(personIdentifier)
        }

        ll_adicionar?.setOnClickListener {
            this.dismiss()
            listener?.alPresionarAdicionarVisita(model.personaId, model.rol)
        }

        ll_phone?.setOnClickListener {
            firebaseAnalytics.enviarEvento(TagAnalytics.EVENTO_MENU_MI_RUTA_LLAMAR)
            this.dismiss()
            if (model.numeroTelefono != null && model.numeroTelefono.trim() != Constant.EMPTY_STRING) {
                listener?.alLlamar(model.numeroTelefono)
            }
        }

        ll_sms?.setOnClickListener {
            firebaseAnalytics.enviarEvento(TagAnalytics.EVENTO_MENU_MI_RUTA_SMS)
            this.dismiss()
            if (model.numeroTelefono != null && model.numeroTelefono.trim() != Constant.EMPTY_STRING) {
                listener?.alEnviarSMS(model.numeroTelefono)
            }
        }

        ll_whatsapp?.setOnClickListener {
            firebaseAnalytics.enviarEvento(TagAnalytics.EVENTO_MENU_MI_RUTA_WHATSAPP)
            this.dismiss()
            if (model.numeroTelefono != null && model.numeroTelefono.trim() != Constant.EMPTY_STRING) {
                listener?.alEnviarWhatsApp(model.numeroTelefono)
            }
        }

        ll_ubicar?.setOnClickListener {
            firebaseAnalytics.enviarEvento(TagAnalytics.EVENTO_MENU_MI_RUTA_MAPA)
            this.dismiss()
            listener?.alPresionarUbicacion(model)
        }

        ll_email?.setOnClickListener {
            this.dismiss()
            listener?.alEviarEmail(model.email!!)

        }
    }

    private fun obtenerColor(id: Int) = ContextCompat.getColor(requireContext(), id)

    private fun obtenerDrawable(id: Int) = ContextCompat.getDrawable(requireContext(), id)
}
