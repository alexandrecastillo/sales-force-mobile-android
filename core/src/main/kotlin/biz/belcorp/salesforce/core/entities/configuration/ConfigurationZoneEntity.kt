package biz.belcorp.salesforce.core.entities.configuration

import biz.belcorp.salesforce.core.constants.Constant
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.TargetIdProperty
import io.objectbox.relation.ToOne

@Entity
data class ConfigurationZoneEntity constructor(
    @Id var code: Long = 0,
    var region: String = Constant.EMPTY_STRING,
    var zone: String = Constant.EMPTY_STRING,
    var isPdv: Boolean = false,
    var isDataReport: Boolean = false
) {

    @TargetIdProperty("configParentCode")
    var configParent: ToOne<ConfigurationCountryEntity> =
        ToOne(this, ConfigurationZoneEntity_.configParent)
}
