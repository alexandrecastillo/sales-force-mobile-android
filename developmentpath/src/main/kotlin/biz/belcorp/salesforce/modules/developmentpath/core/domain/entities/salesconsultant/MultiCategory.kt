package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.salesconsultant


class MultiMarkCategories(
    val multiMark: List<MultiMark>,
    val multiCategories: List<MultiCategories>,
)

data class MultiMark(
    val order: Int,
    val name: String,
    val histories: List<Historical>
) {
    data class Historical(
        val campaign: String,
        val multimarca: Int
    )
}

data class MultiCategories(
    val order: Int,
    val category: String,
    val histories: List<Historical>
) {
    data class Historical(
        val campaign: String,
        val bought: Int
    )
}
