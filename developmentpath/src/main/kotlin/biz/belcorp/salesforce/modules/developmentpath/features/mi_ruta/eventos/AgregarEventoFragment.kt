package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos

import android.os.Bundle
import android.view.View
import biz.belcorp.salesforce.core.base.BaseDialogFragment
import biz.belcorp.salesforce.core.domain.exceptions.UnsupportedRoleException
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.common.broadcast.SenderEventoEditado
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.ruta.data.SenderCambioRDD
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.model.AgregarEventoViewModel
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.presenter.AgregarEventoPresenter
import kotlinx.android.synthetic.main.fragment_agregar_evento.*
import biz.belcorp.salesforce.core.utils.toast
import java.util.*


class AgregarEventoFragment : BaseDialogFragment(),
    AgregarEventoView,
    TiemposAlertaFragment.OnItemClickListener,
    OnDateSelectedListener,
    OnHoursSelectedListener {

    enum class Tipo(
        val idTextoBoton: Int,
        val idTextoCabecera: Int,
        val idTextoMensajeExito: Int
    ) {
        CREAR(
            R.string.rdd_agregareventos_guardar,
            R.string.rdd_agregareventos_titulo,
            R.string.rdd_agregareventos_mensaje_exito
        ),
        EDITAR(
            R.string.rdd_agregareventos_guardar_cambios,
            R.string.rdd_agregareventos_editar_titulo,
            R.string.rdd_editarevento_mensaje_exito
        )
    }

    private val agregarEventoPresenter: AgregarEventoPresenter by inject()
    private val senderCambioRDD: SenderCambioRDD by injectFragment()
    private val senderEventoEditado: SenderEventoEditado by injectFragment()
    private var fecha: Date? = null
    private var planId: Long = -1L
    private var eventoId: Long = -1L

    private val tipo: Tipo
        get() {
            return if (eventoId == EVENTO_ID_POR_DEFECTO) {
                Tipo.CREAR
            } else {
                Tipo.EDITAR
            }
        }

    private lateinit var tiemposAlertaFragment: TiemposAlertaFragment
    private lateinit var alertaStringBuilder: AlertaStringBuilder

    override fun getLayout() = R.layout.fragment_agregar_evento

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        planId = arguments!!.getLong(PLAN_ID)
        eventoId = arguments!!.getLong(EVENTO_ID, EVENTO_ID_POR_DEFECTO)
        fecha = arguments!!.getSerializable(FECHA) as? Date
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configurarUI()
        instanciarTiemposAlertaFragment() ?: return
        instanciarAlertaStringBuilder() ?: return

        agregarEventoPresenter.view = this
        configurarFechaInicial()
        cargarDatos()
    }

    private fun configurarFechaInicial() {
        if (tipo == Tipo.CREAR) {
            agregarEventoPresenter.configurarFechaInicial(fecha ?: Date())
        }
    }

    private fun cargarDatos() {
        when (tipo) {
            Tipo.CREAR -> agregarEventoPresenter.cargarDatosParaCreacion(planId)
            Tipo.EDITAR -> agregarEventoPresenter.cargarDatosParaEdicion(eventoId)
        }
    }

    private fun configurarUI() {
        configurarBotonRetroceder()
        configurarTituloBarra()
        configurarSpinner()
        configurarEditTextDescripcion()
        configurarSwitchTodoElDia()
        configurarEditTextUbicacion()
        configurarLayoutFecha(Date())
        configurarLayoutHora(Date(), Date())
        configurarLayoutSeleccionarAlerta()
        configurarCheckboxCompartir()
        configurarTituloBotonGuadar()
        configurarAccionBotonGuardar()
        configurarBotonCancelar()
    }

    private fun configurarBotonRetroceder() {
        botonRetroceder?.setOnClickListener { dismiss() }
    }

    private fun configurarBotonCancelar() {
        if (tipo == Tipo.EDITAR) {
            botonCancerlar?.toggleVisibility()
            botonCancerlar?.underline()
            botonCancerlar?.setOnClickListener { dismiss() }
        }
    }

    private fun configurarTituloBarra() {
        tituloCabecera?.text = getString(tipo.idTextoCabecera)
    }

    private fun configurarSpinner() {
        spinnerTiposEvento?.setOnItemSelectedListener { pos ->
            agregarEventoPresenter.seleccionarTipoEvento(pos)
        }
    }

    private fun configurarEditTextDescripcion() {
        editTextTituloEvento?.onTextChanged {
            agregarEventoPresenter.cambiarDescripcionPersonalizada(
                editTextTituloEvento?.text.toString()
            )
        }
    }

    private fun configurarSwitchTodoElDia() {
        switchEventoTodoElDia?.setOnCheckedChangeListener { _, isChecked ->
            agregarEventoPresenter.cambiarValorSwitchTodoElDia(isChecked)
        }
    }

    private fun configurarEditTextUbicacion() {
        editTextUbicacion?.onTextChanged {
            agregarEventoPresenter.cambiarUbicacion(
                editTextUbicacion?.text.toString()
            )
        }
    }

    private fun configurarLayoutFecha(fecha: Date) {
        layoutFecha?.setOnOneClickListener {
            val date = FechaPickerEventoDialog.DialogBuilder(requireContext())
                .setInitalDate(fecha)
                .setListener(this)
                .build()
            date.showDialog()
        }
    }

    private fun configurarLayoutHora(inicio: Date, fin: Date) {
        layoutHora?.setOnOneClickListener {
            val date = HoraPickerEventoDialog.DialogBuilder(requireContext())
                .setStartDate(inicio)
                .setEndDate(fin)
                .setListener(this)
                .build()
            date.showDialog()
        }
    }

    private fun configurarLayoutSeleccionarAlerta() {
        layoutSeleccionarAlerta?.setOnClickListener {
            tiemposAlertaFragment.show(childFragmentManager, "tiempos")
        }
    }

    private fun configurarTituloBotonGuadar() {
        botonGuardarEvento?.text = getString(tipo.idTextoBoton)
    }

    private fun configurarAccionBotonGuardar() {
        botonGuardarEvento?.setOnOneClickListener {
            alDarClickGuardarEditar()
        }
    }

    private fun alDarClickGuardarEditar() {
        when (tipo) {
            Tipo.CREAR -> agregarEventoPresenter.guardar()
            Tipo.EDITAR -> agregarEventoPresenter.editar()
        }
    }

    private fun configurarCheckboxCompartir() {
        checkboxCompartir?.setOnCheckedChangeListener { _, checked ->
            agregarEventoPresenter.cambiarValorCheckboxCompartir(checked)
        }
        layoutCompartir?.setOnClickListener {
            checkboxCompartir?.isChecked = !(checkboxCompartir.isChecked)
        }
    }

    private fun instanciarTiemposAlertaFragment(): TiemposAlertaFragment? {
        tiemposAlertaFragment = TiemposAlertaFragment()
            .configurar(context ?: return null)
            .agregarListener(this)

        return tiemposAlertaFragment
    }

    private fun instanciarAlertaStringBuilder(): AlertaStringBuilder? {
        alertaStringBuilder = AlertaStringBuilder(context ?: return null)

        return alertaStringBuilder
    }

    override fun onStart() {
        super.onStart()
        fitFullScreen()
    }

    override fun pintarTiposEvento(tiposEvento: List<String>) {
        spinnerTiposEvento?.adapter = RddArrayAdapter(
            context = context ?: return,
            strings = tiposEvento
        )

        spinnerTiposEvento?.posicionarDialogoDebajo()
    }

    override fun pintarTipoEventoSeleccionado(indice: Int) {
        spinnerTiposEvento?.setSelection(indice)
    }

    override fun pintarDescripcionPersonalizada(descripcion: String) {
        editTextTituloEvento?.setText(descripcion)
    }

    override fun activarSwitchTodoElDia() {
        switchEventoTodoElDia?.checked()
    }

    override fun desactivarSwitchTodoElDia() {
        switchEventoTodoElDia?.unchecked()
    }

    override fun pintarTextoAvisoCompartir(rol: Rol) {
        textViewEventoCompartido?.text = when (rol) {
            Rol.SOCIA_EMPRESARIA -> ""
            Rol.GERENTE_ZONA -> getString(R.string.rdd_agregareventos_aviso_socias)
            Rol.GERENTE_REGION -> getString(R.string.rdd_agregareventos_aviso_gzs)
            Rol.DIRECTOR_VENTAS -> getString(R.string.rdd_agregareventos_aviso_grs)
            else -> throw UnsupportedRoleException(rol)
        }
    }

    override fun pintarFecha(fecha: String) {
        textViewFecha?.text = fecha
    }

    override fun configurarFecha(fecha: Date) {
        configurarLayoutFecha(fecha)
    }

    override fun pintarUbicacion(ubicacion: String) {
        editTextUbicacion?.setText(ubicacion)
    }

    override fun mostrarHoras() {
        layoutHora?.visible()
    }

    override fun ocultarHoras() {
        layoutHora?.gone()
    }

    override fun pintarHoras(horas: String) {
        textViewHora?.text = horas
    }

    override fun configurarHoras(inicio: Date, fin: Date) {
        configurarLayoutHora(inicio, fin)
    }

    override fun pintarTiemposAlerta(alertas: List<AgregarEventoViewModel.Alerta>) {
        tiemposAlertaFragment.actualizar(alertas)
    }

    override fun pintarTiempoAlertaSeleccionado(alerta: AgregarEventoViewModel.Alerta) {
        textViewAlertaSeleccionada?.text = alertaStringBuilder.construir(
            alerta.cantidad,
            alerta.unidad
        )
    }

    override fun mostrarDescripcionPersonalizada() {
        grupoTituloEvento?.visible()
        editTextTituloEvento?.visible()
        separadorTituloEvento?.visible()
    }

    override fun ocultarDescripcionPersonalizada() {
        grupoTituloEvento?.gone()
        editTextTituloEvento?.gone()
        separadorTituloEvento?.gone()
    }

    override fun mostrarAvisoCompartir() {
        layoutAvisoEventoCompartido?.visible()
    }

    override fun ocultarAvisoCompartir() {
        layoutAvisoEventoCompartido?.gone()
    }

    override fun pintarTextoCheckboxCompartir(rol: Rol) {
        textViewCompartir?.text = when (rol) {
            Rol.SOCIA_EMPRESARIA -> Constant.EMPTY_STRING
            Rol.GERENTE_ZONA -> getString(R.string.rdd_agregareventos_compartircheckboxmensaje_socias)
            Rol.GERENTE_REGION -> getString(R.string.rdd_agregareventos_compartircheckboxmensaje_gzs)
            Rol.DIRECTOR_VENTAS -> getString(R.string.rdd_agregareventos_compartircheckboxmensaje_grs)
            else -> throw UnsupportedRoleException(rol)
        }
    }

    override fun mostrarLayoutCompartir() {
        layoutCompartir?.visible()
    }

    override fun ocultarLayoutCompartir() {
        layoutCompartir?.gone()
    }

    override fun activarCompartir() {
        checkboxCompartir?.checked()
    }

    override fun desactivarCompartir() {
        checkboxCompartir?.unchecked()
    }

    override fun onDateSelected(date: Date) {
        agregarEventoPresenter.seleccionarFecha(fecha = date)
    }

    override fun onDatesSelected(startDate: Date, endDate: Date) {
        agregarEventoPresenter.seleccionarHora(startDate, endDate)
    }

    override fun alClickearItemTiempoAlerta(pos: Int) {
        agregarEventoPresenter.seleccionarTiempoAlerta(pos)
    }

    override fun emitirBroadcastEventoEditado() {
        senderEventoEditado.notificarEventoEditado()
    }

    override fun mostrarMensajeExito() {
        toast(tipo.idTextoMensajeExito)
    }

    override fun habilitarBotonGuardar() {
        botonGuardarEvento?.isEnabled = true
    }

    override fun deshabilitarBotonGuardar() {
        botonGuardarEvento?.isEnabled = false
    }

    override fun mostrarErrorGenerico() {
        toast(R.string.rdd_agregareventos_error_generico)
    }

    override fun mostrarErrorFechaFueraDePeriodo() {
        toast(R.string.rdd_agregareventos_error_fecha_fuera_periodo)
    }

    override fun mostrarErrorFechaAntesAHoy() {
        toast(R.string.rdd_agregareventos_error_fecha_antes_hoy)
    }

    override fun mostrarErrorHoraInicioMayorAFin() {
        toast(R.string.rdd_agregareventos_error_hora_inicio_mayor_fin)
    }

    override fun emitirBroadcast() {
        senderCambioRDD.notificarCambioEnPlanificacion()
    }

    override fun cerrar() {
        dismiss()
    }

    companion object {
        private const val PLAN_ID = "PLAN_ID"
        private const val EVENTO_ID = "EVENTO_ID"
        private const val FECHA = "FECHA"

        private const val EVENTO_ID_POR_DEFECTO = -1L

        fun instanciarParaCrear(
            planId: Long,
            fecha: Date = Date()
        ): AgregarEventoFragment {
            val args = Bundle()
            val fragment = AgregarEventoFragment()

            args.putLong(PLAN_ID, planId)
            args.putSerializable(FECHA, fecha)
            fragment.arguments = args

            return fragment
        }

        fun instanciarParaEditar(eventoXUaId: Long): AgregarEventoFragment {
            val args = Bundle()
            val fragment = AgregarEventoFragment()

            args.putLong(EVENTO_ID, eventoXUaId)
            fragment.arguments = args

            return fragment
        }
    }
}
