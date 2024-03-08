package biz.belcorp.salesforce.core.entities.digital

import biz.belcorp.salesforce.core.constants.Constant
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id


@Entity
data class DigitalInfoEntity(
    var campaign: String = Constant.EMPTY_STRING,
    var region: String = Constant.EMPTY_STRING,
    var zone: String = Constant.EMPTY_STRING,
    var section: String = Constant.EMPTY_STRING,
    var profile: String = Constant.EMPTY_STRING,
    var actives: Int = Constant.NUMBER_ZERO,
    var subscribed: Int = Constant.NUMBER_ZERO,
    var share: Int = Constant.NUMBER_ZERO,
    var buy: Int = Constant.NUMBER_ZERO,
    var subscribedActivesRatio: Float = Constant.ZERO_FLOAT,
    var shareActivesRatio: Float = Constant.ZERO_FLOAT,
    var shareSubscribedRatio: Float = Constant.ZERO_FLOAT,
    var buyActivesRatio: Float = Constant.ZERO_FLOAT,
    var buySubscribedRatio: Float = Constant.ZERO_FLOAT,
    @Id var id: Long = Constant.NUMBER_ZERO.toLong()
)
