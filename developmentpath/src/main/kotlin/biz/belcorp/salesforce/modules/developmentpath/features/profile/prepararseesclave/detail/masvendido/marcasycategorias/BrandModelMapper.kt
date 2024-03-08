package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.masvendido.marcasycategorias

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.masvendido.marcasycategorias.Brand
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.masvendido.marcasycategorias.helper.BrandNameColorFactory

class BrandModelMapper {

    fun parse(entity: Brand): BrandModel {
        val pair = BrandNameColorFactory.from(entity.brandType)
        return BrandModel(
            color = pair.first,
            name = pair.second,
            average = entity.average,
            brandType = entity.brandType
        )
    }

}
