package biz.belcorp.salesforce.core.utils.notification

import biz.belcorp.salesforce.core.constants.Constant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class RddRecognitionData {
    @SerialName("id")
    var id: Int = Constant.NUMBER_ZERO

    @SerialName("personId")
    var personId: String = Constant.EMPTY_STRING

    @SerialName("personRol")
    var personRol: String = Constant.EMPTY_STRING

    @SerialName("personRecognizedId")
    var personRecognizedId: String = Constant.EMPTY_STRING

    @SerialName("personRecognizedRole")
    var personRecognizedRole: String = Constant.EMPTY_STRING

    @SerialName("personRecognizedName")
    var personRecognizedName: String = Constant.EMPTY_STRING

    @SerialName("campaign")
    var campaign: String = Constant.EMPTY_STRING

    @SerialName("score")
    var score: Int = Constant.NEGATIVE_NUMBER_ONE
}
