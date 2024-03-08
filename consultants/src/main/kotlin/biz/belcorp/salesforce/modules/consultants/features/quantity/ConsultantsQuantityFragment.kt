package biz.belcorp.salesforce.modules.consultants.features.quantity

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import biz.belcorp.mobile.components.core.extensions.gone
import biz.belcorp.mobile.components.core.extensions.setSafeOnClickListener
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.constants.SourceType
import biz.belcorp.salesforce.core.domain.entities.consultants.ConsultantFilter
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.utils.visible
import biz.belcorp.salesforce.modules.consultants.R
import biz.belcorp.salesforce.modules.consultants.features.searchunified.SearchConsultantDialogFragment
import kotlinx.android.synthetic.main.include_section_go_searching.*
import org.koin.android.viewmodel.ext.android.viewModel

class ConsultantsQuantityFragment : BaseFragment() {

    private val viewModel by viewModel<ConsultantsQuantityViewModel>()

    private val dialogTitleArg by lazy { arguments?.getString(TITLE_DIALOG_KEY).orEmpty() }
    private val buttonTitleArg by lazy { arguments?.getString(TITLE_BUTTON_KEY) }

    private val kpiType by lazy { arguments?.getInt(KPI_TYPE_KEY) ?: SourceType.NONE }
    private val uaKey by lazy { arguments?.getSerializable(UA_KEY) as LlaveUA }

    override fun getLayout() = R.layout.include_section_go_searching

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        initLabel()
    }

    private fun initialize() {
        enableGoConsultantButton(false)
        viewModel.viewState.observe(viewLifecycleOwner, viewStateObserver)
        getConsultantQuantity()
        configureEvents()
    }

    private fun configureEvents() {
        btnGoSearching?.setSafeOnClickListener {
            val dialog =
                SearchConsultantDialogFragment.newInstance(
                    uaKey,
                    dialogTitleArg,
                    kpiType
                )
            dialog.show(childFragmentManager, SearchConsultantDialogFragment::class.java.name)
        }
    }

    private fun getConsultantQuantity() {
        val filter = ConsultantFilter(kpiType, uaKey)
        viewModel.getConsultantsQuantity(filter)
    }

    private fun initLabel() {
        title?.text = buttonTitleArg
    }

    private val viewStateObserver = Observer<ConsultantsQuantityViewState> { state ->
        when (state) {
            is ConsultantsQuantityViewState.Success -> {
                enableGoConsultantButton(state.buttonEnabled)
                drawConsultantsQuantity(state.quantity)
            }
            else -> hideLoading()
        }
    }

    private fun drawConsultantsQuantity(consultantsQuantity: Int) {
        val message = resources.getQuantityString(
            R.plurals.action_go_to_consultants_list,
            consultantsQuantity
        )
        tvConsultantsNumber?.text = message
        tvConsultantsVal?.text = consultantsQuantity.toString()
    }

    private fun enableGoConsultantButton(isEnabled: Boolean) {
        btnGoSearching?.isEnabled = isEnabled
    }

    private fun hideLoading() {
        tvConsultantsVal?.visible()
        loading?.gone()
    }

    companion object {
        const val TITLE_DIALOG_KEY = "TITLE_DIALOG"
        const val TITLE_BUTTON_KEY = "TITLE_BUTTON"
        const val KPI_TYPE_KEY = "KPI_TYPE_KEY"
        const val UA_KEY = "UA_KEY_TAG"
    }

}
