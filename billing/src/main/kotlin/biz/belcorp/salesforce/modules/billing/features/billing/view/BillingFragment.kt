package biz.belcorp.salesforce.modules.billing.features.billing.view

import android.os.Bundle
import android.text.Spannable
import android.view.View
import androidx.lifecycle.Observer
import biz.belcorp.mobile.components.core.extensions.getColor
import biz.belcorp.mobile.components.core.extensions.gone
import biz.belcorp.mobile.components.core.extensions.tinted
import biz.belcorp.mobile.components.core.extensions.visible
import biz.belcorp.mobile.components.design.selector.bar.model.SelectorModel
import biz.belcorp.mobile.components.design.selector.bar.view.SelectorBar
import biz.belcorp.salesforce.analytics.core.domain.entities.ScreenTag
import biz.belcorp.salesforce.analytics.features.trackAnalytics
import biz.belcorp.salesforce.core.base.BaseDialogFragment
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.features.uainfo.UaInfoModel
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.billing.R
import biz.belcorp.salesforce.modules.billing.features.billing.creator.BillingFragmentCreator
import biz.belcorp.salesforce.modules.billing.features.billing.model.BillingFragmentParameters
import biz.belcorp.salesforce.modules.billing.features.utils.AnalyticUtils
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.android.synthetic.main.fragment_billing.*
import kotlinx.android.synthetic.main.item_billing_indicators.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import biz.belcorp.salesforce.base.R as BaseR
import biz.belcorp.salesforce.components.R as ComponentsR

class BillingFragment : BaseDialogFragment(), SelectorBar.SelectorBarListener {

    private val args by lazy { requireNotNull(arguments?.let { BillingFragmentArgs.fromBundle(it) }) }

    private val viewModel by sharedViewModel<BillingViewModel>(from = { this })

    override fun getLayout() = R.layout.fragment_billing

    override fun onStart() {
        super.onStart()
        fitFullScreen()
    }

    override fun onResume() {
        super.onResume()
        sendAnalytics()
    }

    private fun sendAnalytics() {
        AnalyticUtils.screen()
        trackAnalytics(ScreenTag.BILLING)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!isAdded) return
        setup()
        prepareToolbar()
        createFragments(args.rol, isParent = true)
    }

    private fun setup() {
        setupViewModel()
    }

    override fun onSelected(model: Any?) {
        model?.let {
            val value = model as UaInfoModel
            updateChild(value)
            AnalyticUtils.seleccionaTuZona(model.labelText)
        }
    }

    private fun updateChild(uaModel: UaInfoModel) = with(uaModel) {
        viewModel.updateChildDescription(uaModel)
        createFragments(uaModel.role, isParent = !isThirdPerson)
        viewModel.updateChildDetail(uaModel)
    }

    private fun prepareToolbar() {
        actionBar(toolbar as MaterialToolbar) {
            title = getString(R.string.billing_toolbar_title)
            setNavigationIcon(BaseR.drawable.ic_backspace)
            navigationIcon?.tinted(getColor(BaseR.color.white))
            setNavigationOnClickListener { closeDialog() }
        }
    }

    private fun setupViewModel() {
        viewModel.uaViewState.observe(viewLifecycleOwner, uaVieStateObserver)
        viewModel.getUaInformation()
    }

    private fun loadUasInformation(model: List<SelectorModel>) {
        if (model.isEmpty()) return
        sbZones?.apply {
            headerModel = model.first().apply { isSelected = true }
            dataSet = model.excludeFirst()
            addOnItemSelectorBarListener(this@BillingFragment)
            visible()
        }
    }

    private fun createFragments(role: Rol, isParent: Boolean) {
        val params = BillingFragmentParameters(role, requireNotNull(args.uaKey), isParent)
        BillingFragmentCreator.init(params)
            .withManager(childFragmentManager)
            .withRol()
            .commit()
    }

    private fun createChildInformation(uaName: String, personName: String, isCovered: Boolean) {
        context?.let {
            val uaNameFormatted = it.getString(R.string.text_billing_ua_with_hyphen, uaName)
            tvPersonName?.text = getNameWithSpans(uaNameFormatted, personName, isCovered)
            uaPersonInfo?.visible()
        }
    }

    private fun hideChildInformation() {
        uaPersonInfo?.gone()
    }

    private fun getNameWithSpans(ua: String, name: String, isCovered: Boolean): Spannable {
        val colorNotCovered = requireContext().getCompatColor(ComponentsR.color.colorNotCovered)
        val nameSpannable = if (isCovered) span(name) else color(name, colorNotCovered)
        return spannable {
            bold(ua) + nameSpannable
        }
    }

    private val uaVieStateObserver = Observer<BillingViewState> { state ->
        when (state) {
            is BillingViewState.Success -> loadUasInformation(state.uas)
            is BillingViewState.ShowChildDescription -> with(state) {
                createChildInformation(uaName, personName, isCovered)
            }
            BillingViewState.HideChildDescription -> hideChildInformation()
            is BillingViewState.Failure -> Unit
        }
    }
}
