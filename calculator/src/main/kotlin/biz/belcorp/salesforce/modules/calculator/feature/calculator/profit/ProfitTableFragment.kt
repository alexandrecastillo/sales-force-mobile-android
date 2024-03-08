package biz.belcorp.salesforce.modules.calculator.feature.calculator.profit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.domain.entities.session.Sesion
import biz.belcorp.salesforce.core.utils.NonScrollableLayoutManager
import biz.belcorp.salesforce.core.utils.doubleOrIntFormatWithCommas
import biz.belcorp.salesforce.core.utils.withArguments
import biz.belcorp.salesforce.modules.calculator.R
import biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.projection.model.CalculadoraMontoFijoModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_profit_table.*

class ProfitTableFragment : BottomSheetDialogFragment() {

    var list: List<CalculadoraMontoFijoModel> = emptyList()
    var session: Sesion? = null
    private var symbol: String = Constant.EMPTY_STRING
    private var iso: String = Constant.EMPTY_STRING

    private var profitTableAdapter: ProfitTableAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_profit_table, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getArgs()
        initViews()
    }

    private fun getArgs() {
        arguments?.let { bundle ->
            symbol = bundle.getString(KEY_SYMBOL, Constant.EMPTY_STRING)
            iso = bundle.getString(KEY_COUNTRYISO, Constant.EMPTY_STRING)
        }
    }

    private fun initViews() {
        tvInformation?.text = HtmlCompat.fromHtml(
            getString(
                R.string.calculator_comisiona_exitosa,
                session?.nivelActual
            ),
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )

        profitTableAdapter = ProfitTableAdapter()
        profitTableAdapter?.setElements(list)
        profitTableAdapter?.setSymbol(symbol)
        recyclerView2?.layoutManager = NonScrollableLayoutManager()
            .withContext(context)
            .linearVertical()
        recyclerView2?.adapter = profitTableAdapter

        btnAccept?.setOnClickListener { this.dismiss() }

        showInformationNotSuccess()

        showInformationCampaign()
    }

    private fun showInformationNotSuccess() {
        val bonus = list.map { it.bonoNoExitosa }.sum() / list.size
        tvInformationNoExitosa?.text = HtmlCompat.fromHtml(
            getString(
                R.string.calculator_comisiona_no_exitosa,
                session?.nivelActual, bonus.doubleOrIntFormatWithCommas()
            ),
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
    }

    private fun showInformationCampaign() {
        tvMessage?.text = HtmlCompat.fromHtml(
            getString(R.string.calculator_mx_recuperacion_pedido),
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
    }

    companion object {

        const val KEY_SYMBOL = "symbol"
        const val KEY_COUNTRYISO = "countryIso"

        @JvmStatic
        fun newInstance(symbol: String?, countryiso: String): ProfitTableFragment {
            return ProfitTableFragment().withArguments(
                KEY_SYMBOL to symbol,
                KEY_COUNTRYISO to countryiso
            )
        }
    }
}
