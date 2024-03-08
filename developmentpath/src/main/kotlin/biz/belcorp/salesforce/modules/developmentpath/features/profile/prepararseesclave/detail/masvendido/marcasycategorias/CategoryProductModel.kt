package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.masvendido.marcasycategorias

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.masvendido.marcasycategorias.ImagesProducts

data class CategoryProductModel(
    val name: String,
    val order: Int,
    val sku: String,
    val cuv: String,
    val quantity: String?,
    val amount: Double,
    val buyCampaigns: String?,
    val image: ImagesProducts?
)
