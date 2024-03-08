package biz.belcorp.salesforce.modules.brightpath.features.header

import android.os.Bundle
import android.view.View
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.utils.invisible
import biz.belcorp.salesforce.core.utils.visible
import biz.belcorp.salesforce.modules.brightpath.R
import biz.belcorp.salesforce.modules.brightpath.features.container.detail.BrightPathIndicatorDetailFragment
import kotlinx.android.synthetic.main.fragment_level_indicator_header.*
import kotlinx.android.synthetic.main.fragment_level_indicator_table.*
import org.koin.android.ext.android.inject

class BrightPathHeaderKpiFragment : BaseFragment(), BrightPathHeaderKpiView {

    override fun getLayout(): Int = R.layout.fragment_level_indicator_header

    private val presenter: BrightPathHeaderKpiPresenter by inject()

    companion object {
        fun newInstance() = BrightPathHeaderKpiFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.create(this)
        presenter.getSeData()

        checkEndPeriodView()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun showHeader(ordersGoalValue: String) {
        clBrightPathHeaderTable?.visible()
        tvLevelIndicatorDetailMessage?.text = getString(
            R.string.text_bright_path_achieved_prev_campaign,
            ordersGoalValue
        )
    }

    override fun showTitle(currentCampaign: String) {
        tvLevelIndicatorDetailTitle?.text = getString(
            R.string.text_bright_path_quantity_has_changed,
            currentCampaign
        )
    }

    override fun showPrevCampaignText(totalPrevCampaign: String) {
        tvLevelIndicatorTableVal12?.text = totalPrevCampaign
    }

    override fun showPrevCampaignValues(values: Array<String>) {
        values.apply {
            tvLvlN2prevCampaignVal?.text = get(0)
            tvLvlN3prevCampaignVal?.text = get(1)
            tvLvlN4prevCampaignVal?.text = get(2)
            tvLvlN5prevCampaignVal?.text = get(3)
        }
    }

    override fun showCurrentPeriodText(currentPeriod: String) {
        tvLevelIndicatorTableVal11?.text = currentPeriod
    }

    override fun showCurrentPeriodValues(accumulatedPrevPeriodArgs: Array<String>) {
        accumulatedPrevPeriodArgs.apply {
            tvLvlN2PrevPeriodVal?.text = get(0)
            tvLvlN3PrevPeriodVal?.text = get(1)
            tvLvlN4PrevPeriodVal?.text = get(2)
            tvLvlN5PrevPeriodVal?.text = get(3)
        }
    }

    private fun checkEndPeriodView() {
        arguments?.getInt(BrightPathIndicatorDetailFragment.ARG_BEAUTY_CONSULTANT_TYPE_LIST)?.let {
            if (it == 4) {
                tvLevelIndicatorDetailTitle.visibility = View.GONE
                tvLevelIndicatorDetailMessage.visibility = View.GONE
                layoutIndicators.visibility = View.GONE
            } else {
                tvLevelIndicatorDetailTitle.visibility = View.VISIBLE
                tvLevelIndicatorDetailMessage.visibility = View.VISIBLE
            }
        } ?: run {
            tvLevelIndicatorDetailTitle.visibility = View.VISIBLE
            tvLevelIndicatorDetailMessage.visibility = View.VISIBLE
        }
    }
}
