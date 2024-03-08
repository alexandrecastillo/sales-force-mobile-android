package biz.belcorp.salesforce.modules.inspires.features.dashboard

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.core.utils.imageResource
import biz.belcorp.salesforce.modules.inspires.R
import biz.belcorp.salesforce.modules.inspires.core.domain.entities.Inspira
import biz.belcorp.salesforce.modules.inspires.features.analitycs.AnalyticsInspireViewModel
import biz.belcorp.salesforce.modules.inspires.features.benefits.InspireBenefitsFragment
import biz.belcorp.salesforce.modules.inspires.features.travel.InspireTravelFragment
import biz.belcorp.salesforce.modules.inspires.model.InspireIndicatorModel
import kotlinx.android.synthetic.main.fragment_inspire_header.*
import kotlinx.android.synthetic.main.item_inspire_dashboard_benefits.*
import kotlinx.android.synthetic.main.item_inspire_dashboard_travel.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class InspireDashboardFragment : BaseFragment(),
    InspireDashboardView {

    private val analyticsInspireViewModel by viewModel<AnalyticsInspireViewModel>()
    private val presenter: InspireDashboardPresenter by inject()
    private var travelInspireLoaded: Boolean = false

    override fun getLayout(): Int = R.layout.fragment_inspire_dashboard

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.create(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onPrepare()
        presenter.loadDestinationImage()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && isResumed) {
            sendAnalitycsScreenView()
        }
    }

    override fun onDestroy() {
        presenter.destroy()
        super.onDestroy()
    }

    override fun showValues(model: InspireIndicatorModel) {
        txvInspireDashboardTravelTitle?.text = getString(R.string.dashnoard_inspira_viajes_link_title, model.destino)
        txvHeadName?.text = getString(R.string.dashnoard_inspira_program_title)
    }

    override fun showError() {
        cvwInspireDashboardTravelItem.visibility = View.INVISIBLE
    }

    override fun showCampaign(campaign: String) {
        txvHeaderCampaignInformation?.visibility = View.VISIBLE
        txvHeaderCampaignInformation?.text = campaign
    }

    override fun onResume() {
        super.onResume()
        if (userVisibleHint) {
            sendAnalitycsScreenView()
        }
    }

    override fun setListeners() {
        cvwInspireDashboardTravelItem?.setOnClickListener { loadTravelInspire() }
        cvwInspiraDashboardBeneficiosItem?.setOnClickListener { loadBenefistInspire() }
        iconToBack?.setOnClickListener { activity?.onBackPressed() }
    }

    private fun sendAnalitycsScreenView() {
        if (travelInspireLoaded.not()) {
            analyticsInspireViewModel.sendInspireScreen(TagAnalytics.EVENTO_INSPIRA_SCREEN_VISTA)
        }
    }

    private fun loadBenefistInspire() {
        val fragment: Fragment = InspireBenefitsFragment.newInstance()
        loadFragment(fragment, InspireBenefitsFragment::javaClass.name)
        analyticsInspireViewModel.sendInspireEvent(TagAnalytics.EVENTO_INSPIRA_POST, Inspira(Constant.INSPIRA_TRAVEL, Constant.INSPIRA_PROGRAM))
    }

    override fun showDestinationImage(country: String) {
        ivwInspireDashboardTravelDestination.imageResource = when (country) {
            Pais.BOLIVIA.codigoIso -> R.drawable.im_inspira_bo
            Pais.CHILE.codigoIso -> R.drawable.im_inspira_cl
            Pais.COLOMBIA.codigoIso -> R.drawable.im_inspira_co
            Pais.COSTARICA.codigoIso -> R.drawable.im_inspira_cr
            Pais.ECUADOR.codigoIso -> R.drawable.im_inspira_ec
            Pais.GUATEMALA.codigoIso -> R.drawable.im_inspira_gt
            Pais.PANAMA.codigoIso -> R.drawable.im_inspira_pa
            Pais.PERU.codigoIso -> R.drawable.im_inspira_pe
            Pais.SALVADOR.codigoIso -> R.drawable.im_inspira_sv
            Pais.MEXICO.codigoIso, Pais.DOMINICANA.codigoIso -> R.drawable.im_inspira_default
            else -> R.drawable.im_inspira_default
        }
    }

    private fun loadTravelInspire() {
        val fragment: Fragment = InspireTravelFragment.newInstance()
        (fragment as InspireTravelFragment).setIBackDashboardViajeInspira(object :
            IBackDashboardViajeInspira {
            override fun onBackDashboardInspira() {
                travelInspireLoaded = false
                sendAnalitycsScreenView()
            }
        })
        loadFragment(fragment, InspireTravelFragment::javaClass.name)
        analyticsInspireViewModel.sendInspireEvent(TagAnalytics.EVENTO_INSPIRA_POST, Inspira(Constant.INSPIRA_BENEFITS, Constant.INSPIRA_PROGRAM))
        travelInspireLoaded = true
    }

    private fun loadFragment(fragment: Fragment, name: String) {
        childFragmentManager.beginTransaction().apply {
            setCustomAnimations(biz.belcorp.salesforce.core.R.anim.enter, biz.belcorp.salesforce.core.R.anim.exit)
            add(R.id.item_dashboard_inspira_viajes, fragment, name)
            addToBackStack(name)
        }.commit()

        /*(activity as? OnFragmentTransaction)?.onExecute {
            add(R.id.fragmentContainer, fragment, name)
            addToBackStack(name)
        }*/
    }

    companion object {
        @JvmStatic
        fun newInstance(): InspireDashboardFragment =
            InspireDashboardFragment()
    }

    interface IBackDashboardViajeInspira {
        fun onBackDashboardInspira()
    }

}
