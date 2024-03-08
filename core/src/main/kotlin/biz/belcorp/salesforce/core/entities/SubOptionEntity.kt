package biz.belcorp.salesforce.core.entities

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.TargetIdProperty
import io.objectbox.relation.ToOne

@Entity
data class SubOptionEntity constructor(
    @Id(assignable = true) var code: Long = 0L,
    var type: String? = null,
    val description: String? = null,
    val position: Int = 0,
    val url: String? = null,
    val active: Boolean? = null
) {

    @TargetIdProperty("optionParentCode")
    var optionParent: ToOne<OptionEntity> = ToOne(this, SubOptionEntity_.optionParent)

}
