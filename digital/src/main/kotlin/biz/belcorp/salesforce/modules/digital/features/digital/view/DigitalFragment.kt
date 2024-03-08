package biz.belcorp.salesforce.modules.digital.features.digital.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import biz.belcorp.mobile.components.core.extensions.getColor
import biz.belcorp.mobile.components.core.extensions.gone
import biz.belcorp.mobile.components.core.extensions.tinted
import biz.belcorp.mobile.components.core.extensions.visible
import biz.belcorp.mobile.components.design.selector.bar.model.SelectorModel
import biz.belcorp.mobile.components.design.selector.bar.view.SelectorBar
import biz.belcorp.salesforce.core.base.BaseDialogFragment
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.features.uainfo.UaInfoModel
import biz.belcorp.salesforce.core.utils.actionBar
import biz.belcorp.salesforce.core.utils.excludeFirst
import biz.belcorp.salesforce.modules.digital.R
import biz.belcorp.salesforce.modules.digital.features.constants.DigitalFilterType
import biz.belcorp.salesforce.modules.digital.features.digital.creator.DigitalFragmentCreator
import biz.belcorp.salesforce.modules.digital.features.digital.model.DigitalFragmentParameters
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.android.synthetic.main.fragment_digital_container.*
import kotlinx.android.synthetic.main.item_digital_indicators.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class DigitalFragment : BaseDialogFragment(), SelectorBar.SelectorBarListener {

    private lateinit var uaKey: LlaveUA

    private val viewModel by sharedViewModel<DigitalViewModel>(from = { this })

    override fun getLayout() = R.layout.fragment_digital_container

    override fun onStart() {
        super.onStart()
        fitFullScreen()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!isAdded) return
        setup()
        prepareToolbar()
    }

    private fun setup() {
        setupViewModel()
    }

    override fun onSelected(model: Any?) {
        model?.let {
            val value = model as UaInfoModel
            updateChild(value)
        }
    }

    private fun updateChild(uaModel: UaInfoModel) = with(uaModel) {
        viewModel.updateChildInfo(uaModel)
        createFragments(uaModel.role, isParent = !isThirdPerson)
    }

    private fun prepareToolbar() {
        actionBar(toolbar as MaterialToolbar) {
            title = getString(R.string.digital_toolbar_title)
            setNavigationIcon(biz.belcorp.salesforce.base.R.drawable.ic_backspace)
            navigationIcon?.tinted(getColor(biz.belcorp.salesforce.base.R.color.white))
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
            addOnItemSelectorBarListener(this@DigitalFragment)
            visible()
        }
    }

    private fun createFragments(role: Rol, isParent: Boolean) {
        val params = DigitalFragmentParameters(role, uaKey, isParent)
        DigitalFragmentCreator.init(params)
            .withManager(childFragmentManager)
            .withRol()
            .commit()
    }

    private fun createChildInformation(description: String) {
        context?.let {
            tvDescription?.text = description
            detailChildInfo?.visible()
        }
    }

    private fun hideChildInformation() {
        detailChildInfo?.gone()
    }

    private fun showDetailTitle(title: String) {
        detailChildTitle?.text = title
        detailChildTitle?.visible()
    }

    private fun hideDetailInfoView() {
        detailChildInfo?.gone()
        detailChildTitle?.gone()
    }

    private fun successViewState(state: DigitalViewState.Success) {
        uaKey = state.uaKey
        createFragments(state.role, isParent = true)
        loadUasInformation(state.uas)
    }

    private fun showInfoViewState(state: DigitalViewState.ShowInfo) {
        if (state.type == DigitalFilterType.NONE) {
            hideDetailInfoView()
        } else {
            showDetailTitle(state.detailTitle)
            if (state.asParent) hideChildInformation()
            else createChildInformation(state.childDescription)
        }
    }

    private val uaVieStateObserver = Observer<DigitalViewState> { state ->
        when (state) {
            is DigitalViewState.Success -> successViewState(state)
            is DigitalViewState.ShowInfo -> showInfoViewState(state)
        }
    }
}
