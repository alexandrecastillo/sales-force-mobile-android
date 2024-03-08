package biz.belcorp.salesforce.core.entities.collection

import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.TargetIdProperty
import io.objectbox.relation.ToOne

@Entity
data class CollectionOrderEntity(
    @Id
    var id: Long = NUMBER_ZERO.toLong(),
    val range: String = EMPTY_STRING,
    val collected: Int = NUMBER_ZERO,
    val total: Int = NUMBER_ZERO,
    val position:Int = NUMBER_ZERO
) {
    @TargetIdProperty("collectionParentCode")
    var collectionParent: ToOne<CollectionEntity> = ToOne(this, CollectionOrderEntity_.collectionParent)

}
