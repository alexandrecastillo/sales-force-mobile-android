package biz.belcorp.salesforce.modules.calculator.feature.calculator.header

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.modules.calculator.R
import biz.belcorp.salesforce.modules.calculator.feature.Navigator
import biz.belcorp.salesforce.modules.calculator.util.Constant.IS_CAMPANIA_NAVIDAD
import biz.belcorp.salesforce.modules.calculator.util.Constant.IS_RESULTADO
import kotlinx.android.synthetic.main.fragment_calculator_dashboard_cabecera.btn_back
import kotlinx.android.synthetic.main.fragment_calculator_dashboard_cabecera.tv_title
import kotlinx.android.synthetic.main.fragment_calculator_dashboard_cabecera_navidad.tv_title_sub

class CalculatorCabeceraFragment : BaseFragment(),
    CalculatorCabeceraView {

    private var isCampaniaNavidad = false
    private var isResultado = false
    override var navigator: Navigator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            isCampaniaNavidad = it.getBoolean(IS_CAMPANIA_NAVIDAD, false)
            isResultado = it.getBoolean(IS_RESULTADO, false)
        }
    }

    override fun getLayout(): Int {
        return if (isCampaniaNavidad) {
            R.layout.fragment_calculator_dashboard_cabecera_navidad
        } else {
            R.layout.fragment_calculator_dashboard_cabecera
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayout(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEvents()
        initValues()
    }

    private fun initValues() {
        if (isCampaniaNavidad) {
            initValuesNavidad()
        } else {
            initValuesNoNavidad()
        }
    }

    fun initEvents() {
        btn_back?.setOnClickListener {
            if (!arguments?.getString(Constant.SECTION_PARTNER).isNullOrEmpty()) {
                findNavController().navigate(biz.belcorp.salesforce.base.R.id.actionGoToMyPartnersFragment)
            } else {
                activity?.onBackPressed()
            }
        }

    }

    private fun initValuesNavidad() {
        if (isResultado) {
            tv_title?.text = getString(R.string.calculator_dashboard_titulo_resultado)
            tv_title_sub?.text = getString(R.string.calculator_dashboard_titulo_sub_resultado)
        } else {
            tv_title?.text = getString(R.string.calculator_dashboard_titulo)
            tv_title_sub?.text = getString(R.string.calculator_dashboard_titulo_sub)
        }
    }

    private fun initValuesNoNavidad() {
        if (isResultado) {
            tv_title?.text = getString(R.string.calculator_dashboard_titulo_resultado)
        } else {
            tv_title?.text = getString(R.string.calculator_dashboard_titulo)
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(partnerSection: String? = null): CalculatorCabeceraFragment {
            val fragment = CalculatorCabeceraFragment()
            val bundle = bundleOf(Constant.SECTION_PARTNER to partnerSection)
            fragment.arguments = bundle
            return fragment
        }
    }

}
