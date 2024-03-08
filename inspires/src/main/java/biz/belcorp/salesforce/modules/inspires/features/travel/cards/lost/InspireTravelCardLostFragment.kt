package biz.belcorp.salesforce.modules.inspires.features.travel.cards.lost

import android.os.Bundle
import android.view.View
import biz.belcorp.salesforce.modules.inspires.R
import biz.belcorp.salesforce.modules.inspires.features.travel.cards.BaseInspireTravelCardFragment
import biz.belcorp.salesforce.modules.inspires.model.InspireIndicatorModel
import kotlinx.android.synthetic.main.fragment_inspire_travel_card_lost.*
import org.koin.android.ext.android.inject

class InspireTravelCardLostFragment : BaseInspireTravelCardFragment(), InspireTravelCardLostView {

    private val presenter: InspireTravelCardLostPresenter by inject()

    override fun getLayout(): Int = R.layout.fragment_inspire_travel_card_lost

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

    override fun showValues(model: InspireIndicatorModel, nextYear: Int?) {
        tvwInspireTravelCardLostLevelLabel?.text = model.nivel
        nextYear?.let { tvwInspireTravelCardLostStateLabel?.text = getString(R.string.header_inspira_content_state_getting_ready, it) }
        model.nivel?.let { super.setLevelColor(it, ivwInspireTravelCardLostLevelIcon) }
    }

    companion object {

        @JvmStatic
        fun newInstance() = InspireTravelCardLostFragment()
    }
}
