package biz.belcorp.salesforce.modules.consultants.features.list

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import biz.belcorp.salesforce.base.utils.navigateTo
import biz.belcorp.salesforce.core.base.BaseDialogFragment
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.constants.CONSULTANT_ID
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.constants.IS_REGULAR_CONSULTANT_LIST
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.events.ConsumableEvent
import biz.belcorp.salesforce.core.events.EventSubject
import biz.belcorp.salesforce.core.events.LiveDataBus
import biz.belcorp.salesforce.core.features.consultants.OnConsultantsListener
import biz.belcorp.salesforce.core.features.utils.PERSON_IDENTIFIER_KEY
import biz.belcorp.salesforce.core.include.Include
import biz.belcorp.salesforce.core.include.IncludeManager
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.consultants.R
import biz.belcorp.salesforce.modules.consultants.core.domain.constants.Constants
import biz.belcorp.salesforce.modules.consultants.features.list.adapters.BeautyConsultantAdapter
import biz.belcorp.salesforce.modules.consultants.features.list.adapters.OnConsultantItemSelected
import biz.belcorp.salesforce.modules.consultants.features.list.models.ConsultoraModel
import biz.belcorp.salesforce.modules.consultants.features.maps.MapFragment.Companion.HASH_CONSULTANT_ID
import biz.belcorp.salesforce.modules.consultants.features.maps.MapFragment.Companion.HASH_CONSULTANT_LAT_LNG
import biz.belcorp.salesforce.modules.consultants.features.search.models.FiltroConsultoraModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.fragment_consultants_list_view.*
import org.koin.android.ext.android.inject
import biz.belcorp.salesforce.base.R as BaseR

class ConsultantsListFragment :
    BaseFragment(),
    ConsultantsListView,
    OnConsultantItemSelected,
    OnConsultantsListener {

    private val includeManager by inject<IncludeManager>()

    private val presenter: ConsultantsListPresenter by injectFragment()

    private val consultantsAdapter: BeautyConsultantAdapter by inject()

    private var onBackPressed: (() -> Unit)? = null

    override fun getLayout() = R.layout.fragment_consultants_list_view

    companion object {

        private const val KEY_FILTROS = "Filtros"
        private const val ARG_BEAUTY_CONSULTANT_TYPE_LIST = "ARG_BEAUTY_CONSULTANT_TYPE_LIST"

        const val ARG_PREV_UA_ID_SELECTED = "PREV_UA_SEGMENT_ID_SELECTED"
        const val ARG_CONSULTANT_ID_LIST_SELECTED = "ARG_CONSULTANT_ID_LIST_SELECTED"

        fun newInstance(filtros: FiltroConsultoraModel) = ConsultantsListFragment()
            .withArguments(
                KEY_FILTROS to filtros
            )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBackPressedHandler()

        arguments?.let {
            presenter.setUaIdSelected(it.getString(ARG_PREV_UA_ID_SELECTED, Constants.EMPTY_STRING))
            presenter.setConsultantTypeItem(it.getInt(ARG_BEAUTY_CONSULTANT_TYPE_LIST))
            presenter.setFiltroBusqueda(it.getParcelable(KEY_FILTROS))

            if (it.getInt(ARG_CONSULTANT_ID_LIST_SELECTED) != 0) {
                presenter.setConsultantListId(it.getInt(ARG_CONSULTANT_ID_LIST_SELECTED))
            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.getSymbol()
        observeLocationState()
    }

    override fun onLocationIconSelected(model: ConsultoraModel) {
        navigateTo(
            BaseR.id.globalToMapFeature,
            bundleOf(
                CONSULTANT_ID to model.consultorasId,
                IS_REGULAR_CONSULTANT_LIST to true
            )
        )
    }

    override fun onCallIconSelected(section: String?, phoneNumber: String?) {
        llamarATelefono(phoneNumber ?: return)
    }

    override fun setCurrencySymbol(currencySymbol: String) {
        consultantsAdapter.currencySymbol = currencySymbol
    }

    override fun setHightlightName(name: String?) {
        consultantsAdapter.highlightName = name
    }

    override fun onConsultantItemSelected(personIdentifier: PersonIdentifier) {
        val profileFragment = getProfileFragment(personIdentifier)
        profileFragment?.showOnce(childFragmentManager)
    }

    override fun onGrownConsultantItemSelected(consultant: ConsultoraModel) = Unit

    override fun onWhatsAppIconSelected(phoneNumber: String?) {
        phoneNumber?.let { context?.enviarAWhatsapp(it) }
    }

    override fun setBeautyConsultantTypeView(consultantTypeItem: Int) {
        consultantsAdapter.buityConsultantViewType = consultantTypeItem
    }

    override fun showConsultantList(consultants: List<ConsultoraModel>) {
        consultantsAdapter.addItems(consultants)
        consultantsAdapter.setListenerAdapter(this)
        rvwConsultantsList?.apply {
            adapter = consultantsAdapter
            layoutManager = LinearLayoutManager(context)
        }

        activity?.let {
            it.sendBroadcast(Intent().apply {
                action = Constant.ACTION_CONSULTANTS_COUNT
                putExtra(Constant.BUNDLE_CONSULTANTS_COUNT, consultants.size)
            })
        }
    }

    override fun showBeautyConsultantAmountByConstancyInfo(info: SpannableString) {
        tvwBeautyConsultantAmountInfoPerPeriod?.visible()
        tvwBeautyConsultantAmountInfoPerPeriod?.text = info
    }

    override fun showPossibleChangeConsultantsTitle() {
        tvwNonConsultantsAvailablePdvLabel?.gone()
        ivwNonConsultantsAvailableIcon?.gone()
        rvwConsultantsList?.visible()
        tvwBeautyConsultantAmountInfoPerPeriod?.visible()
        tvwBeautyConsultantAmountInfoPerPeriod?.text =
            getString(R.string.impulsa_a_las_siguientes_consultoras)
        tvwBeautyConsultantAmountInfoPerPeriod?.setTypeface(null, Typeface.BOLD)
    }

    override fun hidePossibleChangeConsultantsTitle() {
        tvwBeautyConsultantAmountInfoPerPeriod?.gone()
    }

    override fun hideNonDataAvailable() {
        rvwConsultantsList?.visible()
        tvConsolidadoNoData?.gone()
    }

    override fun showNonDataAvailable() {
        rvwConsultantsList?.gone()
        tvConsolidadoNoData?.visible()
    }

    override fun hideNonGrownConsultantDataAvailable() {
        rvwConsultantsList?.visible()
        tvwBeautyConsultantAmountInfoPerPeriod?.visible()
        tvwNonConsultantsAvailablePdvLabel?.gone()
        ivwNonConsultantsAvailableIcon?.gone()
    }

    override fun showNonGrownConsultantDataAvailable() {
        rvwConsultantsList?.gone()
        tvwBeautyConsultantAmountInfoPerPeriod?.gone()
        tvwNonConsultantsAvailablePdvLabel?.visible()
        ivwNonConsultantsAvailableIcon?.visible()
    }

    override fun activeFiltroBusqueda() {
        consultantsAdapter.filtroBusqueda = true
    }

    override fun getConsultants(ua: String) {
        presenter.setUaIdSelected(ua)
        presenter.iniciarBusqueda()
    }

    override fun getGrownConsultants(filter: String, ua: String, typeSelection: Int) {
        presenter.getBeautyConsultantsByNivel(filter, ua, typeSelection)
    }

    override fun getEndPeriodConsultants(nivel: String, section: String) {
        presenter.getEndPeriodConsultants(nivel = nivel, section = section)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    private fun getProfileFragment(personIdentifier: PersonIdentifier): BaseDialogFragment? {
        return includeManager.getInclude(Include.PROFILE)
            .withArguments(
                PERSON_IDENTIFIER_KEY to personIdentifier
            ) as? BaseDialogFragment
    }

    fun setOnBackPressedListener(onBackPressed: () -> Unit) {
        this.onBackPressed = onBackPressed
    }

    private fun setupBackPressedHandler() {
        onBackPressedHandler {
            fragmentManager?.popBackStack()
            onBackPressed?.invoke()
        }
    }

    private fun observeLocationState() {
        LiveDataBus.from<ConsultantsListFragment>(EventSubject.CONSULTANTS_LOCATION)
            .observe(viewLifecycleOwner, locationObserver)
    }

    private val locationObserver = Observer<ConsumableEvent> {
        it.runAndConsume {
            (it.value as Bundle).apply {
                val latLng = get(HASH_CONSULTANT_LAT_LNG) as LatLng
                val consultantId = get(HASH_CONSULTANT_ID) as Int
                consultantsAdapter.findAndUpdateConsultant(
                    id = consultantId,
                    location = latLng
                )
            }
        }
    }

}
