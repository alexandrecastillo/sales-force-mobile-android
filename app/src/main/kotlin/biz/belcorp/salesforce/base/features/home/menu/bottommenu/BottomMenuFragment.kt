package biz.belcorp.salesforce.base.features.home.menu.bottommenu

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.get
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import biz.belcorp.salesforce.analytics.core.domain.entities.ScreenTag
import biz.belcorp.salesforce.analytics.features.trackAnalytics
import biz.belcorp.salesforce.base.R
import biz.belcorp.salesforce.base.core.domain.usecases.options.OptionsIdentifier
import biz.belcorp.salesforce.base.features.home.menu.model.MenuOptionModel
import biz.belcorp.salesforce.base.utils.navigateSafe
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.events.ConsumableEvent
import biz.belcorp.salesforce.core.events.EventSubject
import biz.belcorp.salesforce.core.events.LiveDataBus
import biz.belcorp.salesforce.core.events.sync.SyncState
import kotlinx.android.synthetic.main.fragment_bottom_menu.*
import org.koin.android.viewmodel.ext.android.viewModel

class BottomMenuFragment : BaseFragment() {

    private val menuViewModel by viewModel<BottomMenuViewModel>()

    private var optionSelectedCode: Int? = null

    companion object {

        fun newInstance(): BottomMenuFragment {
            return BottomMenuFragment()
        }

    }

    override fun getLayout(): Int {
        return R.layout.fragment_bottom_menu
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        menuViewModel.viewState.observe(viewLifecycleOwner, observeViewState)
        setupNavigationView()
        observeSyncState()
        getOptionsMenu()
    }

    override fun onResume() {
        super.onResume()
        trackAnalytics(ScreenTag.MENU)
    }

    private val observeViewState = Observer { state: BottomMenuViewState ->
        when (state) {
            is BottomMenuViewState.Populate -> setMenuOptions(state.options)
        }
    }

    private fun getOptionsMenu() {
        menuViewModel.getOptionsMenu()
    }

    private fun setupNavigationView() {

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_home -> {
                    findNavController().navigate(R.id.homeFragment)
                    true
                }
                R.id.action_search -> {
                    findNavController().navigate(R.id.navFeatureConsultants)
                    true
                }
                R.id.orders_web -> {
                    findNavController().navigate(R.id.navFeatureOrders)
                    true
                }
                R.id.plus -> {
                    findNavController().navigateSafe(R.id.globalToSideMenuFragment)
                    true
                }
                else -> false
            }
        }
    }


    private fun setMenuOptions(options: List<MenuOptionModel>) {
        optionSelectedCode = bottomNavigationView?.selectedItemId
        options.forEach{menuOptionModel ->
            when (menuOptionModel.code) {
                OptionsIdentifier.HOME -> {
                    bottomNavigationView.menu[0].isVisible = true
                }
                OptionsIdentifier.SEARCH_CONSULTANTS -> {
                    bottomNavigationView.menu[1].isVisible = true
                }
                OptionsIdentifier.ORDERS -> {
                    bottomNavigationView.menu[2].isVisible = true
                }
                else -> {
                    bottomNavigationView.menu[3].isVisible = true
                }
            }
        }

    }

    private fun observeSyncState() {
        LiveDataBus.from<BottomMenuFragment>(EventSubject.HOME_SYNC)
            .observe(viewLifecycleOwner, syncStatusObserver)
    }

    private val syncStatusObserver = Observer<ConsumableEvent> {
        it.runAndConsume {
            when (it.value as? SyncState) {
                SyncState.Updated -> {
                    Log.d("OPTIONS", "Home sync menu bottom")
                    getOptionsMenu()
                }
            }
        }
    }

}
