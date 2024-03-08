package biz.belcorp.salesforce.components.features.selector

import android.os.Bundle
import android.text.Spannable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.content.ContextCompat
import biz.belcorp.mobile.components.core.extensions.visible
import biz.belcorp.mobile.components.design.selector.bar.model.SelectorModel
import biz.belcorp.mobile.components.design.selector.bar.view.SelectorBar
import biz.belcorp.salesforce.components.R
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.core.utils.visible
import kotlinx.android.synthetic.main.fragment_selector_bar_shared.*

abstract class BaseSelectorBarFragment : BaseFragment(), SelectorBar.SelectorBarListener {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val contextThemeWrapper = ContextThemeWrapper(activity, getFragmentTheme())
        val localInflater = inflater.cloneInContext(contextThemeWrapper)
        return super.onCreateView(localInflater, container, savedInstanceState)
    }

    override fun getLayout() = R.layout.fragment_selector_bar_shared

    abstract fun getFragmentTheme(): Int

    protected fun createSelectorZoneData(list: List<SelectorModel>) {
        sbZones?.apply {
            visible()
            dataSet = list
            addOnItemSelectorBarListener(this@BaseSelectorBarFragment)
        }
    }

    protected fun createZoneInformation(uaName: String, personName: String, isCovered: Boolean) {
        context?.let {
            val uaNameFormatted = it.getString(R.string.text_kpi_ua_with_hyphen, uaName)
            tvConsultantName?.text = getConsultantNameWithSpans(uaNameFormatted, personName, isCovered)
            groupDescription?.visible()
        }
    }

    private fun getConsultantNameWithSpans(
        uaNameFormatted: String,
        personName: String,
        isCovered: Boolean
    ): Spannable {
        val colorNotCovered = ContextCompat.getColor(requireContext(), R.color.colorNotCovered)
        val nameSpannable = if (isCovered) span(personName) else color(personName, colorNotCovered)
        return spannable {
            bold(uaNameFormatted) + nameSpannable
        }
    }

    protected fun setSelectorTitle(title: String) {
        tvTitle?.apply {
            text = title
            visible(title.isNotEmpty())
        }
    }

    abstract fun onItemBarSelected(model: Any?)

    override fun onSelected(model: Any?) {
        onItemBarSelected(model)
    }
}
