package biz.belcorp.salesforce.modules.developmentpath.features.ingresosextra.more

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import biz.belcorp.salesforce.components.exported.sharedToolbarLight
import biz.belcorp.salesforce.core.base.BaseToolbarDialogFragment
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.core.utils.toast
import biz.belcorp.salesforce.core.utils.withArguments
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.modules.developmentpath.core.domain.utils.Constants
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.AnalyticsConstants
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.FirebaseAnalyticsPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.ingresosextra.section.OnReloadMarcasListener
import biz.belcorp.salesforce.modules.developmentpath.features.ingresosextra.section.OtraMarcaModel
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.android.synthetic.main.fragment_ingresos_extra_otros.*

class IngresosExtraOtrosFragment : BaseToolbarDialogFragment(), IngresosExtraOtrosContract.View,
    IngresosExtraOtrosAdapter.Callback, View.OnClickListener {

    private val presenter by injectFragment<IngresosExtraOtrosContract.Presenter>()
    private val analyticsPresenter by injectFragment<FirebaseAnalyticsPresenter>()

    private var personaId: Long = Constants.MENOS_UNO.toLong()
    private var rol = Rol.CONSULTORA

    private val adapter by lazy {
        IngresosExtraOtrosAdapter().apply { callback = this@IngresosExtraOtrosFragment }
    }

    private var listener: OnReloadMarcasListener? = null

    override fun getLayout(): Int = R.layout.fragment_ingresos_extra_otros

    override fun getToolbar(): MaterialToolbar? = sharedToolbarLight

    override fun getTitle(): String? = resources.getString(R.string.title_agregar_marcas)

    override fun getMode(): Mode = Mode.CLOSEABLE

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (parentFragment is OnReloadMarcasListener) {
            listener = parentFragment as OnReloadMarcasListener
        } else {
            throw ClassCastException(
                context.toString()
                    + " must implement OnReloadMarcasListener"
            )
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recuperarArgs()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inicializar()
    }

    override fun onClick(v: View?) {
        var nameTagEvent = Constants.EMPTY_STRING
        when (v?.id) {
            R.id.btn_secondary -> {
                adapter.clearCheckedItems()
                nameTagEvent = AnalyticsConstants.LIMPIAR_MARCAS
            }
            R.id.btn_primary -> {
                presenter.checkList(adapter.getItemsObject())
                nameTagEvent = AnalyticsConstants.GUARDAR_MARCAS
            }
        }
        analyticsPresenter.enviarEventoTagConsultora(
            TagAnalytics.EVENTO_GUARDAR_LIMPIAR_MARCA_CONSULTORA,
            nameTagEvent
        )
    }

    override fun onCheckMarcaItem(item: OtraMarcaModel, posicion: Int) {
        if (item.checked) {
            analyticsPresenter.enviarEventoTagConsultora(
                TagAnalytics.EVENTO_VENDE_OTRA_MARCA,
                item.name
            )
        } else {
            analyticsPresenter.enviarEventoTagConsultora(
                TagAnalytics.EVENTO_NO_VENDE_OTRA_MARCA,
                item.name
            )
        }
    }

    override fun mostrarOtraData(data: List<OtraMarcaModel>) {
        if (!isAttached()) return
        (recycler?.adapter as? IngresosExtraOtrosAdapter)?.actualizar(data)
    }

    override fun checkSuccess() {
        if (!isAttached()) return
        listener?.onReloadMarcas()
        dismiss()
    }

    override fun checkFailure(message: String) {
        if (!isAttached()) return
        context?.toast(message)
    }

    private fun recuperarArgs() {
        arguments?.let {
            personaId = it.getLong(ARG_PERSONA_ID)
            rol = it.getSerializable(ARG_ROL) as Rol
        }
    }

    private fun inicializar() {
        configurarRecyclerView()
        inicializarEventos()
        inicializarPresenters()
    }

    private fun configurarRecyclerView() {
        val context = context ?: return
        recycler?.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = this@IngresosExtraOtrosFragment.adapter
        }
    }

    private fun inicializarEventos() {
        btn_secondary.setOnClickListener(this)
        btn_primary.setOnClickListener(this)
    }

    private fun inicializarPresenters() {
        presenter.obtener(personaId, rol)
        analyticsPresenter.enviarPantallaMarcas(TagAnalytics.PERFIL_MARCAS, rol, personaId)
    }

    companion object {

        private const val ARG_PERSONA_ID = "PERSONA_ID"
        private const val ARG_ROL = "ROL"

        fun newInstance(personaId: Long, rol: Rol): IngresosExtraOtrosFragment {
            return IngresosExtraOtrosFragment()
                .withArguments(
                    ARG_PERSONA_ID to personaId,
                    ARG_ROL to rol
                )
        }
    }
}
