package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.masvendido.marcasycategorias

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import biz.belcorp.mobile.components.core.extensions.gone
import biz.belcorp.mobile.components.core.extensions.visible
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.features.utils.lazyPersonIdentifier
import biz.belcorp.salesforce.core.features.utils.withPersonIdentifier
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.profile.prepararseesclave.detail.masvendido.marcascategorias.BrandTypeBuilder.getType
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant.FACIAL_TREATMENT_TITLE
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant.FRAGRANCES_TITLE
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant.MAKE_UP_TITLE
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant.OTHERS_TITLE
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.masvendido.marcasycategorias.BrandWithCategoryList
import kotlinx.android.synthetic.main.fragment_marcas_y_categorias.*
import org.koin.android.viewmodel.ext.android.viewModel

class BrandsAndCategoriesFragment : BaseFragment() {

    private val viewModel by viewModel<BrandsAndCategoriesViewModel>()

    private var fragranceProductsList: List<CategoryProductModel> = emptyList()
    private var facialTreatmentProductsList: List<CategoryProductModel> = emptyList()
    private var makeupProductsList: List<CategoryProductModel> = emptyList()
    private var otherProductsList: List<CategoryProductModel> = emptyList()

    private val personIdentifier by lazyPersonIdentifier()

    override fun getLayout(): Int = R.layout.fragment_marcas_y_categorias

    private val observerDataResponse = Observer<BrandsAndCategoriesViewState> { state ->
        when (state) {
            is BrandsAndCategoriesViewState.Success -> doOnSuccess(state.data)
            is BrandsAndCategoriesViewState.Failed -> Unit
        }
    }

    private val sellingDataResponse = Observer<SellingDataViewState> { state ->
        when (state) {
            is SellingDataViewState.Failed -> Unit
            is SellingDataViewState.SellingData -> doOnSellingData(state.data)
        }
    }

    private fun init() {
        initViewModels()
        setupRecyclerView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun doOnSuccess(data: Pair<List<CategoryModel>, List<BrandWithCategoryList>>) =
        with(data) {
            if (!isAttached()) return
            handleMarksVisibility(second)
            drawBarChart(second)
            showCategoriesInfo(first)
        }

    private fun doOnSellingData(data: Pair<String, String>) = with(data) {
        if (!isAttached()) return
//        txtBrandToPromote.text = data.first
//        txtTopSellingBrand.text = data.second
    }

    private fun showCategoriesInfo(categories: List<CategoryModel>) {
        for (category in categories) {
            when (category.name) {
                FRAGRANCES_TITLE -> {
                    tv_fragrance_number.text = category.units.toString()
                    fragranceProductsList = category.categoryProducts
                }

                FACIAL_TREATMENT_TITLE -> {
                    tv_facial_treatment_number.text = category.units.toString()
                    facialTreatmentProductsList = category.categoryProducts
                }

                MAKE_UP_TITLE -> {
                    tv_makeup_number.text = category.units.toString()
                    makeupProductsList = category.categoryProducts
                }

                OTHERS_TITLE -> {
                    tv_others_number.text = category.units.toString()
                    otherProductsList = category.categoryProducts
                }

                else -> {
                    //Do nothing
                }
            }

        }
        setUpFragranceButton()
        setFacialTreatmentButton()
        setUpMakeupButton()
        setUpOthersButton()
    }

    private fun setupRecyclerView() {
        rv_categories?.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = CategoryProductsAdapter()
        }
    }

    private fun fragranceButtonOff() {
        ll_fragrance_container.isSelected = false
        tv_fragrance_number.isSelected = false
        tv_fragrance_title.isSelected = false
    }

    private fun facialTreatmentButtonOff() {
        ll_facial_treatment_container.isSelected = false
        tv_facial_treatment_number.isSelected = false
        tv_facial_treatment_title.isSelected = false

    }

    private fun makeUpButtonOff() {
        ll_makeup_container.isSelected = false
        tv_makeup_number.isSelected = false
        tv_makeup_title.isSelected = false

    }

    private fun otherButtonOff() {
        ll_others_container.isSelected = false
        tv_others_number.isSelected = false
        tv_others_title.isSelected = false
    }

    private fun setUpFragranceButton() {
        ll_fragrance_container.setOnClickListener {
            ll_fragrance_container.isSelected = !ll_fragrance_container.isSelected
            tv_fragrance_number.isSelected = ll_fragrance_container.isSelected
            tv_fragrance_title.isSelected = ll_fragrance_container.isSelected
            if (ll_fragrance_container.isSelected) {
                facialTreatmentButtonOff()
                makeUpButtonOff()
                otherButtonOff()
                if (fragranceProductsList.isEmpty()) {
                    ll_not_success_message.visibility = View.VISIBLE
                    rv_categories.visibility = View.GONE
                } else {
                    ll_not_success_message.visibility = View.GONE
                    rv_categories.visibility = View.VISIBLE
                    (rv_categories?.adapter as? CategoryProductsAdapter)?.update(
                        fragranceProductsList.take(5)
                    )
                }
                cl_categories_list_container.visibility = View.VISIBLE
            } else {
                cl_categories_list_container.visibility = View.GONE
            }
        }
    }

    private fun setFacialTreatmentButton() {
        ll_facial_treatment_container.setOnClickListener {
            ll_facial_treatment_container.isSelected = !ll_facial_treatment_container.isSelected
            tv_facial_treatment_number.isSelected = ll_facial_treatment_container.isSelected
            tv_facial_treatment_title.isSelected = ll_facial_treatment_container.isSelected
            if (ll_facial_treatment_container.isSelected) {
                fragranceButtonOff()
                makeUpButtonOff()
                otherButtonOff()
                if (facialTreatmentProductsList.isEmpty()) {
                    ll_not_success_message.visibility = View.VISIBLE
                    rv_categories.visibility = View.GONE
                } else {
                    ll_not_success_message.visibility = View.GONE
                    rv_categories.visibility = View.VISIBLE
                    (rv_categories?.adapter as? CategoryProductsAdapter)?.update(
                        facialTreatmentProductsList
                    )
                }
                cl_categories_list_container.visibility = View.VISIBLE
            } else {
                cl_categories_list_container.visibility = View.GONE
            }
        }
    }

    private fun setUpMakeupButton() {
        ll_makeup_container.setOnClickListener {
            ll_makeup_container.isSelected = !ll_makeup_container.isSelected
            tv_makeup_number.isSelected = ll_makeup_container.isSelected
            tv_makeup_title.isSelected = ll_makeup_container.isSelected
            if (ll_makeup_container.isSelected) {
                fragranceButtonOff()
                facialTreatmentButtonOff()
                otherButtonOff()
                if (makeupProductsList.isEmpty()) {
                    ll_not_success_message.visibility = View.VISIBLE
                    rv_categories.visibility = View.GONE
                } else {
                    ll_not_success_message.visibility = View.GONE
                    rv_categories.visibility = View.VISIBLE
                    (rv_categories?.adapter as? CategoryProductsAdapter)?.update(
                        makeupProductsList.take(
                            5
                        )
                    )
                }
                cl_categories_list_container.visibility = View.VISIBLE
            } else {
                cl_categories_list_container.visibility = View.GONE
            }
        }
    }

    private fun setUpOthersButton() {
        ll_others_container.setOnClickListener {
            ll_others_container.isSelected = !ll_others_container.isSelected
            tv_others_number.isSelected = ll_others_container.isSelected
            tv_others_title.isSelected = ll_others_container.isSelected
            if (ll_others_container.isSelected) {
                fragranceButtonOff()
                facialTreatmentButtonOff()
                makeUpButtonOff()
                if (otherProductsList.isEmpty()) {
                    ll_not_success_message.visibility = View.VISIBLE
                    rv_categories.visibility = View.GONE
                } else {
                    ll_not_success_message.visibility = View.GONE
                    rv_categories.visibility = View.VISIBLE
                    (rv_categories?.adapter as? CategoryProductsAdapter)?.update(
                        otherProductsList.take(
                            5
                        )
                    )
                }
                cl_categories_list_container.visibility = View.VISIBLE
            } else {
                cl_categories_list_container.visibility = View.GONE
            }
        }
    }

    private fun initViewModels() {
        viewModel.viewState.observe(viewLifecycleOwner, observerDataResponse)
        viewModel.sellingViewState.observe(viewLifecycleOwner, sellingDataResponse)

        viewModel.getBrandsAndCategoriesInfo(personIdentifier.id, personIdentifier.role)
        viewModel.getSellingData(personIdentifier.id, personIdentifier.role)
    }


    private fun drawBarChart(brands: List<BrandWithCategoryList>) {

        when (brands.size) {
            1 -> {
                fillCyzoneChart(brands)
                ll_bars_chart_cyzone_container.visibility = View.VISIBLE
                tv_brand_and_products_cyzone.visibility = View.VISIBLE

                ll_bars_chart_esika_container.visibility = View.GONE
            }

            2 -> {
                fillCyzoneChart(brands)
                fillEsikaChart(brands)

                ll_bars_chart_cyzone_container.visibility = View.VISIBLE
                tv_brand_and_products_cyzone.visibility = View.VISIBLE
                ll_bars_chart_esika_container.visibility = View.VISIBLE
                tv_brand_and_products_esika.visibility = View.VISIBLE
            }

            3 -> {
                fillCyzoneChart(brands)
                fillEsikaChart(brands)
                fillLbelChart(brands)

                ll_bars_chart_esika_container.visibility = View.VISIBLE
                tv_brand_and_products_esika.visibility = View.VISIBLE

                ll_bars_chart_cyzone_container.visibility = View.VISIBLE
                tv_brand_and_products_cyzone.visibility = View.VISIBLE

                ll_bars_chart_lbel_container.visibility = View.VISIBLE
                tv_brand_and_products_lbel.visibility = View.VISIBLE
            }
        }


    }

    private fun fillCyzoneChart(brands: List<BrandWithCategoryList>) {
        //CYZONE
        val itemCyzone = brands[0]
        val totalCyzoneProducts = itemCyzone.units

        val lpCyzoneBarChartFragancias = LinearLayout.LayoutParams(0, 100)
        val lpCyzoneBarChartTratamientoFacial = LinearLayout.LayoutParams(0, 100)
        val lpCyzoneBarChartMaquillaje = LinearLayout.LayoutParams(0, 100)
        val lpCyzoneBarChartOtros = LinearLayout.LayoutParams(0, 100)
        val lpCyzoneBarChartEmpty = LinearLayout.LayoutParams(0, 100)

        if (totalCyzoneProducts == 0) {
            lpCyzoneBarChartFragancias.weight = 0f
            lpCyzoneBarChartTratamientoFacial.weight = 0f
            lpCyzoneBarChartMaquillaje.weight = 0f
            lpCyzoneBarChartOtros.weight = 0f
            lpCyzoneBarChartEmpty.weight = 1f
        } else {
            lpCyzoneBarChartFragancias.weight =
                itemCyzone.categoryList[0].units.toFloat() / totalCyzoneProducts
            lpCyzoneBarChartTratamientoFacial.weight =
                itemCyzone.categoryList[1].units.toFloat() / totalCyzoneProducts
            lpCyzoneBarChartMaquillaje.weight =
                itemCyzone.categoryList[2].units.toFloat() / totalCyzoneProducts
            lpCyzoneBarChartOtros.weight =
                itemCyzone.categoryList[3].units.toFloat() / totalCyzoneProducts
            lpCyzoneBarChartEmpty.weight = 0f
        }


        ll_first_container_cyzone.layoutParams = lpCyzoneBarChartFragancias
        ll_second_container_cyzone.layoutParams = lpCyzoneBarChartTratamientoFacial
        ll_third_container_cyzone.layoutParams = lpCyzoneBarChartMaquillaje
        ll_fourth_container_cyzone.layoutParams = lpCyzoneBarChartOtros
        ll_fifth__empty_container_cyzone.layoutParams = lpCyzoneBarChartEmpty

        tv_brand_and_products_cyzone.text =
            "${getType(itemCyzone.brandType)} (${totalCyzoneProducts})"
    }

    private fun fillEsikaChart(brands: List<BrandWithCategoryList>) {
        //ESIKA
        val itemEsika = brands[1]
        val totalEsikaProducts = itemEsika.units

        val lpEsikaBarChartFragancias = LinearLayout.LayoutParams(0, 100)
        val lpEsikaBarChartTratamientoFacial = LinearLayout.LayoutParams(0, 100)
        val lpEsikaBarChartMaquillaje = LinearLayout.LayoutParams(0, 100)
        val lpEsikaBarChartOtros = LinearLayout.LayoutParams(0, 100)
        val lpEsikaBarChartEmpty = LinearLayout.LayoutParams(0, 100)

        if (totalEsikaProducts == 0) {
            lpEsikaBarChartFragancias.weight = 0f
            lpEsikaBarChartTratamientoFacial.weight = 0f
            lpEsikaBarChartMaquillaje.weight = 0f
            lpEsikaBarChartOtros.weight = 0f
            lpEsikaBarChartEmpty.weight = 1f
        } else {
            lpEsikaBarChartFragancias.weight =
                (itemEsika.categoryList[0].units.toFloat() / totalEsikaProducts)
            lpEsikaBarChartTratamientoFacial.weight =
                (itemEsika.categoryList[1].units.toFloat() / totalEsikaProducts)
            lpEsikaBarChartMaquillaje.weight =
                (itemEsika.categoryList[2].units.toFloat() / totalEsikaProducts)
            lpEsikaBarChartOtros.weight =
                (itemEsika.categoryList[3].units.toFloat() / totalEsikaProducts)
            lpEsikaBarChartEmpty.weight = .0f
        }

        ll_first_container_esika.layoutParams = lpEsikaBarChartFragancias
        ll_second_container_esika.layoutParams = lpEsikaBarChartTratamientoFacial
        ll_third_container_esika.layoutParams = lpEsikaBarChartMaquillaje
        ll_fourth_container_esika.layoutParams = lpEsikaBarChartOtros
        ll_fifth__empty_container_esika.layoutParams = lpEsikaBarChartEmpty

        tv_brand_and_products_esika.text =
            "${getType(itemEsika.brandType)} (${totalEsikaProducts})"
    }

    private fun fillLbelChart(brands: List<BrandWithCategoryList>) {
        //LBEL
        val itemLbel = brands[2]
        val totalLbelProducts = itemLbel.units

        val lpLbelChartFragancias = LinearLayout.LayoutParams(0, 100)
        val lpLbelBarChartTratamientoFacial = LinearLayout.LayoutParams(0, 100)
        val lpLbelBarChartMaquillaje = LinearLayout.LayoutParams(0, 100)
        val lpLbelBarChartOtros = LinearLayout.LayoutParams(0, 100)
        val lpLbelBarChartEmpty = LinearLayout.LayoutParams(0, 100)

        if (totalLbelProducts == 0) {
            lpLbelChartFragancias.weight = 0f
            lpLbelBarChartTratamientoFacial.weight = 0f
            lpLbelBarChartMaquillaje.weight = 0f
            lpLbelBarChartOtros.weight = 0f
            lpLbelBarChartEmpty.weight = 1f
        } else {
            lpLbelChartFragancias.weight =
                itemLbel.categoryList[0].units.toFloat() / totalLbelProducts
            lpLbelBarChartTratamientoFacial.weight =
                itemLbel.categoryList[1].units.toFloat() / totalLbelProducts
            lpLbelBarChartMaquillaje.weight =
                itemLbel.categoryList[2].units.toFloat() / totalLbelProducts
            lpLbelBarChartOtros.weight =
                itemLbel.categoryList[3].units.toFloat() / totalLbelProducts
            lpLbelBarChartEmpty.weight = 0f
        }

        ll_first_container_lbel.layoutParams = lpLbelChartFragancias
        ll_second_container_lbel.layoutParams = lpLbelBarChartTratamientoFacial
        ll_third_container_lbel.layoutParams = lpLbelBarChartMaquillaje
        ll_fourth_container_lbel.layoutParams = lpLbelBarChartOtros
        ll_fifth__empty_container_lbel.layoutParams = lpLbelBarChartEmpty

        tv_brand_and_products_lbel.text =
            "${getType(itemLbel.brandType)} (${totalLbelProducts})"
    }

    private fun handleMarksVisibility(brands: List<BrandWithCategoryList>): Boolean {
        return if (brands.isEmpty()) {
            ll_bars_chart_esika_container?.gone()
            txtSinMarcas?.visible()
            false
        } else {
            ll_bars_chart_esika_container?.visible()
            txtSinMarcas?.gone()
            true
        }
    }


    companion object {
        fun newInstance(personIdentifier: PersonIdentifier) = BrandsAndCategoriesFragment()
            .withPersonIdentifier(personIdentifier)
    }
}
