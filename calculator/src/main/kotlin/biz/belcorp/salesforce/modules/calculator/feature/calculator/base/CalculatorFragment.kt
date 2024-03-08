package biz.belcorp.salesforce.modules.calculator.feature.calculator.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import biz.belcorp.salesforce.core.base.ScopedFragment
import biz.belcorp.salesforce.core.constants.Constant.SECTION_PARTNER
import biz.belcorp.salesforce.core.features.broadcast.SenderEstadoProgress
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.core.utils.injectScoped
import biz.belcorp.salesforce.modules.calculator.R
import biz.belcorp.salesforce.modules.calculator.feature.calculator.dashboard.CalculatorDashboardFragment
import biz.belcorp.salesforce.modules.calculator.feature.calculator.result.CalculatorResultFragment
import biz.belcorp.salesforce.modules.calculator.model.CalculatingResultModel
import org.koin.android.viewmodel.ext.android.viewModel
import biz.belcorp.salesforce.core.R as coreR

class CalculatorFragment : ScopedFragment() {

    private val senderEstadoProgress: SenderEstadoProgress by injectFragment()
    private val stateObserver by injectScoped<NavigationStateObserver>(
        CALCULATOR_SCOPE,
        SHARED_NAV
    )
    private val calculatorViewModel by viewModel<CalculatorViewModel>()

    override fun getLayout(): Int = R.layout.fragment_calculator
    override fun getScopeName() =
        CALCULATOR_SCOPE

    var calculatorAttributesModel: CalculatorAttributesModel? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showDashboard()
    }

//    private fun initViewModels() {
//        stateObserver.nav.observe(viewLifecycleOwner, calculatorResultViewState)
//        calculatorViewModel.viewState.observe(viewLifecycleOwner, observerResultResponse)
//        calculatorViewModel.fetchCampaign()
//        showProgress()
//    }

//    private val calculatorResultViewState = Observer<ConsumableEvent> {
//        it.runAndConsume {
//            when (val state = it.value as NavigatorResultViewState) {
//                is NavigatorResultViewState.RESULT -> {
//                    showResultFragment(state.params, addToBackStack = true, isCalculatorSaved = false)
//                }
//                is NavigatorResultViewState.RESET -> {
//                    clearBackStack()
//                    showDashboard()
//                }
//                is NavigatorResultViewState.CLEAR -> {
//                    clearBackStack()
//                    showResultAsMainScreen(state.params)
//                }
//            }
//            hideProgress()
//        }
//    }

    private fun showResultAsMainScreen(params: CalculatingResultModel) {
        childFragmentManager.beginTransaction().apply {
            calculatorAttributesModel?.let { model ->
                val frag = CalculatorResultFragment.newInstance(
                    params,
                    true,
                    model.isChristmas,
                    model.isDiamond
                )
                replace(R.id.calculator_container, frag, frag.tag)
            }
        }.commit()
    }

    private fun clearBackStack() {
        childFragmentManager.popBackStack(
            null,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }

    private fun showResultFragment(
        result: CalculatingResultModel,
        addToBackStack: Boolean,
        isCalculatorSaved: Boolean
    ) {
        calculatorAttributesModel?.let { model ->
            loadCustomFragment(
                CalculatorResultFragment.newInstance(
                    result,
                    isCalculatorSaved,
                    model.isChristmas,
                    model.isDiamond
                ),
                addToBackStack
            )
        }
    }

//    private val observerResultResponse = Observer<CalculatorViewState> { state ->
//        when (state) {
//            is CalculatorViewState.Success -> {
//                calculatorAttributesModel = state.attributesModel
//                state.result?.let { result ->
//                    showResultFragment(result, addToBackStack = false, isCalculatorSaved = true)
//                } ?: run {
//                    showDashboard()
//                }
//            }
//            is CalculatorViewState.Failed -> Toast.makeText(
//                context,
//                state.message,
//                Toast.LENGTH_LONG
//            ).show()
//        }
//        hideProgress()
//    }

    private fun showDashboard() {
        if (!isAttached()) return
        childFragmentManager.beginTransaction().apply {
            replace(R.id.calculator_container, CalculatorDashboardFragment.newInstance(arguments?.getString(SECTION_PARTNER)))
        }.commit()

    }

    private fun showProgress() {
        senderEstadoProgress.mostrarProgress()
    }

    private fun hideProgress() {
        senderEstadoProgress.ocultarProgress()
    }

    private fun loadCustomFragment(frag: Fragment, addToBackStack: Boolean) {
        childFragmentManager.beginTransaction().apply {
            setCustomAnimations(coreR.anim.enter, coreR.anim.exit)
            add(R.id.calculator_container, frag, frag.tag)
            if (addToBackStack) {
                addToBackStack(null)
            }
        }.commit()
    }
}
