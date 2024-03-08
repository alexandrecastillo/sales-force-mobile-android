package biz.belcorp.salesforce.modules.developmentpath.features.ingresosextra.section

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import biz.belcorp.salesforce.analytics.core.domain.entities.UserProperties
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.session.Sesion
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.core.utils.setOnItemSelectedListener
import biz.belcorp.salesforce.core.utils.withArguments
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.domain.utils.Constants
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.AnalyticsConstants
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.FirebaseAnalyticsPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.ingresosextra.more.IngresosExtraOtrosContract
import biz.belcorp.salesforce.modules.developmentpath.features.ingresosextra.more.IngresosExtraOtrosFragment
import kotlinx.android.synthetic.main.fragment_ingresos_extra.*

class IngresosExtraFragment : BaseFragment(), IngresosExtraContract.View,
    IngresosExtraOtrosContract.View,
    IngresosExtraAdapter.Callback, OnReloadMarcasListener {

    private val presenter by injectFragment<IngresosExtraContract.Presenter>()
    private val presenterOthers by injectFragment<IngresosExtraOtrosContract.Presenter>()
    private val analyticsPresenter by injectFragment<FirebaseAnalyticsPresenter>()

    private var personaId: Long = Constants.MENOS_UNO.toLong()
    private var rol = Rol.CONSULTORA

    private val adapter by lazy {
        IngresosExtraAdapter().apply { callback = this@IngresosExtraFragment }
    }

    override fun getLayout() = R.layout.fragment_ingresos_extra

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recuperarArgs()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inicializar()
    }

    override fun onCheckIngresosExtrasItem(item: OtraMarcaModel, posicion: Int) {

        if(checkIfCategoryIsSelected()){
            addCategoryandCampaignInEachIngresoExtraItem(UserProperties.session)
        }

        presenter.check(item)

        if (item.checked) {
            analyticsPresenter.enviarEventoSeleccionSwitch(
                TagAnalytics.EVENTO_VENDE_OTRA_MARCA,
                item.name)
        } else {
            analyticsPresenter.enviarEventoSeleccionSwitch(
                TagAnalytics.EVENTO_NO_VENDE_OTRA_MARCA,
                item.name
            )
        }

        getCheckedValuesForToogle()
        getCheckedValuesForDisableToogle()
    }

    private fun checkIfCategoryIsSelected(): Boolean {
        return ( sp_categories.selectedItemPosition > 0)
    }

    override fun onReloadMarcas() {
        presenter.obtenerMarcasNofront(personaId, rol)
    }

    override fun mostrarData(data: List<OtraMarcaModel>) {
        checkIfCategoryAlreadySelected(data)
        showLatestCampaignUpdate(data)
        if (!isAttached()) return
        (recycler?.adapter as? IngresosExtraAdapter)?.actualizar(data)
        getCheckedValuesForToogle()
    }

    private fun checkIfCategoryAlreadySelected(data: List<OtraMarcaModel>) {
        val categorySelected = data.filter {
            !it.categoria.isNullOrEmpty() && it.categoria != "null"
        }.firstOrNull()

        when (categorySelected?.categoria) {
            "Seleccionar categoría" , null -> sp_categories.setSelection(0)
            "Maquillaje" -> sp_categories.setSelection(1)
            "Fragancias" -> sp_categories.setSelection(2)
            "Tratamiento Facial" ->  sp_categories.setSelection(3)
            "Tratamiento Corporal" -> sp_categories.setSelection(4)
            "Accesorios cosmeticos" -> sp_categories.setSelection(5)
            "Bijouterie" -> sp_categories.setSelection(6)
            "Otras Categorías" -> sp_categories.setSelection(7)
        }
    }

    override fun mostrarOtraData(data: List<OtraMarcaModel>) {
        data.forEach { item ->
            item.checked = false
        }

        presenterOthers.checkList(data)
    }

    override fun checkSuccess() {
        text_split?.text = ""
        text_split?.visibility = View.GONE

        getCheckedValuesForDisableToogle()
    }

    override fun mostrarDataNoFront(data: List<OtraMarcaModel>) {
        if (!isAttached()) return
        if (data.isNullOrEmpty()) {
            text_split?.visibility = View.GONE

            getCheckedValuesForToogle()
            return
        }
        var concat = ""
        for (i in data.indices) {
            concat += if (i == data.size - 1) {
                data[i].name
            } else {
                data[i].name + ", "
            }
        }
        text_split?.text = concat
        text_split?.visibility = View.VISIBLE

        getCheckedValuesForToogle()
        getCheckedValuesForDisableToogle()
    }

    fun showLatestCampaignUpdate(data: List<OtraMarcaModel>) {
        val latestCampaign = data.filter {
            !it.campania.isNullOrEmpty() && it.campania != "null"
        }.firstOrNull()


        tv_last_update_campaign.text =
            context!!.getString(R.string.last_campaign_update, latestCampaign?.campania ?: "No Disponible")
    }

    override fun updateSelectedCategory(selectedCategory: String?) {
        sp_categories.setSelection(getCategoryPosition(selectedCategory))
    }

    private fun getCategoryPosition(selectedCategory: String?): Int {
        return getCategories.indexOf(selectedCategory)
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
        setUpCompetitorsSpinner()
    }

    private fun configurarRecyclerView() {
        val context = context ?: return
        recycler?.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 3)
            adapter = this@IngresosExtraFragment.adapter
        }
    }

    private fun setUpCompetitorsSpinner() {
        sp_categories.actualizar(getCategories)
        sp_categories?.setOnItemSelectedListener { position ->
            if (position != 0) {
                sp_categories.setSelection(position)
            }
        }
    }

    private fun inicializarEventos() {
        btn_add_others.setOnClickListener {
            IngresosExtraOtrosFragment.newInstance(personaId, rol)
                .show(childFragmentManager, null)
            analyticsPresenter.enviarEventoTagConsultora(
                TagAnalytics.EVENTO_PERFIL_CONSULTORA,
                AnalyticsConstants.AGREGAR_OTRAS_MARCAS
            )
        }

        switch_check.setOnClickListener {
            if (switch_check.isChecked) {
                apagarProductos()

                presenterOthers.obtener(personaId, rol)
            }
        }
    }

    private fun apagarProductos() {
        sp_categories.setSelection(0)
        (recycler?.adapter as? IngresosExtraAdapter)?.apagarTodo()

        (recycler?.adapter as? IngresosExtraAdapter)?.getItems().let { list ->
            list?.forEach { item ->
                presenter.check(item)
            }
        }
    }

    private fun inicializarPresenters() {
        presenter.obtener(personaId, rol)
        presenter.obtenerMarcasNofront(personaId, rol)
    }

    private fun getCheckedValuesForToogle() {
        var checkedRecyclerItems = false

        (recycler?.adapter as? IngresosExtraAdapter)?.getItems().let { list ->
            list?.forEach { item ->
                if (item.checked) checkedRecyclerItems = true
            }
        }

        switch_check.isChecked = !(checkedRecyclerItems || text_split.visibility == View.VISIBLE)
    }

    private fun addCategoryandCampaignInEachIngresoExtraItem(sesion: Sesion?) {
        (recycler?.adapter as? IngresosExtraAdapter)?.getItems().let { list ->
            list?.forEach { item ->
                item.categoria = sp_categories.selectedItem as String
                item.campania = sesion?.campaign?.codigo ?: " "
            }
        }
    }

    private fun getCheckedValuesForDisableToogle() {
        var checkedRecyclerItems = false

        (recycler?.adapter as? IngresosExtraAdapter)?.getItems().let { list ->
            list?.forEach { item ->
                if (item.checked) checkedRecyclerItems = true
            }
        }

        view_disable_switch.visibility =
            if (checkedRecyclerItems || text_split.visibility == View.VISIBLE) {
                View.GONE
            } else {
                View.VISIBLE
            }
    }

    companion object {
        val TAG = IngresosExtraFragment::class.java.simpleName

        private const val ARG_PERSONA_ID = "PERSONA_ID"
        private const val ARG_ROL = "ROL"

        fun newInstance(personaId: Long, rol: Rol): IngresosExtraFragment {
            return IngresosExtraFragment()
                .withArguments(
                    ARG_PERSONA_ID to personaId,
                    ARG_ROL to rol
                )
        }

        val getCategories: List<String>
            get() = listOf(
                "Seleccionar categoría",
                "Maquillaje",
                "Fragancias",
                "Tratamiento Facial",
                "Tratamiento Corporal",
                "Accesorios cosmeticos",
                "Bijouterie",
                "Otras Categorías"
            )
    }
}
