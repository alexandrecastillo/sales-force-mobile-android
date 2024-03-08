package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.masvendido.marcasycategorias

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.masvendido.marcasycategorias.BrandWithCategoryList

open class BrandsAndCategoriesViewState {
    class Success(val data: Pair<List<CategoryModel>, List<BrandWithCategoryList>>) :
        BrandsAndCategoriesViewState()

    class Failed(val message: String) : BrandsAndCategoriesViewState()
}
