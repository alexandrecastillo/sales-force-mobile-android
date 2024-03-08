package biz.belcorp.salesforce.core.entities.mypartners

import biz.belcorp.salesforce.core.constants.Constant
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.TargetIdProperty
import io.objectbox.relation.ToOne

@Entity
data class MyPartnerPersonalInfoEntity(
    val fullName: String = Constant.EMPTY_STRING,
    val firstName: String = Constant.EMPTY_STRING,
    val surname: String = Constant.EMPTY_STRING,
    val secondName: String = Constant.EMPTY_STRING,
    val secondSurname: String = Constant.EMPTY_STRING,
    val documentNumber: String = Constant.EMPTY_STRING,
    val email: String = Constant.EMPTY_STRING,
    val address: String = Constant.EMPTY_STRING,
    val cellphoneNumber: String = Constant.EMPTY_STRING,
    val telephoneNumber: String = Constant.EMPTY_STRING,
    val birthday: String = Constant.EMPTY_STRING,
    @Id var id: Long = Constant.NUMBER_ZERO_LONG
) {

    @TargetIdProperty("myPartnerProfileInfoParent")
    var personalInfo: ToOne<MyPartnerEntity> =
        ToOne(this, MyPartnerPersonalInfoEntity_.personalInfo)
}
