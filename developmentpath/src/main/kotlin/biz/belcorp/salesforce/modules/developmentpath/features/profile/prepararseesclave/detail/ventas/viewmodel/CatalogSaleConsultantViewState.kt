package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.ventas.viewmodel

import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.ventas.model.CatalogSaleConsultantModel

sealed class CatalogSaleConsultantViewState {
    class Success(val model: CatalogSaleConsultantModel) : CatalogSaleConsultantViewState()
    object Failure : CatalogSaleConsultantViewState()
}