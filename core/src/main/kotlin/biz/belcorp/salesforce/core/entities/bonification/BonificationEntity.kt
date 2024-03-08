package biz.belcorp.salesforce.core.entities.bonification

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class BonificationEntity(
    @Id var id: Long = 0L,
    var campaign: String? = null,
    var region: String? = null,
    val zone: String? = null,
    val section: String? = null,
    val code: String? = null,
    val amount: Double? = null
)
