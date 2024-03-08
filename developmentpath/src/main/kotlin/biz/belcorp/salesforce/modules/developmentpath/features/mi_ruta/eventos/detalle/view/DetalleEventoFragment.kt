package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.detalle.view

import android.content.*
import android.os.Bundle
import android.view.View
import biz.belcorp.salesforce.core.base.BaseDialogFragment
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.ruta.data.SenderCambioRDD
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.kinesis.KinesisTag
import biz.belcorp.salesforce.modules.developmentpath.features.ConfirmacionDialogFragment
import biz.belcorp.salesforce.modules.developmentpath.features.manager.KinesisManagerPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.AgregarEventoFragment
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.AlertaStringBuilder
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.detalle.presenter.DetalleEventoPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.model.AgregarEventoViewModel
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.presenter.RddPresenter
import kotlinx.android.synthetic.main.fragment_evento_detalle.*

class DetalleEventoFragment : BaseDialogFragment(),
        DetalleEventoView {

    private var eventoXUaId: Long = -1L

    private val eventoPresenter: DetalleEventoPresenter by injectFragment()

    private val presenter: RddPresenter by injectFragment()

    private val kinesisPresenter: KinesisManagerPresenter by injectFragment()

    private val senderCambioRDD: SenderCambioRDD by injectFragment()

    private var alCerrarListener: AlCerrarListener? = null

    private val recargarDetalleEvento = EventoEditadoReceiver()

    private val confirmacionDialogFragment by lazy {
        val titulo = getString(R.string.rdd_eliminarevento_confirmacion)
        val ok = getString(R.string.rdd_confirmacion_ok)
        return@lazy ConfirmacionDialogFragment.newInstance(titulo, ok)
    }

    private val confirmacionRegistrarDialogFragment by lazy {
        val titulo = getString(R.string.rdd_confirmacion_registrar_evento)
        val ok = getString(R.string.rdd_confirmacion_registrar)
        return@lazy ConfirmacionDialogFragment.newInstance(titulo, ok)
    }

    companion object {
        private const val EVENTO_ID = "evento_id"

        fun newInstance(eventoXUaId: Long): DetalleEventoFragment {
            val args = Bundle()
            val fragment = DetalleEventoFragment()

            args.putSerializable(EVENTO_ID, eventoXUaId)
            fragment.arguments = args

            return fragment
        }
    }

    fun conListener(listener: AlCerrarListener): DetalleEventoFragment {
        alCerrarListener = listener

        return this
    }

    override fun getLayout() = R.layout.fragment_evento_detalle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getArgumentos()
    }

    private fun getArgumentos() {
        eventoXUaId = arguments?.getLong(EVENTO_ID) ?: -1L
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.detalleEventoView = this
        registrarReceiver()
        cargarDatos()
        escucharAcciones()
    }

    override fun onStart() {
        super.onStart()
        fitFullScreen()
    }

    override fun onDestroy() {
        activity?.unregisterReceiver(recargarDetalleEvento)
        super.onDestroy()
    }

    override fun onDismiss(dialog: DialogInterface) {
        alCerrarListener?.alCerrarDialogoDetalleEvento()
        super.onDismiss(dialog)
    }

    private fun cargarDatos() {
        eventoPresenter.getEvento(eventoXUaId)
    }

    private fun escucharAcciones() {
        botonEditar?.setOnOneClickListener { lanzarEdicionEvento() }
        botonRetroceder?.setOnOneClickListener { cerrar() }
        layoutEliminarEvento?.setOnOneClickListener { lanzarVentanaConfirmacion() }
        confirmarEvento?.setOnOneClickListener { registrarEvento() }
    }

    private fun lanzarEdicionEvento() {
        AgregarEventoFragment
                .instanciarParaEditar(eventoXUaId)
                .showOnce(childFragmentManager)
    }

    private fun lanzarVentanaConfirmacion() {
        confirmacionDialogFragment.setListener { eventoPresenter.eliminar(eventoXUaId) }
        confirmacionDialogFragment.showOnce(childFragmentManager)
    }

    private fun registrarEvento(){
        confirmacionRegistrarDialogFragment.setListener { eventoPresenter.confirmarEvento(eventoXUaId) }
        confirmacionRegistrarDialogFragment.showOnce(childFragmentManager)
    }

    override fun pintarUbicacion(ubicacion: String) {
        textUbicacionValor?.text = ubicacion
    }

    override fun pintarFecha(fecha: String) {
        textFechaValor?.text = fecha
    }

    override fun pintarTitulo(titulo: String) {
        textTituloEvento?.text = titulo
    }

    override fun pintarTodoElDia() {
        textHoraValor?.text = getString(R.string.rdd_detalleevento_todoeldia)
    }

    override fun pintarIntervalo(horaInicio: String, horafin: String) {
        textHoraValor?.text = getString(R.string.guion_dos_campos, horaInicio, horafin)
    }

    override fun pintarAlerta(cantidad: Int, unidad: AgregarEventoViewModel.Unidad) {
        val alertaStringBuilder = AlertaStringBuilder(context ?: return)
        val alerta = alertaStringBuilder.construir(cantidad, unidad)
        textAlertaValor?.text = alerta
    }

    override fun permitirEditar() {
        botonEditar?.visible()
    }

    override fun prohibirEditar() {
        botonEditar?.gone()
    }

    override fun cerrar() {
        dismiss()
    }

    override fun mostrarToast() {
        toast(getString(R.string.rdd_eliminarevento_mensaje))
    }

    override fun emitirBroadcast() {
        senderCambioRDD.notificarCambioEnPlanificacion()
    }

    override fun permitirEliminar() {
        mostrarControlesEliminacion()
    }

    override fun prohibirEliminar() {
        ocultarControlesEliminacion()
    }

    override fun mostrarComoEliminado(rolMadre: String) {
        layoutEliminado?.visible()
        textEventoEliminado?.text = getString(R.string.rdd_evento_ya_eliminado, rolMadre)
    }

    override fun mostrarMensageRegistrado(confimar: Boolean) {
        if (confimar) layoutRegistrado?.visible()
    }


    override fun mostrarConfirmar() {
        confirmarEvento?.visible()
    }

    override fun eventoRegistrado() {
        confirmarEvento?.gone()
        layoutRegistrado?.visible()
        //kinesisPresenter.enviarPantalla(KinesisTag.PANTALLA_EVENTO)
    }

    override fun actualizarEventos() {
        senderCambioRDD.notificarCambioEnPlanificacion()
    }


    private fun mostrarControlesEliminacion() {
        layoutEliminarEvento?.visible()
    }

    private fun ocultarControlesEliminacion() {
        layoutEliminarEvento?.gone()
    }

    private fun registrarReceiver() {
        val filter = IntentFilter(Constant.BROADCAST_EVENTO_EDITADO)
        activity?.registerReceiver(recargarDetalleEvento, filter)
    }

    inner class EventoEditadoReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            eventoPresenter.getEvento(eventoXUaId)
        }
    }

    interface AlCerrarListener {
        fun alCerrarDialogoDetalleEvento()
    }
}
