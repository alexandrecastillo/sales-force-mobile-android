package biz.belcorp.salesforce.modules.developmentpath.features.resultadovisitas

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.enviarAWhatsapp
import biz.belcorp.salesforce.core.utils.hideKeyboardFrom
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.core.utils.llamarATelefono
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.FirebaseAnalyticsPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.flujo.Navigator
import kotlinx.android.synthetic.main.fragment_listar_resultado_visitas.*
import biz.belcorp.salesforce.core.utils.toast

class ListarResultadoVisitasFragment : BaseFragment(), ListarResultadoVisitasView {

    override fun getLayout(): Int = R.layout.fragment_listar_resultado_visitas

    var navigator: Navigator? = null

    private val presenter: ListarResultadoVisitasPresenter by injectFragment()
    private val firebaseAnalyticsPresenter: FirebaseAnalyticsPresenter by injectFragment()
    private lateinit var tipoLista: TipoLista

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tipoLista = arguments!!.getSerializable(ARG_PARAM1) as TipoLista
    }

    companion object {

        private const val ARG_PARAM1 = "param1"

        fun instanciarTipoFacturaron(): ListarResultadoVisitasFragment {
            return instanciar(TipoLista.FACTURARON)
        }

        fun instanciarTipoNoFacturaron(): ListarResultadoVisitasFragment {
            return instanciar(TipoLista.NO_FACTURARON)
        }

        private fun instanciar(tipo: TipoLista): ListarResultadoVisitasFragment {
            val fragment = ListarResultadoVisitasFragment()
            val args = Bundle()
            args.putSerializable(ARG_PARAM1, tipo)
            fragment.arguments = args

            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configurarAdapter()
        configurarBotones()
        configurarTextViewDeBusqueda()
        establecerVisibilidadDeSwitchPedidos()
        configurarSwitchPedidos()
        obtenerConsultoras()
    }

    private fun configurarAdapter() {
        rv_main?.layoutManager = LinearLayoutManager(context)
        rv_main?.adapter = instanciarAdapter()
    }

    private fun instanciarAdapter(): ListarResultadoVisitasAdapter? {
        val adapter = ListarResultadoVisitasAdapter(context ?: return null)

        adapter.clickListener = object : ListarResultadoVisitasAdapter.ClickListener {

            override fun alHacerClickEnWhatsapp(numero: String?) {
                context?.enviarAWhatsapp(numero ?: return)
            }

            override fun alHacerClickEnLlamada(numero: String?) {
                llamarATelefono(numero ?: return)
            }

            override fun alHacerClickEnCard(personaId: Long, codigo: String, rol: Rol) {
                val personIdentifier = PersonIdentifier(personaId, codigo, rol)
                navigator?.irAPerfil(personIdentifier)
            }
        }

        return adapter
    }

    private fun configurarBotones() {
        btn_back?.setOnClickListener {
            requireContext().hideKeyboardFrom(view)
            navigator?.retroceder()
        }

        btn_limpiar?.setOnClickListener { tv_filtro?.text?.clear() }
    }

    private fun configurarTextViewDeBusqueda() {
        tv_filtro?.addTextChangedListener(obtenerTextChangeListener())
    }

    private fun establecerVisibilidadDeSwitchPedidos() {
        when (tipoLista) {
            TipoLista.FACTURARON -> grupo_ver_pedido_somosbelcorp?.visibility = View.GONE
            TipoLista.NO_FACTURARON -> grupo_ver_pedido_somosbelcorp?.visibility = View.VISIBLE
        }
    }

    private fun configurarSwitchPedidos() {
        switch_ver_pedido_somosbelcorp?.setOnCheckedChangeListener { _, habilitado ->
            if (habilitado) {
                presenter.mostrarConsultorasConPedido()
            } else {
                presenter.ocultarConsultorasConPedido()
            }
            tagAnalyticsNoFacturaron(habilitado)
        }
    }

    private fun tagAnalyticsNoFacturaron(habilitado: Boolean){
        when(tipoLista){
            TipoLista.NO_FACTURARON -> {
                if(habilitado){
                    firebaseAnalyticsPresenter.enviarEventoTagConsultora(
                        TagAnalytics.EVENTO_NO_FACTURARON_CONSULTORA,
                            Constant.VER_CONSULTORAS_CON_PEDIDO_SB)
                } else {
                    firebaseAnalyticsPresenter.enviarEventoTagConsultora(TagAnalytics.EVENTO_NO_FACTURARON_CONSULTORA,
                            Constant.NO_VER_CONSULTORAS_CON_PEDIDO_SB)
                }
            }
            else -> return
        }
    }

    private fun obtenerConsultoras() {
        when (tipoLista) {
            TipoLista.FACTURARON -> presenter.obtenerFacturadas()
            TipoLista.NO_FACTURARON -> presenter.obtenerNoFacturadas()
        }
    }

    private fun obtenerTextChangeListener() = object : TextWatcher {

        override fun afterTextChanged(s: Editable?) = Unit

        override fun beforeTextChanged(s: CharSequence?,
                                       start: Int,
                                       count: Int, after: Int) = Unit

        override fun onTextChanged(s: CharSequence?,
                                   start: Int,
                                   before: Int, count: Int) {
            cambiarIconoBusquedaSiVacio(s)
            presenter.filtrar(s?.toString() ?: "")
        }

        private fun cambiarIconoBusquedaSiVacio(s: CharSequence?) {
            if (s.isNullOrBlank()) {
                btn_limpiar?.visibility = View.GONE
                iv_busqueda?.visibility = View.VISIBLE
            } else {
                btn_limpiar?.visibility = View.VISIBLE
                iv_busqueda?.visibility = View.GONE
            }
        }
    }

    override fun mostrarCampania(campania: String) {
        tv_campaign_information?.text = campania
    }

    override fun mostrarTituloFacturaron() {
        tv_title?.text =
                getString(R.string.rdd_lista_resultado_visitas_titulo_facturadas)
    }

    override fun mostrarTituloNoFacturaron() {
        tv_title?.text =
                getString(R.string.rdd_lista_resultado_visitas_titulo_no_facturadas)
    }

    override fun mostrarSubtituloFacturaron(campania: String) {
        tv_subtitulo?.text =
                getString(R.string.rdd_lista_resultado_visitas_subtitulo_facturadas, campania)
    }

    override fun mostrarSubtituloNoFacturaron(campania: String) {
        tv_subtitulo?.text =
                getString(R.string.rdd_lista_resultado_visitas_subtitulo_no_facturadas, campania)
    }

    override fun mostrarCantidadTotalDeConsultoras(cantidad: Int) {
        tv_cantidad_total?.text =
                resources.getQuantityString(R.plurals.rdd_lista_resultado_visitas_cantidad_total,
                                            cantidad,
                                            cantidad)
    }

    override fun mostrarConsultoras(modelos: List<ConsultoraModel>, campaniaAnteriorNombreCorto: String) {
        (rv_main?.adapter as? ListarResultadoVisitasAdapter)?.actualizar(modelos, campaniaAnteriorNombreCorto)
    }

    override fun pintarCantidadFiltradas(cantidad: Int) {
        tv_cantidad_resultados?.text =
                getString(R.string.rdd_resultado_visitas_cantidad_filtro, cantidad.toString())
    }

    override fun mostrarCantidadFiltradas() {
        grupo_cantidad_resultados?.visibility = View.VISIBLE
    }

    override fun ocultarCantidadFiltradas() {
        grupo_cantidad_resultados?.visibility = View.GONE
    }

    override fun mostrarSugerencias(sugerencias: List<String>) {
        val adapter = ArrayAdapter(context ?: return,
                                   android.R.layout.simple_dropdown_item_1line,
                                   sugerencias)

        tv_filtro?.setAdapter(adapter)
    }

    override fun mostrarSinResultadosBusqueda() {
        grupo_sin_resultados?.visibility = View.VISIBLE
    }

    override fun ocultarSinResultadosBusqueda() {
        grupo_sin_resultados?.visibility = View.GONE
    }

    override fun bloquearSwitch() {
        switch_ver_pedido_somosbelcorp?.isEnabled = false
    }

    override fun desbloquearSwitch() {
        switch_ver_pedido_somosbelcorp?.isEnabled = true
    }

    override fun mostrarCargando() {
        pb_ver_pedido?.visibility = View.VISIBLE
    }

    override fun ocultarCargado() {
        pb_ver_pedido?.visibility = View.GONE
    }

    override fun resetearSwitch() {
        switch_ver_pedido_somosbelcorp?.isChecked = false
    }

    override fun mostrarErrorSwitch() {
        context ?: return
        toast(R.string.rdd_lista_resultado_visitas_error_ver_pedidos)
    }

    private enum class TipoLista {
        FACTURARON, NO_FACTURARON
    }
}
