package biz.belcorp.salesforce.modules.developmentpath.features.profile.goals.consultora.crear

import android.os.Bundle
import android.view.View
import biz.belcorp.salesforce.components.exported.sharedToolbarLight
import biz.belcorp.salesforce.core.base.BaseToolbarDialogFragment
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.core.utils.onTextChanged
import biz.belcorp.salesforce.core.utils.setOnItemSelectedListener
import biz.belcorp.salesforce.core.utils.withArguments
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.AnalyticsConstants
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.FirebaseAnalyticsPresenter
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.android.synthetic.main.fragment_meta_consultora.*

class CrearMetaConsultoraFragment : BaseToolbarDialogFragment(), CrearMetaConsultoraView {

    var listenerExito: ListenerExito? = null
    private val presenter: CrearMetaConsultoraPresenter by injectFragment()
    private val firebaseAnalyticsPresenter by injectFragment<FirebaseAnalyticsPresenter>()

    private var personaId = -1L
    private lateinit var rol: Rol

    override fun getLayout() = R.layout.fragment_meta_consultora

    override fun getToolbar(): MaterialToolbar? = sharedToolbarLight

    override fun getTitle(): String? = resources.getString(R.string.crear_meta_consultora_titulo)

    override fun getMode(): Mode = Mode.CLOSEABLE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recuperarArgumentos()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAnalyticsPresenter.enviarPantallaMetas(TagAnalytics.PERFIL_METAS, rol, personaId)
        configurarComponentes()
        cargarTiposMetas()
        cargarCampaniasLimite()
    }

    private fun recuperarArgumentos() {
        personaId = requireNotNull(arguments?.getLong(ARG_PERSONA_ID, 0))
        rol = requireNotNull(arguments?.getSerializable(ARG_ROL_ID) as? Rol)
    }

    private fun configurarComponentes() {
        configurarEditTextMonto()
        configurarEditTextComentario()
        configurarSpinnerTipoMeta()
        configurarSpinnerCampaniaInicio()
        configurarSpinnerCampaniaFin()
        btnGuardar?.setOnClickListener { guardar() }
    }

    private fun configurarEditTextMonto() {
        editTextMonto?.onTextChanged { editable ->
            presenter.cambiarMonto(editable.toString())
        }
    }

    private fun configurarEditTextComentario() {
        editTextComentario?.onTextChanged { editable ->
            presenter.cambiarComentario(editable.toString())
        }
    }

    private fun configurarSpinnerTipoMeta() {
        spinnerMetas?.setOnItemSelectedListener { position ->
            presenter.seleccionarTipoMeta(position)
        }
    }

    private fun configurarSpinnerCampaniaInicio() {
        spinnerCampaniaInicio?.isEnabled = false
        spinnerCampaniaInicio?.setOnItemSelectedListener { position ->
            presenter.seleccionarCampaniaInicio(position)
        }
    }

    private fun configurarSpinnerCampaniaFin() {
        spinnerCampaniaFin?.setOnItemSelectedListener { position ->
            presenter.seleccionarCampaniaFin(position)
        }
    }

    private fun cargarCampaniasLimite() {
        presenter.obtenerCampanias(personaId, rol)
    }

    private fun cargarTiposMetas() {
        presenter.obtenerTipoMetas()
    }

    private fun guardar() {
        presenter.guardar(personaId)
        firebaseAnalyticsPresenter.enviarEventoTagConsultora(
            TagAnalytics.EVENTO_GUARDAR_META_CONSULTORA,
            AnalyticsConstants.GUARDAR_META
        )
    }

    override fun comunicarExito() {
        listenerExito?.alGuardarMetaConExito()
    }

    override fun cerrar() {
        dismiss()
    }

    override fun mostrarTipoMetas(tipoMetas: List<String>) {
        spinnerMetas?.actualizar(tipoMetas)
    }

    override fun mostrarListaCampaniasInicio(campanias: List<String>) {
        if (context == null) return

        spinnerCampaniaInicio?.actualizar(campanias)
    }

    override fun mostrarListaCampaniasFin(campanias: List<String>) {
        if (context == null) return

        spinnerCampaniaFin?.actualizar(campanias)
    }

    override fun habilitarBotonGuardar() {
        btnGuardar?.isEnabled = true
    }

    override fun deshabilitarBotonGuardar() {
        btnGuardar?.isEnabled = false
    }

    interface ListenerExito {
        fun alGuardarMetaConExito()
    }

    companion object {

        private const val ARG_PERSONA_ID = "ARG_PERSONA_ID"
        private const val ARG_ROL_ID = "ARG_ROL"

        fun newInstance(personaId: Long, rol: Rol): CrearMetaConsultoraFragment {
            return CrearMetaConsultoraFragment()
                .withArguments(
                    ARG_PERSONA_ID to personaId,
                    ARG_ROL_ID to rol
                )
        }
    }
}
