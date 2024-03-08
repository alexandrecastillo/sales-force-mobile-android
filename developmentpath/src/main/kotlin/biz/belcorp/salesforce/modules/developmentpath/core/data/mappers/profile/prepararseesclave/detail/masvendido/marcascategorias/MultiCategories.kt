package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.profile.prepararseesclave.detail.masvendido.marcascategorias

import biz.belcorp.salesforce.core.entities.profile.preparingiskey.salesconsultant.TopSalesCoEntity
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.salesconsultant.TopSalesCoMultiCategoryEntity
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.salesconsultant.TopSalesCoMultiMarkEntity
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.salesconsultant.TopSalesMultiCategoryHistoryEntity
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.salesconsultant.TopSalesMultiMarkHistoryEntity
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.salesconsultant.MultiCategories
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.salesconsultant.MultiMark
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.salesconsultant.MultiMarkCategories

class MultiMarkCategoriesMapper {

    fun map(entities: List<TopSalesCoEntity>) = MultiMarkCategories(
        multiMark = getBrands(entities),
        multiCategories = getCategories(entities),
    )

    private fun getBrands(items: List<TopSalesCoEntity>) =
        items.firstOrNull()?.multiMark?.toList()?.map { parseMark(it) } ?: emptyList()

    private fun getCategories(items: List<TopSalesCoEntity>) =
        items.firstOrNull()?.multiCategories?.toList()?.map { parseCategory(it) } ?: emptyList()

    private fun parseMark(item: TopSalesCoMultiMarkEntity) = with(item) {
        MultiMark(
            order = item.order,
            name = item.name,
            histories = item.historyMultiMark.map { parseMarkHistory(it) } ?: emptyList()
        )
    }

    private fun parseMarkHistory(item: TopSalesMultiMarkHistoryEntity) = with(item) {
        MultiMark.Historical(
            campaign = item.campaign,
            multimarca = item.multimarca,
        )
    }

    private fun parseCategory(item: TopSalesCoMultiCategoryEntity) = with(item) {
        MultiCategories(
            order = item.order,
            category = item.category,
            histories = item.historyMultiCategory.map { parseCategoriesHistory(it) } ?: emptyList()
        )
    }

    private fun parseCategoriesHistory(item: TopSalesMultiCategoryHistoryEntity) = with(item) {
        MultiCategories.Historical(
            campaign = item.campaign,
            bought = item.bought,
        )
    }

}

