package biz.belcorp.salesforce.modules.developmentpath.features.profile.charts.ranking.common

import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.features.utils.lazyPersonIdentifier
import biz.belcorp.salesforce.core.features.utils.withPersonIdentifier
import biz.belcorp.salesforce.modules.developmentpath.features.profile.charts.ranking.view.RankingChartsFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.charts.ranking.view.RankingChartsViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel

abstract class BaseGraphicFragment : BaseFragment() {

    protected val viewModel by sharedViewModel<RankingChartsViewModel>(from = { requireParentFragment() })

    protected val personIdentifier by lazyPersonIdentifier()

    protected val contenedorFragment by lazy { parentFragment as? RankingChartsFragment }

    companion object {

        inline fun <reified T : BaseGraphicFragment> newInstance(personIdentifier: PersonIdentifier): T {
            return T::class.java.newInstance()
                .withPersonIdentifier(personIdentifier)
        }
    }
}
