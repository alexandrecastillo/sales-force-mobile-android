package biz.belcorp.salesforce.modules.inspires.features.travel.cards.limited

import android.os.Bundle
import android.view.View
import biz.belcorp.salesforce.modules.inspires.R
import biz.belcorp.salesforce.modules.inspires.features.travel.cards.BaseInspireTravelCardFragment
import biz.belcorp.salesforce.modules.inspires.model.InspireIndicatorModel
import biz.belcorp.salesforce.modules.inspires.util.enmascararACampaniaC
import kotlinx.android.synthetic.main.fragment_inspire_travel_card_limited.*
import org.koin.android.ext.android.inject

class InspireTravelCardLimitedFragment : BaseInspireTravelCardFragment(), InspireTravelCardLimitedView {

    private val presenter: InspireTravelCardLimitedPresenter by inject()

    override fun getLayout(): Int = R.layout.fragment_inspire_travel_card_limited

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

    override fun showValues(model: InspireIndicatorModel) {
        txvInspireTravelCardCategoryTitle?.text = getString(R.string.presentation_card_current_level, model.campania.toString().enmascararACampaniaC())
        txvInspireTravelCardDestination?.text = model.destino
        txvInspireTravelCardCategoryLabel?.text = model.nivel
        model.nivel?.let { super.setLevelColor(it, ivwInspireTravelCardCategoryIcon) }
    }

    companion object {

        @JvmStatic
        fun newInstance() = InspireTravelCardLimitedFragment()
    }
}
