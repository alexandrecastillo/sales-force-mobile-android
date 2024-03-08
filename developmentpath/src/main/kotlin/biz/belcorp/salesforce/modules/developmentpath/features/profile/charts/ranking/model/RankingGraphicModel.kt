package biz.belcorp.salesforce.modules.developmentpath.features.profile.charts.ranking.model


class RankingGraphicModel(
    val netSale: NetSale,
    val activesRetention: ActivesRetention,
    val productivity: Productivity,
    val uniqueIp: UniqueIp,
    val retention6d6: Retention6d6
) {

    class NetSale(
        val data: List<NetSaleModel>,
        val chartData: List<ChartEntryModel>
    )

    class ActivesRetention(
        val chartData: List<ChartEntryModel>
    )

    class Productivity(
        val chartData: List<ChartEntryModel>
    )

    class UniqueIp(
        val chartData: List<ChartEntryModel>
    )

    class Retention6d6(
        val chartData: List<ChartEntryModel>
    )

}
