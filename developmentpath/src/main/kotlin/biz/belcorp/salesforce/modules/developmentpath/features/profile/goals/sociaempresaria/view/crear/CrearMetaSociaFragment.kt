package biz.belcorp.salesforce.modules.developmentpath.features.profile.goals.sociaempresaria.view.crear

import android.os.Bundle
import android.view.View
import biz.belcorp.salesforce.components.exported.sharedToolbarLight
import biz.belcorp.salesforce.core.base.BaseToolbarDialogFragment
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.profile.goals.sociaempresaria.model.MetaSociaModelo
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.android.synthetic.main.fragment_meta_socia.*

class CrearMetaSociaFragment : BaseToolbarDialogFragment(), CrearMetaSociaView {

    private var personaId = -1L
    private var rol = Rol.NINGUNO

    private val metaSociaPresenter by injectFragment<MetaSociaPresenter>()
    var meta: MetaSociaModelo? = null
    var listener: () -> Unit = {}

    override fun getLayout() = R.layout.fragment_meta_socia

    override fun getToolbar(): MaterialToolbar? = sharedToolbarLight

    override fun getTitle(): String? = resources.getString(R.string.crear_meta_consultora_titulo)

    override fun getMode(): Mode = Mode.CLOSEABLE

    override fun onStart() {
        super.onStart()
        obtenerFocoEnDescripcion()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        obtenerArgumentos()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iniciarVista()
    }

    override fun habilitarBotonGuardar() {
        botonGuardarMeta?.isEnabled = true
    }

    override fun deshabilitarBotonGuardar() {
        botonGuardarMeta?.isEnabled = false
    }

    private fun obtenerArgumentos() {
        arguments?.let {
            personaId = it.getLong(PARAM_PERSONA_ID)
            rol = it.getSerializable(PARAM_ROL) as Rol
        }
    }

    override fun cerrarVentana() {
        dismiss()
    }

    override fun mostrarMensajeErrorAlCrear() {
        toast(getString(R.string.mensaje_error_guardar_meta))
    }

    override fun mostrarMensajeErrorAlEditar() {
        toast(getString(R.string.mensa_error_editar_meta))
    }

    override fun limpiarDescripcion() {
        edtDescripcionMeta.setText("")
    }

    override fun asignarDescripcion(descripcion: String) {
        edtDescripcionMeta.setText(descripcion)
    }

    override fun cargarDatosMeta(modelo: MetaSociaModelo) {
        edtDescripcionMeta?.setText(modelo.descripcion)
        edtDescripcionMeta?.setSelection(modelo.descripcion.length)
    }

    override fun notifcarExito() {
        listener.invoke()
    }

    private fun iniciarVista() {
        configurarDescripcionMeta()
        configurarBotonGuardarNota()
        configurarModoEdicion()
    }

    private fun configurarModoEdicion() {
        meta?.run {
            metaSociaPresenter.asignarModelo(this)
            metaSociaPresenter.cargarDatosEdicion()
        }
    }

    private fun configurarBotonGuardarNota() {
        botonGuardarMeta?.setOnClickListener {
            metaSociaPresenter.realizarOperacion(personaId, rol)
        }
    }

    private fun configurarDescripcionMeta() {
        edtDescripcionMeta?.onTextChanged {
            metaSociaPresenter.asignarDescripcion(it.toString())
        }
    }

    private fun obtenerFocoEnDescripcion() {
        edtDescripcionMeta?.requestFocus()
        context?.let {
            showKeyboard(it, edtDescripcionMeta)
        }
    }

    companion object {

        private const val PARAM_PERSONA_ID = "personaId"
        private const val PARAM_ROL = "rol"

        fun newInstance(personaId: Long, rol: Rol): CrearMetaSociaFragment {
            return CrearMetaSociaFragment()
                .withArguments(
                    PARAM_PERSONA_ID to personaId,
                    PARAM_ROL to rol
                )
        }
    }
}
