package biz.belcorp.salesforce.core.data.repository.directory.mappers

import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.Constant.NEGATIVE_NUMBER_ONE
import biz.belcorp.salesforce.core.data.repository.directory.cloud.dto.ManagerDirectoryDto
import biz.belcorp.salesforce.core.domain.entities.people.Person
import biz.belcorp.salesforce.core.domain.entities.people.RegionManager
import biz.belcorp.salesforce.core.domain.entities.people.SalesDirector
import biz.belcorp.salesforce.core.domain.entities.people.ZoneManager
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.exceptions.UnsupportedRoleException
import biz.belcorp.salesforce.core.entities.directory.ManagerDirectoryEntity
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.isDV
import biz.belcorp.salesforce.core.utils.isGR
import biz.belcorp.salesforce.core.utils.isGZ
import java.util.*

class ManagerDirectoryMapper {

    fun map(entity: ManagerDirectoryDto.ManagerDirectory): ManagerDirectoryEntity = with(entity) {
        return ManagerDirectoryEntity(
            consultantId = consultantId,
            profile = profile,
            region = region.orEmpty(),
            zone = zone.orEmpty(),
            userName = userName,
            firstName = personalInfo.firstName,
            secondName = personalInfo.secondName,
            surname = personalInfo.surname,
            secondSurname = personalInfo.secondSurname,
            fullName = personalInfo.fullName,
            document = personalInfo.document,
            email = personalInfo.email,
            telephoneNumber = personalInfo.telephoneNumber,
            cellphoneNumber = personalInfo.cellphoneNumber,
            productivity = productivity,
            birthday = personalInfo.birthday,
            code = state.code,
            description = state.description,
            isNew = entity.isNew,
            campaignsAsNew = entity.campaignsAsNew
        )
    }

    fun map(entity: ManagerDirectoryEntity?): Person {
        if (entity == null) return mapPerson()
        val role = Rol.Builder.construir(entity.profile.toUpperCase(Locale.getDefault()))
        return when {
            role.isDV() -> mapSalesDirector(entity)
            role.isGR() -> mapRegionManager(entity)
            role.isGZ() -> mapZoneManager(entity)
            else -> throw UnsupportedRoleException(role)
        }
    }

    private fun mapSalesDirector(entity: ManagerDirectoryEntity): SalesDirector = with(entity) {
        return SalesDirector(
            id = consultantId.toLong(),
            document = document,
            firstName = firstName,
            secondSurname = secondName,
            firstSurname = surname,
            secondName = secondSurname,
            birthDate = birthday
        ).apply {
            this.uaKey =
                LlaveUA(EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, NEGATIVE_NUMBER_ONE.toLong())
        }
    }

    private fun mapZoneManager(entity: ManagerDirectoryEntity): ZoneManager = with(entity) {
        return ZoneManager(
            id = consultantId.toLong(),
            document = document,
            firstName = firstName,
            secondSurname = secondName,
            firstSurname = surname,
            secondName = secondSurname,
            birthDate = birthday,
            productivityState = productivity
        ).apply {
            this.uaKey = LlaveUA(region, zone, EMPTY_STRING, NEGATIVE_NUMBER_ONE.toLong())
        }
    }

    private fun mapPerson(): Person {
        return Person(
            NEGATIVE_NUMBER_ONE.toLong(),
            EMPTY_STRING,
            EMPTY_STRING,
            EMPTY_STRING,
            EMPTY_STRING,
            EMPTY_STRING,
            EMPTY_STRING
        )
    }

    private fun mapRegionManager(entity: ManagerDirectoryEntity): RegionManager = with(entity) {
        return RegionManager(
            id = consultantId.toLong(),
            document = document,
            firstName = firstName,
            secondSurname = secondName,
            firstSurname = surname,
            secondName = secondSurname,
            birthDate = birthday,
            productivityState = productivity
        ).apply {
            this.uaKey = LlaveUA(region, zone, EMPTY_STRING, NEGATIVE_NUMBER_ONE.toLong())
        }
    }
}
