package biz.belcorp.salesforce.modules.developmentpath.features.habilidades.reconocer.view

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import biz.belcorp.salesforce.components.widgets.TextDrawable
import biz.belcorp.salesforce.core.base.BaseDialogFragment
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.core.utils.sp
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.common.broadcast.SenderRecargarHabilidades
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.FirebaseAnalyticsPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.habilidades.reconocer.model.HabilidadModel
import biz.belcorp.salesforce.modules.developmentpath.features.habilidades.reconocer.presenter.ReconocerHabilidadesPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.habilidades.model.GerenteZonaModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_reconocer_habilidades.*


class ReconocerHabilidadesFragment : BaseDialogFragment(),
    ReconocerHabilidadesView,
    ReconocerHabilidadesAdapter.HabilidadesAdapterListener {

    private var personaId: Long = 0
    private var campania: String = Constant.EMPTY_STRING
    private lateinit var rol: Rol
    private lateinit var tipo: Tipo

    private lateinit var reconocerHabilidadesAdapter: ReconocerHabilidadesAdapter
    private val presenter by injectFragment<ReconocerHabilidadesPresenter>()
    private val firebaseAnalyticsPresenter by injectFragment<FirebaseAnalyticsPresenter>()

    private lateinit var senderRecargarHabilidades: SenderRecargarHabilidades

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recuperarArgumentos()
    }

    private fun recuperarArgumentos() {
        personaId = arguments?.getLong(ID)!!
        campania = arguments?.getString(CAMPANIA) ?: ""
        rol = arguments?.getSerializable(ROL) as Rol
        tipo = arguments?.getSerializable(TIPO) as Tipo
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        senderRecargarHabilidades = SenderRecargarHabilidades(view.context)
        escucharAcciones()
        configurarRecyclerView()
        recuperarDatos()
    }

    private fun escucharAcciones() {
        iv_close.setOnClickListener { cerrarDialogo() }
        btn_reconocer.setOnClickListener {
            firebaseAnalyticsPresenter.enviarEventoPorRol(
                TagAnalytics.EVENTO_RECONOCER_HABILIDADES, rol
            )
            presenter.reconocerHabilidades()
        }
    }

    private fun configurarRecyclerView() {
        rv_habilidades.layoutManager =
            GridLayoutManager(context, presenter.obtenerSpanCountPorRol())
        rv_habilidades.adapter = configurarAdapter()
    }

    private fun cerrarDialogo() {
        closeDialog()
    }

    private fun recuperarDatos() {
        when (tipo) {
            Tipo.INSERCION ->
                presenter.obtenerHabilidadesParaInsercion(personaId, rol)
            Tipo.VISUALIZACION ->
                presenter.obtenerHabilidadesParaVisualizacion(personaId, rol, campania)
        }
    }

    override fun getLayout(): Int {
        return R.layout.fragment_reconocer_habilidades
    }

    override fun cerrar() {
        cerrarDialogo()
        abrirReconocerHabilidadesExito()
    }

    private fun abrirReconocerHabilidadesExito() {
        if (!isResumed) return

        val fragment = HabilidadesReconocidasSuccessDialogFragment.newInstance(rol)
        fragmentManager?.let { fragment.show(it, fragment.tag) }
    }


    private fun configurarAdapter(): ReconocerHabilidadesAdapter {
        reconocerHabilidadesAdapter = ReconocerHabilidadesAdapter()
        reconocerHabilidadesAdapter.listener = this
        return reconocerHabilidadesAdapter
    }

    override fun cargarHabilidades(listaHabilidades: List<HabilidadModel>) {
        reconocerHabilidadesAdapter.habilidades = listaHabilidades
        rv_habilidades?.adapter?.notifyDataSetChanged()
    }

    override fun cargarCantidadesDeHabilidades(
        numeroSeleccionados: Int,
        maximoNumeroHabilidades: Int
    ) {
        btn_reconocer?.text = getString(
            R.string.rdd_asignar_habilidad,
            numeroSeleccionados,
            maximoNumeroHabilidades
        )
    }

    override fun cargarGerenteZona(gerenteZona: GerenteZonaModel) {
        agregarIconoIniciales(gerenteZona.iniciales)
        tv_nombre_gz?.text = gerenteZona.nombreCompleto
        tv_estado_gz?.text = gerenteZona.estado
    }

    override fun onClickHabilidad(posicion: Int) {
        presenter.cambiarSeleccionHabilidad(posicion)
    }

    private fun agregarIconoIniciales(iniciales: String) {
        if (context == null) return

        val placeholderCircular = TextDrawable.builder()
            .beginConfig()
            .fontSize(sp(16))
            .endConfig()
            .buildRound(
                iniciales,
                ContextCompat.getColor(context!!, R.color.rdd_accent)
            )

        val requestOptions = RequestOptions()

        Glide.with(context!!)
            .load("")
            .apply(requestOptions.placeholder(placeholderCircular))
            .into(iv_iniciales_gz)
    }

    override fun mostrarBotonReconocer(mostrarBotonReconocer: Boolean) {
        btn_reconocer?.visibility = obtenerVisibilidad(mostrarBotonReconocer)
    }

    override fun desactivarSeleccionar(desactivarSeleccionar: Boolean) {
        rv_habilidades?.isEnabled = !desactivarSeleccionar
    }

    override fun onStart() {
        super.onStart()
        fitFullScreen()
    }

    override fun recargar() {
        senderRecargarHabilidades.recargar()
    }

    private fun obtenerVisibilidad(mostrar: Boolean): Int {
        return if (mostrar) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    companion object {

        const val ID: String = "ID"
        const val CAMPANIA = "CAMPANIA"
        const val ROL = "rol"
        const val TIPO = "tipo"

        fun crearParaVisualizacion(
            id: Long,
            campania: String,
            rol: Rol
        ): ReconocerHabilidadesFragment {
            val fragment = ReconocerHabilidadesFragment()
            val args = Bundle()
            args.putLong(ID, id)
            args.putString(CAMPANIA, campania)
            args.putSerializable(ROL, rol)
            args.putSerializable(TIPO, Tipo.VISUALIZACION)
            fragment.arguments = args
            return fragment
        }

        fun crearParaInsercion(id: Long, rol: Rol): ReconocerHabilidadesFragment {
            val fragment = ReconocerHabilidadesFragment()
            val args = Bundle()
            args.putLong(ID, id)
            args.putSerializable(ROL, rol)
            args.putSerializable(TIPO, Tipo.INSERCION)
            fragment.arguments = args
            return fragment
        }
    }

    enum class Tipo {
        VISUALIZACION, INSERCION
    }

}
