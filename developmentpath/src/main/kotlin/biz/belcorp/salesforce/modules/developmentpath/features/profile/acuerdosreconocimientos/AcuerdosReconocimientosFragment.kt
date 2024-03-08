package biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.features.utils.lazyPersonIdentifier
import biz.belcorp.salesforce.core.features.utils.withPersonIdentifier
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.profile.ProfileViewModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.ProfileViewState
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.acuerdos.campaniaactual.AcuerdosFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.actual.ComportamientosFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.progreso.ProgresoComportamientosFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.redireccion.IrAReconocerFragment
import kotlinx.android.synthetic.main.fragment_acuerdos_reconocimientos.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class AcuerdosReconocimientosFragment : BaseFragment(),
    AcuerdosReconocimientosView {

    private val profileViewModel by sharedViewModel<ProfileViewModel>(from = { requireParentFragment() })

    private val personIdentifier by lazyPersonIdentifier()

    private val irAReconocerFragment
        get() = IrAReconocerFragment.newInstance(personIdentifier.id, personIdentifier.role)



    //C-1 Campaign

    private val comportamientosUCFragment
        get() = ProgresoComportamientosFragment.newInstance(
            personIdentifier.id,
            personIdentifier.role
        )

    private val agreementLatestCampaignFragment
        get() = AcuerdosFragment.newInstance(personIdentifier.id, personIdentifier.role, AcuerdosFragment.LATEST_CAMPAIGN_AGREEMENT_FRAGMENT)

    private val behaviourLatestCampaignFragment
        get() = ComportamientosFragment.newInstance(personIdentifier.id, personIdentifier.role)


    //C-2 Campaign

    private val agreementLatestMinusOneCampaignFragment
        get() = AcuerdosFragment.newInstance(personIdentifier.id, personIdentifier.role,  AcuerdosFragment.LATEST_MINUS_ONE_CAMPAIGN_AGREEMENT_FRAGMENT)

    private val behaviourLatestMinusOneCampaignFragment
        get() = ComportamientosFragment.newInstance(personIdentifier.id, personIdentifier.role)

    //C-3 Campaign

    private val agreementLatestMinusTwoCampaignFragment
        get() = AcuerdosFragment.newInstance(personIdentifier.id, personIdentifier.role,  AcuerdosFragment.LATEST_MINUS_TWO_CAMPAIGN_AGREEMENT_FRAGMENT)

    private val behaviourLatestMinusTwoCampaignFragment
        get() = ComportamientosFragment.newInstance(personIdentifier.id, personIdentifier.role)


    private val presenter by injectFragment<AcuerdosReconocimientosPresenter>()

    override fun getLayout(): Int = R.layout.fragment_acuerdos_reconocimientos

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        incrustarFragments()
        presenter.getLastCampaignsSync(personIdentifier.id, personIdentifier.role)
        setupViewModel()
    }

    private fun setupViewModel() {
        profileViewModel.viewState.observe(viewLifecycleOwner, viewStateObserver)
    }

    private val viewStateObserver = Observer<ProfileViewState> {
        when (it) {
            is ProfileViewState.SuccessSync -> incrustarFragments()
        }
    }

    private fun incrustarFragments() {
        childFragmentManager.beginTransaction()
            .replace(R.id.frameLayoutIrAReconocer, irAReconocerFragment)
            .replace(R.id.frameLayoutReconocimientosUC, comportamientosUCFragment)
            .replace(R.id.frameLayoutAgreementCurrentCampaign, agreementLatestCampaignFragment)
            .replace(R.id.frameLayoutAgreementCurrentMinusOneCampaign, agreementLatestMinusOneCampaignFragment)
            .replace(R.id.frameLayoutAgreementCurrentMinusTwoCampaign, agreementLatestMinusTwoCampaignFragment)
            .replace(R.id.frameLayoutRecognitionsCurrentCampaign, behaviourLatestCampaignFragment)
            .commit()
    }


    companion object {

        fun newInstance(personIdentifier: PersonIdentifier) = AcuerdosReconocimientosFragment()
            .withPersonIdentifier(personIdentifier)
    }

    override fun setLatestThreeCampaignAgreements(campaigns: List<Campania>) {
        if(campaigns.isNotEmpty()){

            if(campaigns.size == 3){
                headerAgreementLastCampaign?.hitTitle = context?.getString(
                    R.string.perfil_acuerdos_campania_actual,
                    campaigns[0].nombreCorto)

                headerAgreementLastMinusOneCampaign?.hitTitle = context?.getString(
                    R.string.perfil_acuerdos_campania_actual,
                    campaigns[1].nombreCorto)

                headerAgreementLastMinusTwoCampaign?.hitTitle = context?.getString(
                    R.string.perfil_acuerdos_campania_actual,
                    campaigns[2].nombreCorto)
            }

            if(campaigns.size == 2){
                headerAgreementLastCampaign?.hitTitle = context?.getString(
                    R.string.perfil_acuerdos_campania_actual,
                    campaigns[0].nombreCorto)

                headerAgreementLastMinusOneCampaign?.hitTitle = context?.getString(
                    R.string.perfil_acuerdos_campania_actual,
                    campaigns[1].nombreCorto)

                headerAgreementLastMinusTwoCampaign.visibility = View.GONE
            }

            if(campaigns.size == 1){
                headerAgreementLastCampaign?.hitTitle = context?.getString(
                    R.string.perfil_acuerdos_campania_actual,
                    campaigns[0].nombreCorto)

                headerAgreementLastMinusOneCampaign.visibility = View.GONE
                headerAgreementLastMinusTwoCampaign.visibility = View.GONE
            }

        }
    }
}
