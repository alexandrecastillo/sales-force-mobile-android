package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.salesconsultant

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.masvendido.marcasycategorias.BrandWithCategoryList
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.masvendido.marcasycategorias.Category


class BrandsAndCategories(
    val brands: List<BrandWithCategoryList>,
    val categories: List<Category>,
    val brandToPromote: String?,
    val topSellingBrand: String?
)
