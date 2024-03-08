package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.masvendido.marcasycategorias

data class Product(
    val name: String,
    val order: Int,
    val sku: String,
    val cuv: String,
    val quantity: Int,
    val amount: Double,
    val buyCampaigns: List<BuyCampaigns>,
    val image: ImagesProducts,
)

data class ImagesProducts(
    val codsap: String,
    val photoProduct: List<String>,
)
