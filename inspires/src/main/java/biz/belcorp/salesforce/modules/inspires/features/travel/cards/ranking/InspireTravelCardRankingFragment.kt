package biz.belcorp.salesforce.modules.inspires.features.travel.cards.ranking

import android.os.Bundle
import android.view.View
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.modules.inspires.R
import biz.belcorp.salesforce.modules.inspires.features.travel.cards.BaseInspireTravelCardFragment
import biz.belcorp.salesforce.modules.inspires.model.InspiraRankingModel
import biz.belcorp.salesforce.modules.inspires.model.InspireIndicatorModel
import biz.belcorp.salesforce.modules.inspires.util.enmascararACampaniaC
import kotlinx.android.synthetic.main.fragment_inspire_travel_card_ranking.*
import org.koin.android.ext.android.inject
import kotlin.math.roundToInt

class InspireTravelCardRankingFragment : BaseInspireTravelCardFragment(), InspireTravelCardRankingView {

    private val presenter: InspireTravelCardRankingPresenter by inject()

    override fun getLayout(): Int = R.layout.fragment_inspire_travel_card_ranking

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.create(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.loadValues()
    }

    override fun onDestroy() {
        presenter.destroy()
        super.onDestroy()
    }

    override fun showIndicatorValues(model: InspireIndicatorModel) {
        txvInspireTravelCardRankingLabel?.text = getString(R.string.presentation_card_current_position_label, model.campania.toString().enmascararACampaniaC())
        txvInspireTravelCardDestination?.text = model.destino
        txvInspireTravelCardCategoryTitle?.text = getString(R.string.presentation_card_current_level, model.campania.toString().enmascararACampaniaC())
        txvInspireTravelCardCategoryLabel?.text = model.nivel
        txvInspireTravelCardPointsCampaign?.text = getString(R.string.presentation_card_points_campaign, model.campania.toString().enmascararACampaniaC())
        model.nivel?.let { super.setLevelColor(it, ivwInspireTravelCardCategoryIcon) }
        model.puntaje?.let { txvInspireTravelCardProgressPoints?.text = getString(R.string.presentation_card_points_progress, it) }
        sbrInspireTravelCardPoints?.setOnTouchListener { _, _ -> true }
        sbrInspireTravelCardPoints.progress = getScore(model)
    }

    override fun showRankingPosition(model: InspiraRankingModel) {
        txvInspireTravelCardRankingPosition.text = getString(R.string.presentation_card_current_position, model.puesto)
        super.makeUp(model.flagStatus, ivwInspireTravelCardRankingUpgradeIcon, txvInspireTravelCardRankingPosition)
    }

    private fun getScore(model: InspireIndicatorModel): Int {
        val puntaje = model.puntaje?.toDouble() ?: Constant.EMPTY_DOUBLE
        val puntajeMax = model.puntajeMax?.toDouble() ?: Constant.EMPTY_DOUBLE

        return if (puntaje > Constant.EMPTY_DOUBLE && puntajeMax > Constant.EMPTY_DOUBLE) {
            val progressValue = puntaje.div(puntajeMax) * ONE_HUNDRED
            progressValue.roundToInt()
        } else {
            Constant.NUMBER_ZERO
        }
    }

    companion object {

        const val ONE_HUNDRED = 100

        @JvmStatic
        fun newInstance() = InspireTravelCardRankingFragment()

    }
}
