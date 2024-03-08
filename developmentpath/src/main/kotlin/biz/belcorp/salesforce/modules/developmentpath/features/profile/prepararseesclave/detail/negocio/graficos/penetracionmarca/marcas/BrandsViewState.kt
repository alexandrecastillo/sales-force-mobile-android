package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.penetracionmarca.marcas

import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.masvendido.marcasycategorias.BrandModel

sealed class BrandsViewState {
    class Success(val data: List<BrandModel>) : BrandsViewState()
    class Failure(val message: String) : BrandsViewState()
}
