package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.masvendido.marcasycategorias.helper

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.masvendido.marcasycategorias.BrandType
import biz.belcorp.salesforce.base.R as BaseR

object BrandNameColorFactory {
    fun from(mark: Int): Pair<Int, String> = when (mark) {
        BrandType.ESIKA -> Pair(BaseR.color.brand_esika, "Ésika")
        BrandType.LBEL -> Pair(BaseR.color.brand_lbel, "L’Bel")
        BrandType.CYZONE -> Pair(BaseR.color.brand_cyzone, "Cyzone")
        else -> error("Marca no encontrada")
    }
}
