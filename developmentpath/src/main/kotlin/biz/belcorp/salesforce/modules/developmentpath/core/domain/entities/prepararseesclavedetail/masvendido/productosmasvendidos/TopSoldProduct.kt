package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.masvendido.productosmasvendidos

class TopSoldProduct(
    val top: Int,
    val productName: String,
    val average: Double,
    val histories: List<History>
) {
    class History(
        val campaign: String,
        val average: Int
    )
}

class EsikaLbelData(
    val esika: List<EsikaLbel>,
    val lBel: List<EsikaLbel>,
    val lBelStay: List<EsikaLbel>,
)

data class EsikaLbel(
    val order: Int,
    val name: String,
    val sku: String,
    val cuv: String,
    val images: ImagesProduct,
    val histories: List<Historical>
) {
    data class Historical(
        val campaign: String,
        val bought: Int
    )

    data class ImagesProduct(
        val codsap: String = "",
        val photoProduct: List<String> = emptyList()
    )
}

