package biz.belcorp.salesforce.modules.developmentpath.features.focos

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import biz.belcorp.salesforce.core.base.BaseDialogFragment
import biz.belcorp.salesforce.core.utils.NonScrollableLayoutManager
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.core.utils.toast
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.common.broadcast.SenderRecargarFocos
import biz.belcorp.salesforce.modules.developmentpath.common.broadcast.SenderRecargarMisFocos
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.focos.FocoSeleccionado
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.FirebaseAnalyticsPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.focos.model.FocoModel
import biz.belcorp.salesforce.modules.developmentpath.features.focos.model.PersonaModel
import biz.belcorp.salesforce.modules.developmentpath.features.focos.presenter.AsignarFocosPresenter
import kotlinx.android.synthetic.main.fragment_dialog_asignacion_focos.*
import java.io.Serializable

class AsignarFragment : BaseDialogFragment(), AsignarView {

    private val presenter: AsignarFocosPresenter by injectFragment()

    private val senderRecargarFocos: SenderRecargarFocos by injectFragment()

    private val senderRecargarMisFocos: SenderRecargarMisFocos by injectFragment()

    private val firebaseAnalyticsPresenter: FirebaseAnalyticsPresenter by injectFragment()

    private lateinit var request: Request
    private lateinit var event: TagAnalytics
    private val focosAdapter get() = rv_focos?.adapter as? AsignarFocosAdapter
    private val personasAdapter get() = rv_personas?.adapter as? AsignarPersonasAdapter
    var valClick: Boolean = false
    var iseleccionFocos: Boolean = false
    lateinit var foco: FocoSeleccionado
    lateinit var codigo: FocoSeleccionado

    companion object {
        private const val REQUEST_ASIGNAR = "REQUEST_ASIGNAR"
        private const val TAG_EVENT = "TAG_EVENT"

        fun newInstance(request: Request, event: TagAnalytics): AsignarFragment {
            val args = Bundle()
            val fragment = AsignarFragment()
            args.putSerializable(REQUEST_ASIGNAR, request)
            args.putSerializable(TAG_EVENT, event)
            fragment.arguments = args

            return fragment
        }
    }

    override fun getLayout(): Int {
        return R.layout.fragment_dialog_asignacion_focos
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        request = arguments!!.get(REQUEST_ASIGNAR) as Request
        event = arguments!!.getSerializable(TAG_EVENT) as TagAnalytics
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fitFullScreen()

        btn_cerrar?.setOnClickListener { cerrar() }
        btnConfirmar?.setOnClickListener { guardarValidar() }

        configurarRecyclerFocos()
        configurarRecyclerPersonas()

        presenter.view = this

        obtenerDatos()
    }

    private fun configurarRecyclerPersonas() {
        rv_personas?.layoutManager = NonScrollableLayoutManager()
                .withContext(context)
                .linearVertical()

        rv_personas?.adapter = AsignarPersonasAdapter(requireContext()).apply {
            establecerClickEnSociaListener { posicion ->
                iseleccionFocos = false
                valClick = true
                presenter.seleccionarSocia(posicion)
            }
        }
    }


    private fun configurarRecyclerFocos() {
        rv_focos?.layoutManager = NonScrollableLayoutManager()
                .withContext(context)
                .linearVertical()

        rv_focos?.adapter = AsignarFocosAdapter(requireContext()).apply {
            establecerClickEnFocoListener { posicion ->
                iseleccionFocos = true
                valClick = true
                establecerClickFocos(posicion)
            }
        }
    }

    private fun obtenerDatos() {
        val request = request
        when (request) {
            is Request.EditarTodos -> presenter.obtenerTodo()
            is Request.EditarUno -> presenter.obtenerParaPersona(request.personaId)
            is Request.EditarPropio -> presenter.obtnerFocosPropio()
        }
    }

    private fun validarRequest() {
        val rol: String = presenter.obtenerRol().toUpperCase()
        if (valClick && rol == Constant.GR_CODIGO_ROL) {
            when (request) {
                is Request.EditarTodos -> {
                    if (iseleccionFocos) {
                        pintarSeleccionFocos()
                    } else {
                        pintarSeleccionCodigo()
                    }
                }
                is Request.EditarUno, is Request.EditarPropio -> {
                    foco.tipo = Constant.EVENTO_EDITAR_FOCOS
                    firebaseAnalyticsPresenter.enviarFocosSeleccionado(event, foco)
                }
            }
        }
        valClick = false
    }

    private fun guardarValidar() {
        val request = request
        when (request) {
            is Request.EditarPropio -> presenter.guardarFocosPropio()
            else -> presenter.guardar()
        }
    }

    private fun establecerClickFocos(posicion: Int) {
        val request = request
        when (request) {
            is Request.EditarPropio -> presenter.seleccionarFocoPropio(posicion)
            else -> presenter.seleccionarFoco(posicion)
        }
    }

    override fun onStart() {
        super.onStart()
        ajustarTamanioAPantalla()
    }

    private fun ajustarTamanioAPantalla() {
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.MATCH_PARENT
        dialog?.window?.setLayout(width, height)
    }

    private fun pintarSeleccionFocos() {
        event = TagAnalytics.EVENTO_SELECCION_MI_EQUIPO_MULTIPLE
        enviarFocoAnalytics(event)
    }

    private fun pintarSeleccionCodigo() {
        event = TagAnalytics.EVENTO_SELECCION_CODIGO_MI_EQUIPO_MULTIPLE
        enviarFocoAnalytics(event)
    }

    private fun enviarFocoAnalytics(event: TagAnalytics) {
        if (foco.focos.isNotEmpty()) {
            firebaseAnalyticsPresenter.enviarFocosSeleccionado(event, foco)
        }
    }

    override fun mostrarTituloFocosPropios() {
        tv_titulo_focos?.visibility = View.VISIBLE
        tv_titulo_focos?.text = getText(R.string.rdd_asignacion_focos_titulo_focos_propios)
    }

    override fun mostrarTituloFocosHijos() {
        tv_titulo_focos?.visibility = View.VISIBLE
        tv_titulo_focos?.text = getText(R.string.rdd_asignacion_focos_titulo_focos)
    }

    override fun ocultarTituloFocos() {
        tv_titulo_focos?.visibility = View.GONE
    }

    override fun mostrarTituloPersonas() {
        tv_titulo_personas?.visibility = View.VISIBLE
    }

    override fun ocultarTituloPersonas() {
        tv_titulo_personas?.visibility = View.GONE
    }

    override fun cargarFocos(focos: List<FocoModel>) {
        focosAdapter?.actualizar(focos)
    }

    override fun focoSeleccionado(foco: FocoSeleccionado) {
        this@AsignarFragment.foco = foco
    }

    override fun codigoSeleccionado(codigo: FocoSeleccionado) {
        this@AsignarFragment.codigo = codigo
        validarRequest()
    }

    override fun cargarPersonas(personas: List<PersonaModel>) {
        personasAdapter?.actualizar(personas)
    }

    override fun notificarActualizacionEnFocosDeHijas() {
        senderRecargarFocos.recargar()
    }

    override fun notificarActualizacionEnMisFocos() {
        senderRecargarMisFocos.recargar()
    }

    override fun habilitarBotonGuardado() {
        btnConfirmar?.isEnabled = true
    }

    override fun deshabilitarBotonGuardado() {
        btnConfirmar?.isEnabled = false
    }

    override fun mostrarMensaje(mensaje: String) {
        toast(mensaje)
    }

    override fun cerrar() {
        dismiss()
    }

    sealed class Request : Serializable {
        class EditarTodos : Request(), Serializable
        class EditarUno(val personaId: Long) : Request(), Serializable
        class EditarPropio : Request(), Serializable
    }
}
