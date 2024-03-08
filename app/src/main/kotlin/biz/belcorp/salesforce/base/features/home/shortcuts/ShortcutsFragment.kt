package biz.belcorp.salesforce.base.features.home.shortcuts

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import biz.belcorp.salesforce.base.R
import biz.belcorp.salesforce.base.core.domain.usecases.options.OptionsIdentifier.ACADEMY
import biz.belcorp.salesforce.base.core.domain.usecases.options.OptionsIdentifier.BRIGHT_PATH
import biz.belcorp.salesforce.base.core.domain.usecases.options.OptionsIdentifier.CAMPAIGN_REPORTS
import biz.belcorp.salesforce.base.core.domain.usecases.options.OptionsIdentifier.DEVELOPMENT_PATH
import biz.belcorp.salesforce.base.core.domain.usecases.options.OptionsIdentifier.DIGITAL
import biz.belcorp.salesforce.base.core.domain.usecases.options.OptionsIdentifier.INSPIRE_PROGRAM
import biz.belcorp.salesforce.base.core.domain.usecases.options.OptionsIdentifier.JOIN_US
import biz.belcorp.salesforce.base.core.domain.usecases.options.OptionsIdentifier.MANAGE_BRIGHT_PATH_CHANGE
import biz.belcorp.salesforce.base.core.domain.usecases.options.OptionsIdentifier.MATERIALES_REDES_SOCIALES
import biz.belcorp.salesforce.base.core.domain.usecases.options.OptionsIdentifier.MY_PARTNERS
import biz.belcorp.salesforce.base.core.domain.usecases.options.OptionsIdentifier.POSTULANT_EVALUATION
import biz.belcorp.salesforce.base.core.domain.usecases.options.OptionsIdentifier.PROJECTION
import biz.belcorp.salesforce.base.core.domain.usecases.options.OptionsIdentifier.UCB
import biz.belcorp.salesforce.base.features.utils.decorations.SpaceGridInsideDecoration
import biz.belcorp.salesforce.base.features.webpage.WebPageFragmentArgs
import biz.belcorp.salesforce.base.utils.AnalyticUtils
import biz.belcorp.salesforce.base.utils.navigateTo
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.domain.usecases.browser.WebTopic
import biz.belcorp.salesforce.core.events.ConsumableEvent
import biz.belcorp.salesforce.core.events.EventSubject
import biz.belcorp.salesforce.core.events.LiveDataBus
import biz.belcorp.salesforce.core.events.sync.SyncState
import biz.belcorp.salesforce.core.utils.toast
import kotlinx.android.synthetic.main.fragment_home_shortcuts.rvShorcuts
import org.koin.android.viewmodel.ext.android.viewModel

class ShortcutsFragment : BaseFragment() {

    private val viewModel by viewModel<ShortcutsViewModel>()

    private val adapter by lazy {
        ShortcutsAdapter().apply {
            onClick = {
                openShortcut(it)
            }
        }
    }

    override fun getLayout(): Int {
        return R.layout.fragment_home_shortcuts
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
        initialize()
        observeSyncState()
    }

    private fun setup() {
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        rvShorcuts?.apply {
            isNestedScrollingEnabled = false
            val spanCount = resources.getInteger(R.integer.shortcuts_grid_count)
            addItemDecoration(
                SpaceGridInsideDecoration(
                    requireContext(),
                    spanCount,
                    R.dimen.ds_margin_medium,
                    R.dimen.ds_margin_other_2
                )
            )
            layoutManager = GridLayoutManager(context, spanCount)
            adapter = this@ShortcutsFragment.adapter
        }
    }

    private fun initialize() {
        viewModel.viewState.observe(::getLifecycle, ::observeViewState)
        viewModel.getShortcuts()
    }

    private fun openShortcut(model: ShortcutModel) {
        when (model.code) {
            JOIN_US -> navigateTo(R.id.globalToPostulants)
            ACADEMY -> openWebPage(WebTopic.MY_ACADEMY)
            CAMPAIGN_REPORTS -> openWebPage(WebTopic.DATA_REPORT)
            UCB -> openWebPage(WebTopic.UCB)
            DIGITAL -> navigateTo(R.id.globalToDigitalFragment)
            DEVELOPMENT_PATH -> navigateTo(R.id.globalToDevelopmentPathFragment)
            BRIGHT_PATH -> navigateTo(R.id.actionGoToBrightPathIndicator)
            INSPIRE_PROGRAM -> navigateTo(R.id.actionGoToInspiresProgramFragment)
            MATERIALES_REDES_SOCIALES -> generateExternalUrl(WebTopic.MATERIALES_REDES_SOCIALES)
            POSTULANT_EVALUATION -> navigateTo(R.id.globalToPostulantEvaluation)
            PROJECTION -> navigateTo(R.id.navToCalculator)
            MANAGE_BRIGHT_PATH_CHANGE -> navigateTo(R.id.actionGoToBrightPathChangeLevelFragment)
            MY_PARTNERS -> navigateTo(R.id.actionGoToMyPartnersFragment)
            else -> requireContext().toast(model.text ?: Constant.EMPTY_STRING)
        }
        AnalyticUtils.desarrollaTuNegocio(model.text.toString())
    }

    private fun generateExternalUrl(webTopic: WebTopic) {
        viewModel.getUrlByTopic(webTopic)
    }

    private fun openWebPage(webTopic: WebTopic) {
        val args = WebPageFragmentArgs(webTopic)
        navigateTo(R.id.globalToWebPageFragment, args.toBundle())
    }

    private fun openExternalWebPage(externalUrl: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(externalUrl)))
    }

    private fun observeViewState(state: ShortcutsViewState) {
        when (state) {
            is ShortcutsViewState.Success -> {
                showData(state.data)
            }

            is ShortcutsViewState.LoadExternalWebPage -> {
                openExternalWebPage(state.url)
            }
        }
    }

    private fun showData(data: List<ShortcutModel>) {
        adapter.submitList(data)
    }

    private fun observeSyncState() {
        LiveDataBus.from<ShortcutsFragment>(EventSubject.HOME_SYNC)
            .observe(viewLifecycleOwner, syncStatusObserver)
    }

    private val syncStatusObserver = Observer<ConsumableEvent> {
        it.runAndConsume {
            when (it.value as? SyncState) {
                SyncState.Updated -> viewModel.getShortcuts()
            }
        }
    }

    companion object {

        val TAG = ShortcutsFragment::class.java.simpleName

        fun newInstance(/*personIdentifier: PersonIdentifier*/): ShortcutsFragment {
            return ShortcutsFragment() /*.withPersonIdentifier(personIdentifier)*/
        }
    }
}
