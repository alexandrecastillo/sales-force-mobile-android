package biz.belcorp.salesforce.core.data.repository.businesspartners.mappers

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.data.repository.businesspartners.cloud.dto.BusinessPartnerDto
import biz.belcorp.salesforce.core.entities.sql.directorio.SociaEmpresariaEntity
import biz.belcorp.salesforce.core.utils.zeroIfNull

class BusinessPartnerTableMapper {

    fun map(data: BusinessPartnerDto): List<SociaEmpresariaEntity> {
        return data.businessPartners.map { map(it) }
    }

    private fun map(entity: BusinessPartnerDto.BusinessPartner): SociaEmpresariaEntity =
        with(entity) {
            return SociaEmpresariaEntity().apply {
                consultorasId = consultantId.zeroIfNull().toLong()
                codigo = code
                region = entity.region
                zona = zone
                seccion = section
                documentoIdentidad = personalInfo.document
                primerNombre = personalInfo.firstName
                segundoNombre = personalInfo.secondName
                primerApellido = personalInfo.firstSurname
                segundoApellido = personalInfo.secondSurname
                fechaNacimiento = personalInfo.birthDate.orEmpty()
                anniversaryDate = anniversaryDate.orEmpty()
                email = personalInfo.email
                telefonoCelular = personalInfo.cellphone
                telefonoCasa = personalInfo.homephone
                direccion = personalInfo.address
                ultimaFacturacion = billingInfo.billingLastCampaign
                campaniaIngreso = campaignAdmission
                saldoPendiente = pendingDebt.zeroIfNull().toString()
                exitosa = if (successful) Constant.NUMBER_ONE else Constant.NUMBER_ZERO
                nivel = level.levelName
            }
        }

}
