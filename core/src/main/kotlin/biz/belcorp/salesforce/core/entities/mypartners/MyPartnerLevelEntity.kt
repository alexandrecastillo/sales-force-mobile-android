package biz.belcorp.salesforce.core.entities.mypartners

import biz.belcorp.salesforce.core.constants.Constant
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.TargetIdProperty
import io.objectbox.relation.ToOne

@Entity
data class MyPartnerLevelEntity(
    val code: String = Constant.EMPTY_STRING,
    val name: String = Constant.EMPTY_STRING,
    @Id var id: Long = Constant.NUMBER_ZERO_LONG
) {

    @TargetIdProperty("myPartnersLevelParent")
    var levelParent: ToOne<MyPartnerEntity> =
        ToOne(this, MyPartnerLevelEntity_.levelParent)
}
