package biz.belcorp.salesforce.modules.kpis.features.kpidetail.detailbutton

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import biz.belcorp.mobile.components.core.extensions.visible
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.base.FragmentParameters
import biz.belcorp.salesforce.core.constants.KpiType
import biz.belcorp.salesforce.core.include.Include
import biz.belcorp.salesforce.core.include.IncludeManager
import biz.belcorp.salesforce.core.utils.visible
import biz.belcorp.salesforce.core.utils.withArguments
import biz.belcorp.salesforce.modules.kpis.R
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.detailbutton.model.DetailButtonType
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.model.KpiFragmentParameters
import biz.belcorp.salesforce.modules.kpis.utils.AnalyticUtils
import biz.belcorp.salesforce.modules.kpis.utils.navigateToBilling
import kotlinx.android.synthetic.main.fragment_detail_button.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class DetailButtonFragment : BaseFragment() {

    private val viewModel by viewModel<DetailButtonViewModel>()

    private val includeManager by inject<IncludeManager>()

    private val params by lazy {
        arguments?.getSerializable(FragmentParameters.key) as KpiFragmentParameters
    }

    override fun getLayout() = R.layout.fragment_detail_button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        viewModel.enableButton(params.kpiType)
    }

    private fun initObservers() {
        viewModel.viewState.observe(viewLifecycleOwner, viewStateObserver)
    }

    private val viewStateObserver = Observer { state: DetailButtonViewState ->
        when (state) {
            is DetailButtonViewState.Success -> showDetailButton(state.title, state.type)
        }
    }

    private fun showDetailButton(title: String, @DetailButtonType type: Int) {
        if (type == DetailButtonType.CONSULTANT) {
            showConsultantButton(title)
        } else {
            showBillingButton(title)
        }
    }

    private fun showConsultantButton(title: String) {
        initViews(false)
        clButtonDetailContainer?.visible()
        val consultantButton = consultantButton(title)
        childFragmentManager
            .beginTransaction()
            .replace(flConsultantsQuantity.id, consultantButton)
            .commitAllowingStateLoss()
    }

    private fun consultantButton(title: String): Fragment {
        return includeManager.getInclude(Include.CONSULTANTS_QUANTITY)
            .withArguments(
                TITLE_DIALOG_KEY to title,
                TITLE_BUTTON_KEY to title,
                KPI_TYPE_KEY to KpiType.toSourceType(params.kpiType),
                UA_KEY_TAG to params.uaKey
            )
    }

    private fun showBillingButton(title: String) {
        initViews(true)
        clButtonDetailContainer?.visible()
        arguments?.let {
            titleButton?.text = title
            btnGoBilling?.setOnClickListener {
                navigateToBilling(params.uaKey)
                AnalyticUtils.avanceFacturacion(params.kpiType)
            }
        }
    }

    private fun initViews(isBillingEnabled: Boolean) {
        titleButton?.visible(isBillingEnabled)
        btnGoBilling?.visible(isBillingEnabled)
        flConsultantsQuantity.visible(!isBillingEnabled)
    }

    companion object {
        val TAG = DetailButtonFragment::javaClass.javaClass.simpleName
        private const val UA_KEY_TAG = "UA_KEY_TAG"
        private const val TITLE_DIALOG_KEY = "TITLE_DIALOG"
        private const val TITLE_BUTTON_KEY = "TITLE_BUTTON"
        private const val KPI_TYPE_KEY = "KPI_TYPE_KEY"

        fun newInstance() = DetailButtonFragment()
    }
}
