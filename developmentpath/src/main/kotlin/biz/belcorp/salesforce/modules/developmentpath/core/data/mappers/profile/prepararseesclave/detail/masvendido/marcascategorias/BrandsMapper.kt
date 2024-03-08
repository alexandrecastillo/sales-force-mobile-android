package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.profile.prepararseesclave.detail.masvendido.marcascategorias

import biz.belcorp.salesforce.core.entities.profile.preparingiskey.salesconsultant.TopSalesCoBrandsEntity
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.salessection.TopSalesSeEntity
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.masvendido.marcasycategorias.Brand
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.salesconsultant.BrandContainer

class BrandsMapper {

    fun map(entities: List<TopSalesSeEntity>) = BrandContainer(brands = getBrands(entities))

    private fun getBrands(items: List<TopSalesSeEntity>) =
        items.firstOrNull()?.brands?.toList()?.map { parseMark(it) } ?: emptyList()

    private fun parseMark(item: TopSalesCoBrandsEntity) = with(item) {
        Brand(brandType = BrandTypeBuilder.getType(name), average = units.toDouble())
    }

}

