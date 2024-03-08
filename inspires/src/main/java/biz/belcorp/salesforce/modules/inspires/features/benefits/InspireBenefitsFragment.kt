package biz.belcorp.salesforce.modules.inspires.features.benefits

import android.os.Bundle
import android.view.View
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.modules.inspires.R
import biz.belcorp.salesforce.modules.inspires.model.InspireIndicatorModel
import biz.belcorp.salesforce.modules.inspires.util.enmascararACampaniaC
import kotlinx.android.synthetic.main.item_inspire_benefits_header.*
import org.koin.android.ext.android.inject


class InspireBenefitsFragment : BaseFragment(), InspireBenefitsView {

    private val presenter: InspireBenefitsPresenter by inject()

    override fun getLayout(): Int = R.layout.fragment_inspire_benefits

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.loadValues()
    }

    override fun showValues(model: InspireIndicatorModel) {
        txvInspireBenefitsHeaderLevelTitle?.text = getString(R.string.presentation_card_current_level, model.campania.toString().enmascararACampaniaC())
        txvInspireBenefitsHeaderLevelLabel?.text = model.nivel
    }

    companion object {
        fun newInstance(): InspireBenefitsFragment = InspireBenefitsFragment()
    }
}
