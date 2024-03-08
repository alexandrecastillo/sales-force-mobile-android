package biz.belcorp.salesforce.core.entities

import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany

@Entity
data class OptionEntity constructor(
    @Id(assignable = true) var code: Long = 0L,
    var type: String? = null,
    val description: String? = null,
    val position: Int = 0,
    val url: String? = null,
    val active: Boolean? = null
) {

    @Backlink(to = "optionParent")
    var subOptions: ToMany<SubOptionEntity> = ToMany(this, OptionEntity_.subOptions)

}
