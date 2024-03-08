package biz.belcorp.salesforce.modules.brightpath.features.drilldown.utils

import android.content.Context
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.modules.brightpath.R
import biz.belcorp.salesforce.modules.brightpath.core.domain.constants.Constants
import biz.belcorp.salesforce.modules.brightpath.features.drilldown.model.ConsultantHeaderDetailedKpiModel

class GridBuilder(private val context: Context) {

    private val changeLevelText by lazy { context.getString(R.string.label_cambio_de_nivel) }
    private val possibleLevelChangeText by lazy { context.getString(R.string.label_posibles_cambios_de_nivel) }
    private val endPeriodText by lazy { context.getString(R.string.label_fin_periodo) }

    private val couldChangeInText by lazy { context.getString(R.string.text_posibles_changes_level_consultant_inf) }

    private val possibleLevelChangeId = Constants.ID_LIST_MAY_CHANGE_LEVEL
    val changeLevelId = Constants.ID_LIST_CHANGE_LEVEL
    val endPeriodLevelId = Constants.ID_END_PERIOD

    fun build(hidePossibleChanges: Boolean = false): List<ConsultantHeaderDetailedKpiModel> {
        return getBrightPathGrids(hidePossibleChanges)
    }

    private fun getBrightPathGrids(hidePossibleChanges: Boolean = false): List<ConsultantHeaderDetailedKpiModel> {
        return ArrayList<ConsultantHeaderDetailedKpiModel>().apply {
            if (!hidePossibleChanges) add(doMayChangeLevel())

            add(doChangeLevel())
            add(doEndPeriod())
        }.toList()
    }

    private fun doMayChangeLevel() = ConsultantHeaderDetailedKpiModel(
        id = possibleLevelChangeId,
        title = possibleLevelChangeText,
        description = couldChangeInText
    )

    private fun doChangeLevel() = ConsultantHeaderDetailedKpiModel(
        id = changeLevelId,
        title = changeLevelText,
        description = EMPTY_STRING
    )

    private fun doEndPeriod() = ConsultantHeaderDetailedKpiModel(
        id = endPeriodLevelId,
        title = endPeriodText,
        description = EMPTY_STRING
    )
}
