package biz.belcorp.salesforce.modules.billing.features.billing.view.detail

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.base.FragmentParameters
import biz.belcorp.salesforce.core.constants.SourceType
import biz.belcorp.salesforce.core.domain.entities.consultants.ConsultantFilter
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.events.EventSubject
import biz.belcorp.salesforce.core.events.LiveDataBus
import biz.belcorp.salesforce.core.include.Include.Companion.BILLING_ADVANCEMENT
import biz.belcorp.salesforce.core.include.IncludeManager
import biz.belcorp.salesforce.core.utils.getSelectedTab
import biz.belcorp.salesforce.core.utils.inject
import biz.belcorp.salesforce.core.utils.onTabSelected
import biz.belcorp.salesforce.core.utils.replaceOnce
import biz.belcorp.salesforce.modules.billing.R
import biz.belcorp.salesforce.modules.billing.features.billing.model.BillingFragmentParameters
import biz.belcorp.salesforce.modules.billing.features.billing.view.BillingViewModel
import biz.belcorp.salesforce.modules.billing.features.billing.view.BillingViewState
import biz.belcorp.salesforce.modules.billing.features.billing.view.detail.utils.TabsCreator
import biz.belcorp.salesforce.modules.billing.features.utils.AnalyticUtils
import kotlinx.android.synthetic.main.fragment_billing_detail.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class BillingDetailFragment : BaseFragment() {

    private val includeManager by inject<IncludeManager>()

    private val uaViewModel by sharedViewModel<BillingViewModel>(from = { requireParentFragment() })

    private val sourceType = SourceType.BILLING_ADVANCEMENT

    private val args by lazy { arguments?.getSerializable(FragmentParameters.key) as BillingFragmentParameters }

    override fun getLayout() = R.layout.fragment_billing_detail

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTabLayout()
        setupFragment()
        setup()
    }

    private fun setup() {
        if (args.isParent) updateConsultants()
        else listenBillingChanges()
    }

    private fun updateConsultants() {
        Handler().post { updateConsultantsList() }
    }

    private fun listenBillingChanges() {
        uaViewModel.uaViewState.observe(viewLifecycleOwner, uaViewStateObserver)
    }

    private fun setupTabLayout() {
        TabsCreator.with(tlBilling).create(sourceType)
        tlBilling?.onTabSelected {
            AnalyticUtils.detallePedido(it.text.toString())
            updateConsultantsList()
        }
    }

    private fun setupFragment() {
        createFragment(includeManager.getInclude(BILLING_ADVANCEMENT))
    }

    private fun createFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction().replaceOnce(
            R.id.cfvBilling,
            fragment.javaClass.simpleName,
            childFragmentManager
        ) { fragment }.commit()
    }

    private fun updateUa(uaKey: LlaveUA) {
        args.uaKey = uaKey
        updateConsultantsList()
    }

    private fun updateConsultantsList() {
        publish(args.uaKey, (tlBilling?.getSelectedTab()?.tag as? String).orEmpty())
    }

    private fun publish(uaKey: LlaveUA, filter: String) {
        LiveDataBus.publish(
            EventSubject.CONSULTANT_FILTERS,
            ConsultantFilter(sourceType, uaKey, listOf(filter))
        )
    }

    private val uaViewStateObserver = Observer<BillingViewState> { state ->
        when (state) {
            is BillingViewState.UpdateItem -> updateUa(state.uaKey)
        }
    }

    companion object {

        val TAG = BillingDetailFragment::class.java.simpleName

        fun newInstance() = BillingDetailFragment()

    }
}
