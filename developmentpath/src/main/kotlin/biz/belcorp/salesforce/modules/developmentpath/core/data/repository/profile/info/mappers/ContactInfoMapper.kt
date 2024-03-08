package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.info.mappers

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.entities.ConsultantEntity
import biz.belcorp.salesforce.core.entities.businesspartner.BusinessPartnerEntity
import biz.belcorp.salesforce.core.entities.directory.ManagerDirectoryEntity
import biz.belcorp.salesforce.core.entities.sql.unete.PostulanteEntity
import biz.belcorp.salesforce.core.utils.guionSiVacioONull
import biz.belcorp.salesforce.core.utils.toDate
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.profile.ProfileInfo

class ContactInfoMapper {

    fun map(entity: PostulanteEntity): ProfileInfo.Contact {
        return ProfileInfo.Contact(
            primerNombre = entity.primerNombre.orEmpty(),
            primerApellido = entity.apellidoPaterno.orEmpty(),
            segundoApellido = entity.apellidoMaterno.orEmpty(),
            birthdate = entity.fechaNacimiento?.toDate(),
            phoneNumber = entity.celular.orEmpty(),
            email = entity.correoElectronico.orEmpty(),
            address = entity.direccion.orEmpty(),
            document = entity.numeroDocumento.orEmpty()
        )
    }

    fun map(entity: ConsultantEntity): ProfileInfo.Contact {
        return ProfileInfo.Contact(
            primerNombre = entity.firstName,
            primerApellido = entity.surname,
            segundoApellido = entity.secondSurname,
            birthdate = entity.birthday.toDate(),
            phoneNumber = entity.phone,
            email = entity.email,
            anniversaryDate = entity.anniversaryDate.toDate(),
            address = entity.address,
            addressReference = entity.addressReference,
            document = entity.document,
            multiBrand = entity.multiMarca,
            digitalSegment = entity.digitalSegment
        )
    }

    fun map(entity: BusinessPartnerEntity): ProfileInfo.Contact {
        return ProfileInfo.Contact(
            primerNombre = entity.firstName,
            primerApellido = entity.firstSurname,
            segundoApellido = entity.secondSurname,
            birthdate = entity.birthDate.toDate(),
            phoneNumber = entity.cellphone,
            email = entity.email,
            anniversaryDate = entity.anniversaryDate.toDate(),
            address = entity.address,
            document = entity.document
        )
    }

    fun map(entity: ManagerDirectoryEntity): ProfileInfo.Contact {
        return ProfileInfo.Contact(
            primerNombre = entity.firstName,
            primerApellido = entity.surname,
            segundoApellido = entity.secondSurname,
            birthdate = entity.birthday.toDate(),
            phoneNumber = entity.cellphoneNumber.guionSiVacioONull(),
            homeNumber = entity.telephoneNumber.guionSiVacioONull(),
            email = entity.email,
            address = Constant.EMPTY_STRING,
            document = entity.document
        )
    }

}
