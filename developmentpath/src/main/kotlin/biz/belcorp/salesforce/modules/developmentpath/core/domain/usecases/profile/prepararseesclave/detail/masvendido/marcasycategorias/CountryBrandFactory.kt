package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.profile.prepararseesclave.detail.masvendido.marcasycategorias

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.masvendido.marcasycategorias.Brand
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.masvendido.marcasycategorias.BrandType
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.masvendido.marcasycategorias.BrandWithCategoryList

object CountryBrandFactory {

    private fun getDefaultBrands(countryIso: String): List<Brand> {
        return if (countryIso == Pais.PANAMA.codigoIso) listOf(
            Brand(BrandType.CYZONE, Constant.EMPTY_DOUBLE),
            Brand(BrandType.LBEL, Constant.EMPTY_DOUBLE)
        )
        else listOf(
            Brand(BrandType.ESIKA, Constant.EMPTY_DOUBLE),
            Brand(BrandType.CYZONE, Constant.EMPTY_DOUBLE),
            Brand(BrandType.LBEL, Constant.EMPTY_DOUBLE)
        )
    }

    fun getFilteredBrands(countryIso: String, brands: List<BrandWithCategoryList>): List<Brand> {
        if (brands.isEmpty()) return emptyList()

        val defaultBrands = getDefaultBrands(countryIso)

        defaultBrands.forEach { dBrand ->
            dBrand.average = brands.firstOrNull { brand ->
                brand.brandType == dBrand.brandType
            }?.units?.toDouble() ?: Constant.EMPTY_DOUBLE
        }
        return defaultBrands.sortedByDescending { it.average }
    }

    fun getFilteredBrandsOld(countryIso: String, brands: List<Brand>): List<Brand> {
        if (brands.isEmpty()) return emptyList()

        val defaultBrands = getDefaultBrands(countryIso)

        defaultBrands.forEach { dBrand ->
            dBrand.average = brands.firstOrNull { brand ->
                brand.brandType == dBrand.brandType
            }?.average ?: Constant.EMPTY_DOUBLE
        }
        return defaultBrands.sortedByDescending { it.average }
    }

}
