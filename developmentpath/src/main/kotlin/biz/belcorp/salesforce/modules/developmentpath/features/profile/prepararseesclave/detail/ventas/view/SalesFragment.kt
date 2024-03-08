package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.ventas.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import biz.belcorp.mobile.components.core.extensions.visible
import biz.belcorp.salesforce.analytics.core.domain.entities.UserProperties
import biz.belcorp.salesforce.components.utils.decoration.BackgroundPairTintDecorator
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.core.domain.entities.pais.Pais.Companion.countriesIncludeBrightPath
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.features.utils.lazyPersonIdentifier
import biz.belcorp.salesforce.core.features.utils.withPersonIdentifier
import biz.belcorp.salesforce.core.utils.getCompatColor
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.core.utils.textColor
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.FirebaseAnalyticsPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.ventas.adapters.CatalogSaleConsultantAdapter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.ventas.adapters.GainConsultantAdapter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.ventas.model.CatalogSaleConsultantModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.ventas.model.GainConsultantModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.ventas.viewmodel.CatalogSaleConsultantViewModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.ventas.viewmodel.CatalogSaleConsultantViewState
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.ventas.viewmodel.GainConsultantViewModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.ventas.viewmodel.GainConsultantViewState
import kotlinx.android.synthetic.main.fragment_sales.clCatalogSale
import kotlinx.android.synthetic.main.fragment_sales.clGains
import kotlinx.android.synthetic.main.fragment_sales.iclMontoFacturado
import kotlinx.android.synthetic.main.fragment_sales.iclPrimeraFacturacion
import kotlinx.android.synthetic.main.fragment_sales.iclUltimaFacturacion
import kotlinx.android.synthetic.main.fragment_sales.iclVentaPromedioU6C
import kotlinx.android.synthetic.main.fragment_sales.knownBrightPath
import kotlinx.android.synthetic.main.fragment_sales.rvCatalogSale
import kotlinx.android.synthetic.main.fragment_sales.rvGains
import kotlinx.android.synthetic.main.fragment_sales.tvCampaign
import kotlinx.android.synthetic.main.fragment_sales.tvCatalogSaleAmount
import kotlinx.android.synthetic.main.fragment_sales.tvGainAmount
import kotlinx.android.synthetic.main.fragment_sales.tvGainSubTitle
import kotlinx.android.synthetic.main.fragment_sales.tvNoCatalogSale
import kotlinx.android.synthetic.main.fragment_sales.tvNoGains
import kotlinx.android.synthetic.main.inflate_custom_etiqueta_information.view.textViewSubtitulo
import kotlinx.android.synthetic.main.inflate_custom_etiqueta_information.view.textViewTitulo
import org.koin.android.viewmodel.ext.android.viewModel
import biz.belcorp.salesforce.base.R as BaseR

val DIALOG_BRIGHT_PATH = "DIALOG_BRIGHT_PATH"

class SalesFragment : BaseFragment() {

    private val personIdentifier by lazyPersonIdentifier()
    private val catalogSaleViewModel by viewModel<CatalogSaleConsultantViewModel>()
    private val gainViewModel by viewModel<GainConsultantViewModel>()
    private val analyticsPresenter by injectFragment<FirebaseAnalyticsPresenter>()
    private val adapterGain by lazy { GainConsultantAdapter() }
    private val adapterCatalogSale by lazy { CatalogSaleConsultantAdapter() }


    override fun getLayout(): Int = R.layout.fragment_sales

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!isAttached()) return
        setupUI()
        setupViewModel()
        initialize()
    }

    private fun setupUI() {
        setupRecyclerViews()
        setupKnowBrightPath()
    }

    private fun setupRecyclerViews() {
        setupCatalogSale()
        setupGain()
    }

    private fun setupCatalogSale() {
        rvCatalogSale?.apply {
            isNestedScrollingEnabled = false
            setHasFixedSize(true)
            addItemDecoration(
                BackgroundPairTintDecorator(R.color.background_color, BaseR.color.white)
            )
            layoutManager = LinearLayoutManager(requireContext())
            adapter = adapterCatalogSale
        }
    }

    private fun setupGain() {
        rvGains?.apply {
            isNestedScrollingEnabled = false
            setHasFixedSize(true)
            addItemDecoration(
                BackgroundPairTintDecorator(R.color.background_color, BaseR.color.white)
            )
            layoutManager = LinearLayoutManager(requireContext())
            adapter = adapterGain
        }
    }

    private fun setupKnowBrightPath() {
        if(UserProperties.session?.pais?.codigoIso in countriesIncludeBrightPath){
            knownBrightPath.visibility = View.VISIBLE
        }else{
            knownBrightPath.visibility = View.GONE
        }
        knownBrightPath.setOnClickListener {
            BrightPathFragment().withPersonIdentifier(personIdentifier).show(childFragmentManager, DIALOG_BRIGHT_PATH)
        }
    }

    private fun setupViewModel() {
        catalogSaleViewModel.viewState.observe(viewLifecycleOwner, catalogSaleViewStateObserver)
        gainViewModel.viewState.observe(viewLifecycleOwner, gainViewStateObserver)
    }

    private fun initialize() {
        catalogSaleViewModel.getCatalogSaleConsultant(personIdentifier)
        gainViewModel.getGainConsultant(personIdentifier)
        analyticsPresenter.enviarPantallaVentas(
            TagAnalytics.PREPARARSE_ES_CLAVE_VENTAS,
            personIdentifier.role,
            personIdentifier.id
        )
    }

    private fun updateCatalogSales(model: CatalogSaleConsultantModel) {
        tvNoCatalogSale?.visible(!model.hasSales)
        clCatalogSale?.visible(model.hasSales)
        adapterCatalogSale.submitList(model.sales)
        if (model.hasSales) updateCatalogSaleLastCampaign(model)
        updateCatalogSaleInformation(model)
    }

    private fun updateCatalogSaleLastCampaign(model: CatalogSaleConsultantModel) {
        tvCatalogSaleAmount?.text = model.lastSale?.amount.orEmpty()
        tvCampaign?.text = model.subtitle
    }

    private fun updateGains(model: GainConsultantModel) {
        tvNoGains?.visible(!model.hasGains)
        clGains?.visible(model.hasGains)
        adapterGain.submitList(model.gains)
        if (model.hasGains) updateGainLastCampaign(model)
    }

    private fun updateGainLastCampaign(model: GainConsultantModel) {
        tvGainAmount?.text = model.lastGain?.amount.orEmpty()
        tvGainSubTitle?.text = model.subtitle
    }

    private fun updateCatalogSaleInformation(model: CatalogSaleConsultantModel) {
        updateCatalogSaleTitles()
        updateCatalogSaleSubtitles()
        updateCatalogSaleAmounts(model)
    }

    private fun updateCatalogSaleAmounts(model: CatalogSaleConsultantModel) {
        iclMontoFacturado?.textViewSubtitulo?.text = model.billedAmount
        iclVentaPromedioU6C?.textViewSubtitulo?.text = model.amountAverage
        iclPrimeraFacturacion?.textViewSubtitulo?.text = model.firstBillingCampaign
        iclUltimaFacturacion?.textViewSubtitulo?.text = model.lastBillingCampaign
    }

    private fun updateCatalogSaleTitles() {
        val color = getCompatColor(BaseR.color.gray_4)
        iclMontoFacturado?.textViewTitulo?.textColor = color
        iclMontoFacturado?.textViewTitulo?.text = getString(R.string.title_monto_facturado)
        iclVentaPromedioU6C?.textViewTitulo?.textColor = color
        iclVentaPromedioU6C?.textViewTitulo?.text = getString(R.string.title_venta_promedio_u6c)
        iclPrimeraFacturacion?.textViewTitulo?.textColor = color
        iclPrimeraFacturacion?.textViewTitulo?.text = getString(R.string.title_primera_facturacion)
        iclUltimaFacturacion?.textViewTitulo?.textColor = color
        iclUltimaFacturacion?.textViewTitulo?.text = getString(R.string.title_ultima_factuacion)
    }

    private fun updateCatalogSaleSubtitles() {
        val color = getCompatColor(BaseR.color.gray_5)
        iclMontoFacturado?.textViewSubtitulo?.textColor = color
        iclVentaPromedioU6C?.textViewSubtitulo?.textColor = color
        iclPrimeraFacturacion?.textViewSubtitulo?.textColor = color
        iclUltimaFacturacion?.textViewSubtitulo?.textColor = color
    }

    private val catalogSaleViewStateObserver = Observer<CatalogSaleConsultantViewState> {
        when (it) {
            is CatalogSaleConsultantViewState.Success -> updateCatalogSales(it.model)
            is CatalogSaleConsultantViewState.Failure -> Unit
        }
    }

    private val gainViewStateObserver = Observer<GainConsultantViewState> {
        when (it) {
            is GainConsultantViewState.Success -> updateGains(it.model)
            is GainConsultantViewState.Failure -> Unit
        }
    }

    companion object {
        fun newInstance(personIdentifier: PersonIdentifier): SalesFragment {
            return SalesFragment()
                .withPersonIdentifier(personIdentifier)
        }
    }
}
