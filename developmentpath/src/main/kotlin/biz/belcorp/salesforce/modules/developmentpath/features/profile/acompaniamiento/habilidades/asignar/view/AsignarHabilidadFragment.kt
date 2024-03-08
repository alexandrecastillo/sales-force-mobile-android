package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.habilidades.asignar.view

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import biz.belcorp.salesforce.components.widgets.TextDrawable
import biz.belcorp.salesforce.core.base.BaseDialogFragment
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.core.utils.sp
import biz.belcorp.salesforce.core.utils.toUpperCase
import biz.belcorp.salesforce.core.utils.withArguments
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.common.broadcast.SenderRecargarHabilidadesEquipo
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.focos.FocoSeleccionado
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.FirebaseAnalyticsPresenter
import biz.belcorp.salesforce.modules.developmentpath.common.broadcast.SenderRecargarHabilidadesPropias
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.habilidades.asignar.adapter.AsignarHabilidadesAdapter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.habilidades.asignar.model.HabilidadModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.habilidades.asignar.presenter.AsignarHabilidadPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.habilidades.model.GerenteZonaModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_asignar_habilidad.*
import kotlinx.android.synthetic.main.item_rdd_dashboard_focos_zona.rv_habilidades
import java.io.Serializable

class AsignarHabilidadFragment : BaseDialogFragment(), AsignarHabilidadView,
    AsignarHabilidadesAdapter.HabilidadesAdapterListener {

    private lateinit var request: Request
    private var itemPosition: Int = DEFAULT_POSITION
    private lateinit var asignarHabilidadesAdapter: AsignarHabilidadesAdapter
    private val presenter by injectFragment<AsignarHabilidadPresenter>()
    private val senderRecargarHabilidadesEquipo by injectFragment<SenderRecargarHabilidadesEquipo>()
    private val senderRecargarHabilidadesPropias by injectFragment<SenderRecargarHabilidadesPropias>()
    private val firebaseAnalyticsPresenter by injectFragment<FirebaseAnalyticsPresenter>()

    var focoSeleccionado: FocoSeleccionado? = null
    var valClick: Boolean = false

    private var tagAnalyticName: TagAnalytics = TagAnalytics.EVENTO_SELECCION_MIS_HABILIDADES

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        request = arguments?.getSerializable(REQUEST_ASIGNAR) as Request
        itemPosition = arguments?.getInt(ITEM_POSITION_EXTRA, DEFAULT_POSITION) ?: DEFAULT_POSITION
    }

    override fun getLayout(): Int {
        return R.layout.fragment_asignar_habilidad
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_habilidades?.layoutManager = LinearLayoutManager(context)
        rv_habilidades?.adapter = configurarAdapter()
        iv_close?.setOnClickListener { cerrarDialogo() }
        btn_asignar?.setOnClickListener { eventoAsignar() }
        obtenerDatos()
    }

    private fun obtenerDatos() {
        val request = request
        when (request) {
            is Request.EditarHabilidadesAOtro -> {
                tagAnalyticName = TagAnalytics.EVENTO_SELECCION_HABILIDADES_MI_EQUIPO
                presenter.obtenerHabilidades(request.personaId)
            }
            is Request.EditarHabilidadesPropias -> {
                tagAnalyticName = TagAnalytics.EVENTO_SELECCION_MIS_HABILIDADES
                presenter.obtenerMisHabilidades()
                ocultarDatos()
            }
        }
    }

    private fun eventoAsignar() {
        val request = request
        when (request) {
            is Request.EditarHabilidadesAOtro -> {
                presenter.asignarHabilidades()
            }
            is Request.EditarHabilidadesPropias -> {
                presenter.asignarMiHabilidades()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        fitFullScreen()
    }

    private fun configurarAdapter(): AsignarHabilidadesAdapter {
        asignarHabilidadesAdapter = AsignarHabilidadesAdapter()
        asignarHabilidadesAdapter.listener = this
        return asignarHabilidadesAdapter
    }

    override fun deshabilitarBotonGuardado() {
        if (context == null) return

        btn_asignar?.isEnabled = false
    }

    override fun habilitarBotonGuardado() {
        if (context == null) return

        btn_asignar?.isEnabled = true
    }

    override fun cargarHabilidades(listaHabilidades: List<HabilidadModel>) {
        asignarHabilidadesAdapter.habilidades = listaHabilidades
        rv_habilidades?.adapter?.notifyDataSetChanged()
    }

    override fun cargarCantidadesDeHabilidades(
        titulo: Int,
        numeroSeleccionados: Int,
        maximoNumeroHabilidades: Int
    ) {
        btn_asignar?.text = getString(
            R.string.rdd_asignar_habilidad,
            numeroSeleccionados,
            maximoNumeroHabilidades
        )
        tv_habilidades_subtitulo?.text = getString(titulo, maximoNumeroHabilidades)
    }

    override fun cargarGerenteZona(gerenteZona: GerenteZonaModel) {
        agregarIconoIniciales(gerenteZona.iniciales)
        tv_nombre_gz?.text = gerenteZona.nombreCompleto
        tv_estado_gz?.text = gerenteZona.estado
    }

    fun ocultarDatos() {
        ly_datos?.visibility = View.GONE
    }

    override fun onClickHabilidad(posicion: Int) {
        presenter.cambiarSeleccionHabilidad(posicion)
        valClick = true
    }

    override fun cargarFococSeleccionado(foco: FocoSeleccionado) {

        if (valClick && presenter.obtenerRol().toUpperCase() == Rol.GERENTE_REGION.codigoRol) {
            focoSeleccionado =
                FocoSeleccionado(foco.codigoua, Constant.EVENTO_ASIGNAR_FOCOS, foco.focos)
            firebaseAnalyticsPresenter.enviarFocosSeleccionado(tagAnalyticName, focoSeleccionado!!)
            valClick = false
        }

    }

    override fun cerrar() {
        cerrarDialogo()
    }

    override fun recargarTabFocos() {

        val request = request
        when (request) {
            is Request.EditarHabilidadesAOtro -> {
                senderRecargarHabilidadesEquipo.recargar(itemPosition)
            }
            is Request.EditarHabilidadesPropias -> {
                senderRecargarHabilidadesPropias.recargar()
            }
        }
    }

    fun agregarIconoIniciales(iniciales: String) {
        if (context == null) return

        val placeholderCircular = TextDrawable.builder()
            .beginConfig()
            .fontSize(sp(16))
            .endConfig()
            .buildRound(
                iniciales,
                ContextCompat.getColor(requireContext(), R.color.rdd_accent)
            )
        val requestOptions = RequestOptions.circleCropTransform()
            .placeholder(placeholderCircular)


        Glide.with(requireContext())
            .load("")
            .apply(requestOptions)
            .into(iv_iniciales_gz)
    }

    private fun cerrarDialogo() {
        closeDialog()
    }

    sealed class Request : Serializable {
        class EditarHabilidadesAOtro(val personaId: Long) : Request(),
            Serializable

        class EditarHabilidadesPropias : Request(),
            Serializable
    }

    companion object {

        private const val REQUEST_ASIGNAR = "REQUEST_ASIGNAR"
        private const val ITEM_POSITION_EXTRA = "ITEM_POSITION"
        private const val DEFAULT_POSITION = -1

        fun newInstance(
            request: Request,
            itemPosition: Int = DEFAULT_POSITION
        ): AsignarHabilidadFragment {
            return AsignarHabilidadFragment().withArguments(
                REQUEST_ASIGNAR to request,
                ITEM_POSITION_EXTRA to itemPosition
            )
        }
    }
}
