package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.registrar

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import biz.belcorp.salesforce.core.base.BaseDialogFragment
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.common.broadcast.SenderRegistroVisitaExitoso
import biz.belcorp.salesforce.modules.developmentpath.common.broadcast.SenderValorIndicador
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.ruta.data.SenderCambioRDD
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.FirebaseAnalyticsPresenter
import biz.belcorp.salesforce.modules.developmentpath.utils.conPrimeraLetraMayus
import kotlinx.android.synthetic.main.fragment_registrar_visita.*
import biz.belcorp.salesforce.core.utils.toast
import java.util.*
import biz.belcorp.salesforce.components.R as ComponentR

class RegistrarVisitaFragment : BaseDialogFragment(),
        RegistrarView,
        AcuerdosView,
        RegistrarVisitaSuccessFragment.Listener,
        AcuerdoAdapter.OnItemClickListener {

    private lateinit var date: Date
    private var visitaId = -1L

    private val acuerdoAdapter by lazy {
        AcuerdoAdapter(context = requireContext(),
                listener = this)
    }


    private val registrarPresenter: RegistrarPresenter by injectFragment()

    private val senderCambioRDD: SenderCambioRDD by injectFragment()

    private val senderRegistroVisitaExitoso: SenderRegistroVisitaExitoso by injectFragment()

    private val senderNuevosIndicadores: SenderValorIndicador by injectFragment()

    private val firebaseAnalyticsPresenter: FirebaseAnalyticsPresenter by injectFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        visitaId = arguments!!.getLong(ID_VISITA)
    }

    override fun getLayout(): Int {
        return R.layout.fragment_registrar_visita
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        establecerFechaEnVista()
        configurarComponentesUI()
        configurarRegistrarPresenter()

        registrarPresenter.cargarDatosIniciales(visitaId)
    }

    private fun configurarRegistrarPresenter() {
        registrarPresenter.registrarView = this
        registrarPresenter.acuerdosView = this

    }

    override fun onDestroyView() {
        super.onDestroyView()
        registrarPresenter.destroy()
    }

    override fun onStart() {
        super.onStart()
        fitFullScreen()
    }

    private fun scrollearAAcuerdoDiferido() {
        val delayMills: Long = 200

        Handler().postDelayed({ scrollearAAcuerdo() }, delayMills)
    }

    private fun scrollearAAcuerdo() {
        val toPos: Int = rl_nueva_acuerdo?.top ?: 0
        scrollview?.smoothScrollTo(0, toPos)
    }

    private fun configurarComponentesUI() {
        btn_back?.setOnClickListener { this.dismiss() }
        configurarRecyclerAcuerdos()
        configurarSwitchPasaPedido()
        configurarEditTextNuevoAcuerdo()
        configurarBotonNuevoAcuerdo()
        configurarBotonRegistrarVisita()
        configurarBotonCancelarNuevoAcuerdo()
        btn_guardar_ac?.setOnClickListener { crearAcuerdo() }
        configurarEditTextComentarioAcuerdo()
    }

    private fun configurarRecyclerAcuerdos() {
        list_acuerdos?.layoutManager = NonScrollableLayoutManager()
                .withContext(context)
                .linearVertical()
        list_acuerdos?.adapter = acuerdoAdapter
    }

    private fun configurarSwitchPasaPedido() {
        switchPasaPedido?.onSwitch { isChecked ->
            registrarPresenter.cambiarEstadoSwitch(isChecked)
        }
    }

    private fun configurarEditTextNuevoAcuerdo() {
        et_comentario_acuerdo?.setOnClickListener { scrollearAAcuerdoDiferido() }

        et_comentario_acuerdo?.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) return@setOnFocusChangeListener
            scrollearAAcuerdoDiferido()
        }

        et_comentario_acuerdo?.onTextChanged { scrollearAAcuerdo() }
    }

    private fun configurarBotonRegistrarVisita() {
        button_registrar_visita?.setOnClickListener {
            verificarSiGuardarAcuerdo()
            registrarPresenter.registrar(visitaId)
        }
    }

    private fun verificarSiGuardarAcuerdo() {
        if (et_comentario_acuerdo?.text.toString().isNotBlank()) {
            registrarPresenter.crearAcuerdo(visitaId, et_comentario_acuerdo.text.toString())
        }
    }

    private fun configurarBotonNuevoAcuerdo() {
        layoutNuevoAcuerdo?.setOnClickListener {
            rl_nueva_acuerdo?.visibility = View.VISIBLE
            et_comentario_acuerdo?.requestFocus()
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(et_comentario_acuerdo, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    private fun configurarBotonCancelarNuevoAcuerdo() {
        btn_cancelar_ac?.setOnClickListener {
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(et_comentario_acuerdo?.windowToken, 0)
            rl_nueva_acuerdo?.visibility = View.GONE
            et_comentario_acuerdo?.setText("")
        }
    }

    private fun crearAcuerdo() {
        if (et_comentario_acuerdo?.text.toString().isBlank()) {
            til_comentario?.error = "Debe agregar un acuerdo."
            return
        }
        taggearEnAnalytics()
        registrarPresenter.crearAcuerdo(visitaId, et_comentario_acuerdo.text.toString())
    }

    private fun configurarEditTextComentarioAcuerdo() {
        et_comentario_acuerdo?.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                crearAcuerdo()
            }

            return@setOnEditorActionListener false
        }
    }

    private fun taggearEnAnalytics() {
        firebaseAnalyticsPresenter.enviarEvento(TagAnalytics.EVENTO_GUARDAR_ACUERDO)
    }

    override fun habilitarBotonRegistro() {
        button_registrar_visita?.isEnabled = true
    }

    override fun deshabilitarBotonRegistro() {
        button_registrar_visita?.isEnabled = false
    }

    override fun cerrar() {
        dialog?.hide()
    }

    override fun mostrarExitoRegistro() {
        if (!isResumed) return

        RegistrarVisitaSuccessFragment
                .newInstance()
                .apply { listener = this@RegistrarVisitaFragment }
                .show(childFragmentManager, "ReprogramarExito")
    }

    private fun establecerFechaEnVista() {
        date = Date()
        txt_fecha_visita?.text = date.parseToDateString().conPrimeraLetraMayus()
        txt_hora_visita?.text = String.format(getString(R.string.text_hora_visita),
                date.parseToHoursDescription())
    }

    override fun alCerrarDialogoDeExito() {
        senderRegistroVisitaExitoso.notificarRegistroVisita()
        safeDismiss()
    }

    override fun notificarCambioEnPlanificacion() {
        senderCambioRDD.notificarCambioEnPlanificacion()
    }

    override fun mostrarError(mensaje: String) {
        toast(mensaje)
    }

    override fun alRecuperarIndicadores(realizadas: Int, planificadas: Int) {
        senderNuevosIndicadores.enviarNuevosValores(realizadas, planificadas)
    }

    override fun mostrarSwitch() {
        grupoSwitchPasaPedido?.visibility = View.VISIBLE
    }

    override fun ocultarSwitch() {
        grupoSwitchPasaPedido?.visibility = View.GONE
    }

    override fun pintarNombreEnSwitch(nombre: String) {
        if (context == null) return

        val texto = getString(R.string.mi_ruta_registrar_visita_descripcion_pasa_pedido, nombre)

        textViewTituloPasaPedido?.text = FontStyler
                .establecerTexto(texto)
                .conContext(requireContext())
                .conDelimitador("|")
                .conFuentePrimaria(TipoFuente.MULISH_BOLD)
                .conFuenteSecundaria(TipoFuente.MULISH_LIGHT)
            .conColorPrimarioDesdeRecurso(ComponentR.color.colorButtonVariant)
                .procesar()
    }

    override fun habilitarSwitch() {
        switchPasaPedido?.checked()
    }

    override fun deshabilitarSwitch() {
        switchPasaPedido?.noChecked()
    }

    override fun habilitarCreacionAcuerdos() {
        ll_consultant_acuerdos?.visibility = View.VISIBLE
    }

    override fun deshabilitarCreacionAcuerdos() {
        ll_consultant_acuerdos?.visibility = View.GONE
    }

    override fun onItemDeleteAcuerdo(posicion: Int) {
        registrarPresenter.eliminarAcuerdo(posicion)
    }

    override fun mostrarNuevoAcuerdo(acuerdoModel: AcuerdoModel) {
        et_comentario_acuerdo?.text?.clear()
        acuerdoAdapter.agregarAcuerdo(acuerdoModel)
    }

    override fun quitarAcuerdoEliminado(posicion: Int) {
        acuerdoAdapter.eliminarAcuerdo(posicion)
    }

    override fun ocultarContenedorNuevoAcuerdo() {
        rl_nueva_acuerdo?.visibility = View.GONE
    }

    override fun limpiarCampoError() {
        til_comentario?.error = null
    }

    override fun ocultarTeclado() {
        btn_guardar_ac?.dismissKeyboard()
    }

    companion object {

        private const val ID_VISITA = "ID_VISITA"

        fun newInstance(visitaId: Long): RegistrarVisitaFragment {
            val args = Bundle()
            val fragment = RegistrarVisitaFragment()
            args.putLong(ID_VISITA, visitaId)
            fragment.arguments = args
            return fragment
        }
    }
}
