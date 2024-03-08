package biz.belcorp.salesforce.core.entities

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO_LONG
import biz.belcorp.salesforce.core.entities.businesspartner.BusinessPartnerEntity
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.TargetIdProperty
import io.objectbox.relation.ToOne

@Entity
data class SuccessfuHistoricEntity(
    @Id var id: Long = NUMBER_ZERO_LONG,
    var campaign: String = Constant.EMPTY_STRING,
    var value: Boolean = false
) {

    @TargetIdProperty("partnerCode")
    var partnerParent: ToOne<BusinessPartnerEntity> =
        ToOne(this, SuccessfuHistoricEntity_.partnerParent)

}
