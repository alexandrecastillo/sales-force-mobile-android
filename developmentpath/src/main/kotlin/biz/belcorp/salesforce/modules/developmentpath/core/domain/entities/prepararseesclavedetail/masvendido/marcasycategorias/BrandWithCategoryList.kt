package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.masvendido.marcasycategorias


data class BrandWithCategoryList(
    @BrandType val brandType: Int,
    var units : Int,
    var categoryList: List<BrandCategory>
)

