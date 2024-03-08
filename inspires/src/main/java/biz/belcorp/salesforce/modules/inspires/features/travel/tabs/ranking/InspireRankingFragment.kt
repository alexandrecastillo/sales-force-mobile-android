package biz.belcorp.salesforce.modules.inspires.features.travel.tabs.ranking

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.getColor
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.core.utils.imageResource
import biz.belcorp.salesforce.core.utils.textColor
import biz.belcorp.salesforce.modules.inspires.R
import biz.belcorp.salesforce.modules.inspires.features.analitycs.AnalyticsInspireViewModel
import biz.belcorp.salesforce.modules.inspires.model.InspiraRankingModel
import kotlinx.android.synthetic.main.fragment_inspire_travel_page_ranking.*
import kotlinx.android.synthetic.main.item_inspire_travel_ranking.view.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class InspireRankingFragment : BaseFragment(), InspireRankingView {

    private val analyticsInspireViewModel by viewModel<AnalyticsInspireViewModel>()
    private val presenter: InspireRankingPresenter by inject()

    override fun getLayout(): Int = R.layout.fragment_inspire_travel_page_ranking

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.create(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onPrepare()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && isResumed) {
            sendAnalitycsScreenView()
        }
    }

    override fun onResume() {
        super.onResume()
        if (userVisibleHint) {
            sendAnalitycsScreenView()
        }
    }

    override fun onDestroy() {
        presenter.destroy()
        super.onDestroy()
    }

    private fun sendAnalitycsScreenView() {
        analyticsInspireViewModel.sendInspireScreen(TagAnalytics.EVENTO_INSPIRA_SCREEN_VISTA_RANKING)
    }

    override fun showValues(list: List<InspiraRankingModel>, topeRanking: Int?) {

        for (item in list) {

            val rootView = when (item.bloque) {
                BLOCK_ONE -> llyRankingBlockOne
                BLOCK_TWO -> llyRankingBlockTwo
                BLOCK_THREE -> llyRankingBlockThree
                else -> llyRankingBlockThree
            }

            item.flagEsUsuario?.let { isUser ->
                addCustomView(isUser, item, rootView, topeRanking)
            }
        }
    }

    private fun addCustomView(isUser: Boolean, item: InspiraRankingModel, rootView: ViewGroup, topeRanking: Int?) {
        val view = LayoutInflater.from(context).inflate(if (isUser) R.layout.item_selected_inspira_ranking else R.layout.item_inspire_travel_ranking, rootView, false)
        view.txvInspiraRankingPositionNumber?.text = getString(R.string.ranking_position, item.puesto)
        view.txvInspiraRankingUsername?.text = item.usuario?.toLowerCase(Locale.getDefault())?.capitalize()
        view.txvInspiraRankingPoints?.text = (activity as Context).getString(R.string.points_label, item.puntaje)
        makeUpValues(item.flagStatus, isUser, view.ivwInspiraRankingUpgradeIcon, view.txvInspiraRankingPositionNumber)

        if (isUser) {
            val win = item.puesto <= (topeRanking ?: Constant.NUMBER_ZERO)
            tvwInspiraRankingHeaderTitle?.text = if (win) getString(R.string.progress_top_header, topeRanking) else getString(R.string.progress_out_header)
            ivwInspiraRankingHeaderIcon?.imageResource = if (win) R.drawable.ic_medal else R.drawable.ic_inspira_lose
            view.txvInspiraRankingPositionNumber?.textColor = getColor(activity as Context, R.color.white)
        }

        when (item.bloque) {
            BLOCK_ONE -> llyRankingBlockOne?.addView(view)
            BLOCK_TWO -> llyRankingBlockTwo?.addView(view)
            BLOCK_THREE -> llyRankingBlockThree?.addView(view)
        }
    }

    private fun makeUpValues(flagStatus: String?, isUser: Boolean, icon: ImageView, number: TextView) {
        when (flagStatus) {
            UPGRADE -> {
                icon.imageResource = R.drawable.ic_inspira_arrow_up
                number.textColor = getColor(activity as Context, if (isUser) R.color.white else R.color.inspira_green)
            }
            NO_CHANGE -> {
                icon.visibility = View.GONE
                number.textColor = getColor(activity as Context, if (isUser) R.color.white else R.color.black)
            }
            DOWNGRADE -> {
                icon.imageResource = R.drawable.ic_inspira_arrow_down
                number.textColor = getColor(activity as Context, if (isUser) R.color.white else R.color.red)
            }
        }
    }

    companion object {

        const val UPGRADE: String = "S"
        const val NO_CHANGE: String = "-"
        const val DOWNGRADE: String = "B"

        const val BLOCK_ONE: Int = 1
        const val BLOCK_TWO: Int = 2
        const val BLOCK_THREE: Int = 3

        @JvmStatic
        fun newInstance(): InspireRankingFragment = InspireRankingFragment()
    }
}
