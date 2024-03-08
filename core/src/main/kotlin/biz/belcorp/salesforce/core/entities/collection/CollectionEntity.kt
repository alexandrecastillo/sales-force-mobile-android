package biz.belcorp.salesforce.core.entities.collection

import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO
import biz.belcorp.salesforce.core.constants.Constant.ZERO_DECIMAL
import biz.belcorp.salesforce.core.db.objectbox.converters.NullToDefaultCollectionStringConverter
import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany

@Entity
data class CollectionEntity(
    var campaign: String = EMPTY_STRING,
    var profile: String = EMPTY_STRING,
    var region: String = EMPTY_STRING,
    var zone: String = EMPTY_STRING,
    var section: String = EMPTY_STRING,
    @Convert(dbType = String::class, converter = NullToDefaultCollectionStringConverter::class)
    var days: String = EMPTY_STRING,
    var percentage: Double = ZERO_DECIMAL,
    var invoicedSale: Double = ZERO_DECIMAL,
    var amountCollected: Double = ZERO_DECIMAL,
    var debtorConsultants: Int = NUMBER_ZERO,
    var ordersTotalGained: Double = ZERO_DECIMAL,
    var ordersMinimalCollectionPercentage: Double = ZERO_DECIMAL,
    var ordersTotalCollected: Int = NUMBER_ZERO,
    var ordersTotal: Int = NUMBER_ZERO,
    @Id
    var id: Long = NUMBER_ZERO.toLong()
) {

    @Backlink(to = "collectionParent")
    var ordersRange: ToMany<CollectionOrderEntity> = ToMany(this, CollectionEntity_.ordersRange)
}
