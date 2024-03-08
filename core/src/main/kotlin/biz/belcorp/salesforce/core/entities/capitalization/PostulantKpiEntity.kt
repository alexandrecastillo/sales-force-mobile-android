package biz.belcorp.salesforce.core.entities.capitalization

import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
class PostulantKpiEntity(
    @Id var id: Long = 0,
    val currentCampaign: String = EMPTY_STRING,
    val region: String = EMPTY_STRING,
    val zone: String = EMPTY_STRING,
    val section: String = EMPTY_STRING,
    val name: String = EMPTY_STRING,
    val inEvaluation: Int = NUMBER_ZERO,
    val preApproved: Int = NUMBER_ZERO,
    val approved: Int = NUMBER_ZERO,
    val rejected: Int = NUMBER_ZERO,
    val conversion: Int = NUMBER_ZERO,
    val daysOnHold: Int = NUMBER_ZERO,
    val anticipatedEntries: Int = NUMBER_ZERO,
    val preRegistered: Int = NUMBER_ZERO
)
