package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.info.mappers

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.ConsultantEntity
import biz.belcorp.salesforce.core.entities.businesspartner.BusinessPartnerEntity
import biz.belcorp.salesforce.core.entities.directory.ManagerDirectoryEntity
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.guionSiNull
import biz.belcorp.salesforce.core.utils.isGZ
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.profile.ProfileInfo


class ProfileInfoMapper {

    fun map(
        contact: ProfileInfo.Contact
    ): ProfileInfo.DatoPersonaPc {
        return ProfileInfo.DatoPersonaPc(contact)
    }

    fun map(
        contact: ProfileInfo.Contact,
        entity: ConsultantEntity
    ): ProfileInfo.DatoPersonaCo {
        return ProfileInfo.DatoPersonaCo(
            contact = contact,
            codigo = entity.code,
            digitoVerificador = entity.checkDigit,
            saldoPendiente = entity.pendingDebt,
            cantidadReingresos = entity.reentriesU18C,
            autorizada = entity.isAuthorized,
            codigoCampaniaIngreso = entity.billingFirstCampaign,
            codigoCampaniaUltimaFacturacion = entity.billingLastCampaign,
            celular = entity.phone,
            segmentoInterno = entity.segmentDescription,
            hasCashPayment = entity.hasCashPayment,
            digitalSegment = entity.digitalSegment,
            segmentDescription = entity.segmentDescription
        )
    }

    fun map(
        contact: ProfileInfo.Contact,
        entity: BusinessPartnerEntity
    ): ProfileInfo.DatoPersonaSe {
        return ProfileInfo.DatoPersonaSe(
            contact = contact,
            campaniaIngreso = entity.campaignAdmission,
            nivel = entity.level.guionSiNull(),
            codigo = entity.partnerCode,
            saldoPendiente = entity.pendingDebt,
            codigoSeccion = entity.section,
            campaniaUltimaFacturacion = entity.billingLastCampaign,
            exitosa = entity.successful,
            productividad = entity.productivity
        )
    }

    fun map(
        role: Rol,
        contact: ProfileInfo.Contact,
        entity: ManagerDirectoryEntity
    ): ProfileInfo {
        return if (role.isGZ()) {
            mapGZ(contact, entity)
        } else {
            mapGR(contact, entity)
        }
    }

    private fun mapGZ(
        contact: ProfileInfo.Contact,
        entity: ManagerDirectoryEntity
    ): ProfileInfo.DatoPersonaGz {
        val uaKey = LlaveUA(
            codigoRegion = entity.region,
            codigoZona = entity.zone
        )
        return ProfileInfo.DatoPersonaGz(
            contact = contact,
            llaveUA = uaKey,
            estado = entity.description,
            esNueva = entity.isNew,
            nroCampaniasComoNueva = entity.campaignsAsNew
        )
    }

    private fun mapGR(
        contact: ProfileInfo.Contact,
        entity: ManagerDirectoryEntity
    ): ProfileInfo.DatoPersonaGr {
        val uaKey = LlaveUA(
            codigoRegion = entity.region
        )
        return ProfileInfo.DatoPersonaGr(
            contact = contact,
            llaveUA = uaKey,
            estado = entity.description
        )
    }

}
