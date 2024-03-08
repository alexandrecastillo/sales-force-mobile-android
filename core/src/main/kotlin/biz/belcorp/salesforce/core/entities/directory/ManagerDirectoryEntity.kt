package biz.belcorp.salesforce.core.entities.directory

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class ManagerDirectoryEntity(
    @Id var id: Long = 0,
    val consultantId: Int,
    val profile: String,
    val region: String,
    val zone: String,
    val userName: String,
    val firstName: String,
    val secondName: String,
    val surname: String,
    val secondSurname: String,
    val fullName: String,
    val document: String,
    val email: String,
    val telephoneNumber: String,
    val cellphoneNumber: String,
    val birthday: String,
    val productivity: String,
    val code: String,
    val description: String,
    val isNew: Boolean,
    val campaignsAsNew: Int
)
