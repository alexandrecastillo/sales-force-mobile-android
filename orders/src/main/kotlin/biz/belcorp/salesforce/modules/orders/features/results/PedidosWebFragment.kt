package biz.belcorp.salesforce.modules.orders.features.results

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import biz.belcorp.salesforce.analytics.core.domain.entities.ScreenTag
import biz.belcorp.salesforce.analytics.features.trackAnalytics
import biz.belcorp.salesforce.core.base.BaseDialogFragment
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.features.utils.PERSON_IDENTIFIER_KEY
import biz.belcorp.salesforce.core.include.Include
import biz.belcorp.salesforce.core.include.IncludeManager
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.orders.R
import biz.belcorp.salesforce.modules.orders.features.results.adapters.PedidosWebResultadosAdapter
import biz.belcorp.salesforce.modules.orders.features.results.dialogs.LockOrderWebDialog
import biz.belcorp.salesforce.modules.orders.features.results.dialogs.OrderWebDialog
import biz.belcorp.salesforce.modules.orders.features.results.dialogs.OrderWebErrorDialog
import biz.belcorp.salesforce.modules.orders.features.results.dialogs.UnLockOrderWebDialog
import biz.belcorp.salesforce.modules.orders.features.results.model.ResultadoItemPedidosWebModel
import biz.belcorp.salesforce.modules.orders.features.results.utils.PedidosMasOpciones
import biz.belcorp.salesforce.modules.orders.features.search.FiltrosPedidosWebFragment
import biz.belcorp.salesforce.modules.orders.features.search.model.FiltroPedidosWebModel
import biz.belcorp.salesforce.modules.orders.utils.AnalyticUtils
import kotlinx.android.synthetic.main.fragment_pedidos_web.*
import org.koin.android.ext.android.inject

class PedidosWebFragment : BaseFragment(), PedidosWebView, PedidosMasOpciones.AccionesListener {

    private val pedidosWebPresenter: PedidosWebPresenter by injectFragment()
    private val includeManager by inject<IncludeManager>()

    private val resultadosAdapter by lazy { PedidosWebResultadosAdapter(this) }
    private val filtrosFragment by lazy { FiltrosPedidosWebFragment.newInstance() }
    private var filtrosModel: FiltroPedidosWebModel? = null

    override fun getLayout(): Int = R.layout.fragment_pedidos_web

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iniciarEventos()
        configurarRecyclerView()
        pedidosWebPresenter.obtenerDatosConfiguracion()
    }

    override fun onResume() {
        super.onResume()
        sendAnalytics()
    }

    private fun sendAnalytics() {
        AnalyticUtils.screen()
        trackAnalytics(ScreenTag.WEB_PED)
    }

    private fun iniciarEventos() {
        btnFiltros?.setOnClickListener {
            abrirFiltros()
        }
        rlHeaderLock?.setOnClickListener {
            cerrarOpciones()
        }
    }

    private fun configurarRecyclerView() {
        rvPedidoWeb?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = resultadosAdapter
        }
    }

    private fun abrirFiltros() {
        filtrosFragment.escucharFiltrosAplicados { model, aplicar ->
            actualizarFiltros(model, aplicar)
        }
        filtrosFragment.mostrarFiltrosActuales(filtrosModel)
        childFragmentManager.beginTransaction().apply {
            setCustomAnimations(
                R.anim.enter_from_bottom_to_top,
                R.anim.exit_from_top_to_bottom,
                R.anim.pop_enter,
                R.anim.pop_exit
            )
            replace(R.id.containerFiltros, filtrosFragment)
            addToBackStack(null)
        }.commit()
        containerFiltros.visible()
    }

    private fun actualizarFiltros(filtros: FiltroPedidosWebModel, aplicar: Boolean) {
        filtrosModel = filtros
        if (aplicar) {
            resultadosAdapter.setHighlightName(filtros.nombreConsultora)
            pedidosWebPresenter.buscarPedidos(filtros)
        }
    }

    override fun configurarAdapter(campania: String, rol: Rol, bloqueoActivado: Int) {
        resultadosAdapter.bloqueoActivado = bloqueoActivado
        resultadosAdapter.campaniaActual = campania
        resultadosAdapter.rol = rol
    }

    override fun mostrarCampaniaVenta(nombreCorto: String) {
        tvCampania?.text = getString(R.string.campania_tipo_v, nombreCorto)
    }

    override fun mostrarCampaniaFacturacion(nombreCorto: String) {
        tvCampania?.text = getString(R.string.campania_tipo_f, nombreCorto)
    }

    override fun mostrarBloqueoDialog(f: () -> Unit) {
        val dialog = LockOrderWebDialog(object : OrderWebDialog.OrderWebDialogClick {
            override fun onClick() {
                cerrarOpciones()
                f.invoke()
            }
        })
        activity?.supportFragmentManager?.also { dialog.show(it, "") }
    }

    override fun mostrarDesloqueoDialog(f: () -> Unit) {
        val dialog = UnLockOrderWebDialog(object : OrderWebDialog.OrderWebDialogClick {
            override fun onClick() {
                cerrarOpciones()
                f.invoke()
            }
        })
        activity?.supportFragmentManager?.also { dialog.show(it, "") }
    }

    override fun actualizarBloqueo(pedidoId: Int, bloqueado: Int) {
        resultadosAdapter.actualizarBloqueo(pedidoId, bloqueado)
    }

    override fun ocultarLoading() {
        progressBar?.gone()
    }

    override fun mostrarLoading() {
        progressBar?.visible()
    }

    override fun ocultarResultados() {
        tvMensaje?.gone()
        tvTotal?.gone()
        tvTotalMontoMinimo?.gone()
        resultadosAdapter.clear()
    }

    override fun mostrarResultadosEncontrados() {
        ablLayout?.setExpanded(true)
        tvMensaje?.setText(R.string.pedido_mensaje_resultados)
        tvMensaje?.setTextColor(requireContext().getCompatColor(R.color.color_pedidos_web))
        tvMensaje?.visible()
    }

    override fun mostrarResultadosNoEncontrados() {
        ablLayout?.setExpanded(true)
        val mensaje = getString(R.string.pedido_mensaje_no_resultados)
        mostrarMensajeError(mensaje)
    }

    override fun mostrarTotalResultados(total: Int) {
        tvTotal?.text = getString(R.string.resultados_total, total)
        tvTotal?.visible()
    }

    override fun mostrarTotalResultadosMontoMinimo(totalMontoMinimo: Int) {
        tvTotalMontoMinimo?.text =
            getString(R.string.resultados_total_monto_minimo, totalMontoMinimo)
        tvTotalMontoMinimo?.visible()
    }

    override fun mostrarResultados(resultados: List<ResultadoItemPedidosWebModel>) {
        resultadosAdapter.actualizarData(resultados)
    }

    override fun mostrarDialogoError() {
        val dateDialog = OrderWebErrorDialog()
        activity?.supportFragmentManager?.also {
            dateDialog.show(it, "")
        }
    }

    override fun mostrarMensajeError(mensaje: String) {
        tvMensaje?.text = mensaje
        tvMensaje?.setTextColor(requireContext().getCompatColor(R.color.red_error))
        tvMensaje?.visible()
    }

    override fun bloquear(model: ResultadoItemPedidosWebModel) {
        pedidosWebPresenter.verificarBloqueo(model)
    }

    override fun desbloquear(model: ResultadoItemPedidosWebModel) {
        pedidosWebPresenter.verificarDesbloqueo(model)
    }

    override fun llamar(telefono: String) {
        llamarATelefono(telefono)
    }

    override fun irAPerfil(personIdentifier: PersonIdentifier) {
        val profileFragment = getProfileFragment(personIdentifier)
        profileFragment?.showOnce(childFragmentManager)
    }

    private fun getProfileFragment(personIdentifier: PersonIdentifier): BaseDialogFragment? {
        return includeManager.getInclude(Include.PROFILE)
            .withArguments(
                PERSON_IDENTIFIER_KEY to personIdentifier
            ) as? BaseDialogFragment
    }

    override fun abrirOpciones(consultoraId: Int) {
        resultadosAdapter.mostrarOpciones(consultoraId)
        rlHeaderLock?.visible()
        rvPedidoWeb?.enableVersticleScroll(false)
        val backgroundColor = requireContext().getCompatColor(R.color.color_background_opciones)
        rvPedidoWeb?.setBackgroundColor(backgroundColor)
        rootLayout?.setOnTouchListener { _, _ -> true }
    }

    override fun cerrarOpciones() {
        resultadosAdapter.ocultarOpciones()
        rlHeaderLock?.gone()
        rvPedidoWeb?.enableVersticleScroll(true)
        val backgroundColor =
            requireContext().getCompatColor(R.color.sections_drilldown_detail_background)
        rvPedidoWeb?.setBackgroundColor(backgroundColor)
        rootLayout?.setOnTouchListener { _, _ -> false }
    }

    override fun onDestroy() {
        super.onDestroy()
        pedidosWebPresenter.onDestroy()
    }

}
